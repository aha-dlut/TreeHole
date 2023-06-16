package com.tc.treehole.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*
@author TanCheng
@create 2023 -06 -11    
*/
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;

    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private  Integer totalPage;
    public void setPagination(Integer totalPage, Integer page) {
        this.totalPage = totalPage;
        this.page = page;

        pages.add(page);
        for( int i = 1; i <= 3; i++){
            if(page - i >0){
                pages.add(0,page - i);
            }
            if(page + i <= this.totalPage){
                pages.add(page + i);
            }
        }



        //是否展示前一页
        if(page == 1){
            showPrevious = false;
        }else{
            showPrevious = true;
        }
        //是否展示下一页
        if(page == this.totalPage){
            showNext = false;
        }else{
            showNext = true;
        }

        //是否展示首页
        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }

        //是否展示尾页
        if(pages.contains(this.totalPage)){
            showEndPage = false;
        }else{
            showEndPage = true;

        }
    }
}
