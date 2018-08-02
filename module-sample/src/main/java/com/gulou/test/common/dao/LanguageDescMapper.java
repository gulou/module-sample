package com.gulou.test.common.dao;

import com.gulou.test.common.po.LanguageDesc;
import com.gulou.test.common.po.LanguageDescExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LanguageDescMapper {
    long countByExample(LanguageDescExample example);

    int deleteByExample(LanguageDescExample example);

    int deleteByPrimaryKey(Long id);

    int insert(LanguageDesc record);

    int insertSelective(LanguageDesc record);

    List<LanguageDesc> selectByExample(LanguageDescExample example);

    LanguageDesc selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") LanguageDesc record, @Param("example") LanguageDescExample example);

    int updateByExample(@Param("record") LanguageDesc record, @Param("example") LanguageDescExample example);

    int updateByPrimaryKeySelective(LanguageDesc record);

    int updateByPrimaryKey(LanguageDesc record);
}