package com.tc.treehole.enums;

/*
@author TanCheng
@create 2023 -06 -17    
*/
public enum NotificationStatusEnum {
    UNREAD(0), READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
