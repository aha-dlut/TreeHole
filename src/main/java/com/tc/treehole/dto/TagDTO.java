package com.tc.treehole.dto;

import lombok.Data;

import java.util.List;

/*
@author TanCheng
@create 2023 -06 -17    
*/
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
