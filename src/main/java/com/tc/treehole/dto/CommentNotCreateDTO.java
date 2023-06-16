package com.tc.treehole.dto;

import com.tc.treehole.model.User;
import lombok.Data;

/*
@author TanCheng
@create 2023 -06 -15    
*/
@Data
public class CommentNotCreateDTO {

    private Integer id;


    private Integer parentId;


    private Integer type;

    private Integer commentator;

    private Long gmtCreate;

    private Long gmtModified;

    private Long likeCount;

    private String content;

    private User user;

    private Integer commentCount;

}
