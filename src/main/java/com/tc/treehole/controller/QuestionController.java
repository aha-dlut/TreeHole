package com.tc.treehole.controller;

import com.tc.treehole.dto.CommentNotCreateDTO;
import com.tc.treehole.dto.QuestionDTO;
import com.tc.treehole.enums.CommentTypeEnum;
import com.tc.treehole.service.CommentService;
import com.tc.treehole.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/*
@author TanCheng
@create 2023 -06 -11    
*/
@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model
                           ){

        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentNotCreateDTO> comments =  commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relatedQuestions);
        return "question";
    }
}
