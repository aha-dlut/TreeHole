package com.tc.treehole.controller;

import com.tc.treehole.dto.AccessTokenDTO;
import com.tc.treehole.dto.GIthubUser;
import com.tc.treehole.mapper.UserMapper;
import com.tc.treehole.model.User;
import com.tc.treehole.provider.GithubProvider;
import com.tc.treehole.service.UserService;
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


    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    UserService userService;

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
            if(githubUser.getId() == null){
                return "redirect:/";
            }
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            if(githubUser.getAvatar_url() == null){
                user.setAvatarUrl("https://avatars.githubusercontent.com/u/128967553?v=4");
            }else{
                user.setAvatarUrl(githubUser.getAvatar_url());
            }
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",token));

            return "redirect:/";
        }else{
            return "redirect:/";
        }

    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response
                         ){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
