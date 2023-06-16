package com.tc.treehole.dto;

import lombok.Data;

/*
@author TanCheng
@create 2023 -06 -04    
*/
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
