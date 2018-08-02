package com.gulou.test.common.service;

import com.gulou.test.common.BaseService;
import com.gulou.test.common.dao.LanguageDescMapper;
import com.gulou.test.common.enums.LanguageType;
import com.gulou.test.common.po.LanguageDesc;
import com.gulou.test.common.po.LanguageDescExample;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>语言描述服务</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/05 11:20
 */

@Service
@Slf4j
public class LanguageDescService extends BaseService {

    @Autowired
    private LanguageDescMapper descMapper;

    //根据语言描述id查询
    public List<LanguageDesc> queryByDescId(Long descId) {
        LanguageDescExample descExample = new LanguageDescExample();
        descExample.createCriteria().andLanguageDescIdEqualTo(descId);

        return descMapper.selectByExample(descExample);
    }

    //根据语言描述id+语言查询
    public LanguageDesc queryByDescIdAndLanguage(Long descId, LanguageType languageType) {
        LanguageDescExample descExample = new LanguageDescExample();
        descExample.createCriteria().andLanguageDescIdEqualTo(descId)
                .andLanguageEqualTo(languageType.getCode());

        List<LanguageDesc> languageDescs = descMapper.selectByExample(descExample);
        if(languageDescs.size() == 0){
            return null;
        }
        return languageDescs.get(0);
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
