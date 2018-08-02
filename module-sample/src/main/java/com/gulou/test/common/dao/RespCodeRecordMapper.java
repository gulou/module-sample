package com.gulou.test.common.dao;

import com.gulou.test.common.po.RespCodeRecord;
import com.gulou.test.common.po.RespCodeRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RespCodeRecordMapper {
    long countByExample(RespCodeRecordExample example);

    int deleteByExample(RespCodeRecordExample example);

    int deleteByPrimaryKey(Long id);

    int insert(RespCodeRecord record);

    int insertSelective(RespCodeRecord record);

    List<RespCodeRecord> selectByExample(RespCodeRecordExample example);

    RespCodeRecord selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") RespCodeRecord record, @Param("example") RespCodeRecordExample example);

    int updateByExample(@Param("record") RespCodeRecord record, @Param("example") RespCodeRecordExample example);

    int updateByPrimaryKeySelective(RespCodeRecord record);

    int updateByPrimaryKey(RespCodeRecord record);
}