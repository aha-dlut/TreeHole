package com.tc.treehole.service;

import com.tc.treehole.dto.CommentNotCreateDTO;
import com.tc.treehole.enums.CommentTypeEnum;
import com.tc.treehole.exception.CustomizeErrorCode;
import com.tc.treehole.exception.CustomizeException;
import com.tc.treehole.mapper.*;
import com.tc.treehole.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
@author TanCheng
@create 2023 -06 -15    
*/
@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentExtMapper commentExtMapper;

    @Transactional
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // 回复评论
            Comment commentDB = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (commentDB == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            comment.setCommentCount(0);

            commentMapper.insert(comment);
            commentDB.setCommentCount(1);
            commentExtMapper.incCommentCount(commentDB);
        } else {
            // 回复问题
            Question questionDB = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (questionDB == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionDB.setCommentCount(1);
            questionExtMapper.incComment(questionDB);
        }

    }

    public List<CommentNotCreateDTO> listByTargetId(Integer id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andTypeEqualTo(type.getType());
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if(comments.size() == 0){
            return new ArrayList<>();
        }
        //获取评论人id并且去重
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList<>();
        userIds.addAll(commentators);

        //获取评论人
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Integer, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));

        //组合
        List<CommentNotCreateDTO> commentNotCreateDTOS = comments.stream().map(comment -> {
            CommentNotCreateDTO commentNotCreateDTO = new CommentNotCreateDTO();
            BeanUtils.copyProperties(comment,commentNotCreateDTO);
            commentNotCreateDTO.setUser(userMap.get(comment.getCommentator()));
            return commentNotCreateDTO;
        }).collect(Collectors.toList());

        return commentNotCreateDTOS;
    }
}
