package com.tc.treehole.dto;

import com.tc.treehole.model.User;
import lombok.Data;

/*
@author TanCheng
@create 2023 -06 -11    
*/
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long modified;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;


    private Integer creator;
    private String tag;
    private User user;
}
