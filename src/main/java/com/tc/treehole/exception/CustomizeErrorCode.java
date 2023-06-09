package com.tc.treehole.exception;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

/*
@author TanCheng
@create 2023 -06 -13    
*/
public enum CustomizeErrorCode implements ICustomizeErrorCode{

   QUESTION_NOT_FOUND(2001,"你找到的问题不存在了，换一个试试"),
   TARGET_PARAM_NOT_FOUND(2002,"未选中任何温和或者评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试"),
    SYS_ERROR(2004,"服务器累坏啦"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在"),
    READ_NOTIFICATION_FAIL(2008,"这是别人的信息"),
   NOTIFICATION_NOT_FOUND(2009,"你找的消息不存在了");
    private Integer code;
   @Override
   public Integer getCode(){
       return  code;
   }


   private String message;



    @Override
    public String getMessage(){
        return  message;
    }

    CustomizeErrorCode(Integer code,String message){
        this.message = message;
        this.code = code;
    }
}
