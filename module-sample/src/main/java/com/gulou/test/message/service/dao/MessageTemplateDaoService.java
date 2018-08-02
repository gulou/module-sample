package com.gulou.test.message.service.dao;

import com.gulou.test.common.enums.LanguageType;
import com.gulou.test.message.dao.MessageTemplateMapper;
import com.gulou.test.message.po.MessageTemplate;
import com.gulou.test.message.po.MessageTemplateExample;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>模板dao</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/08/01 14:59
 */

@Service
public class MessageTemplateDaoService {

    @Autowired
    private MessageTemplateMapper templateMapper;

    //插入
    public int insert(MessageTemplate template){
        return templateMapper.insert(template) ;
    }

    //删除

    //通过主键更新
    public int updateById(MessageTemplate template){
        return templateMapper.updateByPrimaryKeySelective(template) ;
    }

    //通过代码+语言更新
    public int updateByCodeAndLanguage(Integer code, LanguageType languageType
            , MessageTemplate template){

        MessageTemplateExample templateExample = new MessageTemplateExample();
        templateExample.createCriteria().andCodeEqualTo(code)
                .andLanguageEqualTo(languageType.getCode());

        return templateMapper.updateByExampleSelective(template,templateExample);
    }


    //通过代码+语言查询
    public MessageTemplate queryByCodeAndLanguage(Integer code,LanguageType languageType){

        MessageTemplateExample templateExample = new MessageTemplateExample();
        templateExample.createCriteria().andCodeEqualTo(code)
                .andLanguageEqualTo(languageType.getCode());

        List<MessageTemplate> templates = templateMapper.selectByExample(templateExample);

        if(!CollectionUtils.isEmpty(templates)){
            return templates.get(0);
        }

        return null;
    }
}
