package com.gulou.test.member.service.dao;

import com.google.common.collect.Lists;
import com.gulou.test.common.BaseTest;
import com.gulou.test.member.po.MemUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gulou on 2018/8/1.
 */
@Transactional
@Slf4j
public class MemUserDaoServiceTest extends BaseTest{

    private static final Long USER_ID = 123456789L;
    private static final String mobileArea = "86";
    private static final String mobileNum = "86";
    private static final String email = "59595959@qq.com";

    @Autowired
    private MemUserDaoService userDaoService;


    @Test
    public void queryByMobiles() throws Exception {
        List<String> mobiles = Lists.newArrayList();
        mobiles.add("86123");
        mobiles.add("86124");
        List<MemUser> memUsers = userDaoService.queryByMobiles(mobiles);
        log.info("users:{}",toJson(memUsers));
    }
}