package com.tc.treehole.controller;

import com.tc.treehole.dto.AccessTokenDTO;
import com.tc.treehole.dto.GIthubUser;
import com.tc.treehole.mapper.UserMapper;
import com.tc.treehole.model.User;
import com.tc.treehole.provider.GithubProvider;
import okhttp3.Response;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/*
@author TanCheng
@create 2023 -06 -04    
*/
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response
                           ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
       accessTokenDTO.setCode(code);
       accessTokenDTO.setRedirect_uri(redirectUri);
       accessTokenDTO.setState(state);
       accessTokenDTO.setClient_id(clientId);
       accessTokenDTO.setClient_secret(clientSecret);
       String accessToken =  githubProvider.getAccessToken(accessTokenDTO);
        GIthubUser githubUser =  githubProvider.getUser(accessToken);

        if(githubUser != null){
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtModified());

            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            //登录成功，插入数据库，相当于写入session


            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else{
            return "redirect:/";
        }

    }
}
