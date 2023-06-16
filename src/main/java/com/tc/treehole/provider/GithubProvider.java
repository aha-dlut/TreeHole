package com.tc.treehole.provider;

import com.alibaba.fastjson.JSON;
import com.tc.treehole.dto.AccessTokenDTO;
import com.tc.treehole.dto.GIthubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;
import okhttp3.MediaType;
import java.io.IOException;
import java.security.PublicKey;
import java.util.concurrent.TimeUnit;

/*
@author TanCheng
@create 2023 -06 -04    
*/
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient.Builder().build();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GIthubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization","token "+accessToken)
                .build();
        try  {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GIthubUser githubUser = JSON.parseObject(string, GIthubUser.class);
            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}