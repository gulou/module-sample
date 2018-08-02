package com.gulou.test.member.dao.ext;

import com.gulou.test.member.po.MemUser;

import java.util.List;

public interface MemUserExtMapper {

    List<MemUser> selectByMobiles(List<String> mobiles);
}