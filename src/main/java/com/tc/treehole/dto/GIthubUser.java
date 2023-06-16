package com.tc.treehole.dto;

import lombok.Data;

/*
@author TanCheng
@create 2023 -06 -04    
*/
@Data
public class GIthubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;

    @Override
    public String toString() {
        return "GIthubUser{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", bio='" + bio + '\'' +
                '}';
    }
}
