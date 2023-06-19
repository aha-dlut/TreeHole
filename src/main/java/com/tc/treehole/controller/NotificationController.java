package com.tc.treehole.controller;

import com.tc.treehole.dto.NotificationDTO;
import com.tc.treehole.dto.PaginationDTO;
import com.tc.treehole.enums.NotificationTypeEnum;
import com.tc.treehole.model.User;
import com.tc.treehole.service.NotificationService;
import com.tc.treehole.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/*
@author TanCheng
@create 2023 -06 -17    
*/
@Controller
public class NotificationController {
    @Autowired
    QuestionService questionService;

    @Autowired
    NotificationService notificationService;


    @GetMapping("/notification/{id}")
    public String profile(@PathVariable(name = "id") Integer id,
                          Model model,
                          HttpServletRequest request
    ) {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id,user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterid();
        } else {
            return "redirect:/";
        }


    }
}
