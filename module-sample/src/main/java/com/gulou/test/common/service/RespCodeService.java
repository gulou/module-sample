package com.gulou.test.common.service;

import com.gulou.test.common.BaseService;
import com.gulou.test.common.dao.RespCodeRecordMapper;
import com.gulou.test.common.po.RespCodeRecord;
import com.gulou.test.common.po.RespCodeRecordExample;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>返回码查询</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/07/05 11:26
 */

@Service
@Slf4j
public class RespCodeService extends BaseService{

    @Autowired
    private RespCodeRecordMapper codeRecordMapper;

    //通过返回码查询
    public RespCodeRecord queryByCode(Long respCode){

        RespCodeRecordExample codeRecordExample = new RespCodeRecordExample();
        codeRecordExample.createCriteria().andCodeEqualTo(respCode) ;

        List<RespCodeRecord> codeRecords = codeRecordMapper.selectByExample(codeRecordExample);
        if(codeRecords.size() == 0){
            return null;
        }
        return codeRecords.get(0);
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
