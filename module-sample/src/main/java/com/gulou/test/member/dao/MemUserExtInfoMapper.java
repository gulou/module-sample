package com.gulou.test.member.dao;

import com.gulou.test.member.po.MemUserExtInfo;
import com.gulou.test.member.po.MemUserExtInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemUserExtInfoMapper {
    long countByExample(MemUserExtInfoExample example);

    int deleteByExample(MemUserExtInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MemUserExtInfo record);

    int insertSelective(MemUserExtInfo record);

    List<MemUserExtInfo> selectByExample(MemUserExtInfoExample example);

    MemUserExtInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MemUserExtInfo record, @Param("example") MemUserExtInfoExample example);

    int updateByExample(@Param("record") MemUserExtInfo record, @Param("example") MemUserExtInfoExample example);

    int updateByPrimaryKeySelective(MemUserExtInfo record);

    int updateByPrimaryKey(MemUserExtInfo record);
}