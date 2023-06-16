package com.tc.treehole.mapper;

import com.tc.treehole.model.Question;
import com.tc.treehole.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Question record);
    int incComment(Question record);
}