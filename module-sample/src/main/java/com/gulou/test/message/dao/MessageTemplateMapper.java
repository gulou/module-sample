package com.gulou.test.message.dao;

import com.gulou.test.message.po.MessageTemplate;
import com.gulou.test.message.po.MessageTemplateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageTemplateMapper {
    long countByExample(MessageTemplateExample example);

    int deleteByExample(MessageTemplateExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MessageTemplate record);

    int insertSelective(MessageTemplate record);

    List<MessageTemplate> selectByExample(MessageTemplateExample example);

    MessageTemplate selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MessageTemplate record, @Param("example") MessageTemplateExample example);

    int updateByExample(@Param("record") MessageTemplate record, @Param("example") MessageTemplateExample example);

    int updateByPrimaryKeySelective(MessageTemplate record);

    int updateByPrimaryKey(MessageTemplate record);
}