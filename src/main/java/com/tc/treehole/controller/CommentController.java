package com.tc.treehole.controller;

import com.tc.treehole.dto.CommentDTO;
import com.tc.treehole.dto.CommentNotCreateDTO;
import com.tc.treehole.dto.ResultDTO;
import com.tc.treehole.enums.CommentTypeEnum;
import com.tc.treehole.exception.CustomizeErrorCode;
import com.tc.treehole.model.Comment;
import com.tc.treehole.model.User;
import com.tc.treehole.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
@author TanCheng
@create 2023 -06 -13    
*/
@Controller
public class CommentController {


   @Autowired
   private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){


        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setLikeCount(0L);
        comment.setCommentator(user.getId());
        commentService.insert(comment,user);
        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentNotCreateDTO>> comments(@PathVariable(name = "id") Integer id){
        List<CommentNotCreateDTO> commentNotCreateDTOS= commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentNotCreateDTOS);
    }
}
