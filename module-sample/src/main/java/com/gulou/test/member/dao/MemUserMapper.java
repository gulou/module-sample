package com.gulou.test.member.dao;

import com.gulou.test.member.po.MemUser;
import com.gulou.test.member.po.MemUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MemUserMapper {
    long countByExample(MemUserExample example);

    int deleteByExample(MemUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MemUser record);

    int insertSelective(MemUser record);

    List<MemUser> selectByExample(MemUserExample example);

    MemUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MemUser record, @Param("example") MemUserExample example);

    int updateByExample(@Param("record") MemUser record, @Param("example") MemUserExample example);

    int updateByPrimaryKeySelective(MemUser record);

    int updateByPrimaryKey(MemUser record);
}