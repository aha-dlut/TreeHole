package com.tc.treehole.dto;

import lombok.Data;

/*
@author TanCheng
@create 2023 -06 -17    
*/
@Data
public class NotificationDTO {
    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private Integer notifier;
    private String notifierName;
    private String outerTitle;
    private Integer outerid;
    private String typeName;
    private Integer type;

}
