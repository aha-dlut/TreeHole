package com.tc.treehole.dto;

import lombok.Data;

/*
@author TanCheng
@create 2023 -06 -13    
*/
@Data
public class CommentDTO {
    private Integer parentId;
    private String content;
    private Integer type;
}
