package com.tc.treehole.enums;

/*
@author TanCheng
@create 2023 -06 -15    
*/
public enum CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);

    private Integer type;

    CommentTypeEnum(Integer type){
        this.type = type;
    }
    public Integer getType(){
        return type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum value : CommentTypeEnum.values()) {
               if(value.getType() == type){
                   return true;
               }
        }
        return false;
    }
}
