package com.gulou.test.member.service.dao;

import com.gulou.test.member.dao.MemUserExtInfoMapper;
import com.gulou.test.member.dao.MemUserMapper;
import com.gulou.test.member.dao.ext.MemUserExtMapper;
import com.gulou.test.member.po.MemUser;
import com.gulou.test.member.po.MemUserExample;
import com.gulou.test.member.po.MemUserExtInfo;
import com.gulou.test.member.po.MemUserExtInfoExample;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>用户信息</p>
 *
 * @author gulou
 * @version V1.0
 * @Date 2018/08/01 19:53
 */

@Service
public class MemUserDaoService {

    @Autowired
    private MemUserMapper memUserMapper;
    @Autowired
    private MemUserExtMapper memUserExtMapper;
    @Autowired
    private MemUserExtInfoMapper userExtInfoMapper;

    //插入记录
    public int insert(MemUser memUser){
        return memUserMapper.insert(memUser) ;
    }

    //根据主键更新记录
    public int updateById(MemUser memUser){
        return memUserMapper.updateByPrimaryKeySelective(memUser) ;
    }

    //根据用户id更新记录
    public int updateByUserId(Long userId,MemUser memUser){

        MemUserExample userExample = new MemUserExample();
        userExample.createCriteria().andUserIdEqualTo(userId);

        return memUserMapper.updateByExampleSelective(memUser,userExample);
    }

    //根据用户id查询
    public MemUser queryByUserId(Long userId){

        MemUserExample userExample = new MemUserExample();
        userExample.createCriteria().andUserIdEqualTo(userId);

        List<MemUser> userList = memUserMapper.selectByExample(userExample);

        if(!CollectionUtils.isEmpty(userList)){
            return userList.get(0);
        }
        return null;
    }

    //根据手机号查询
    public MemUser queryByMobile(String mobileArea,String mobileNum){

        MemUserExample userExample = new MemUserExample();
        userExample.createCriteria()
                .andMobileAreaEqualTo(mobileArea)
                .andMobileNumEqualTo(mobileNum);

        List<MemUser> userList = memUserMapper.selectByExample(userExample);

        if(!CollectionUtils.isEmpty(userList)){
            return userList.get(0);
        }
        return null;
    }

    //根据邮箱查询
    public MemUser queryByEmail(String email){

        MemUserExample userExample = new MemUserExample();
        userExample.createCriteria()
                .andEmailEqualTo(email);

        List<MemUser> userList = memUserMapper.selectByExample(userExample);

        if(!CollectionUtils.isEmpty(userList)){
            return userList.get(0);
        }
        return null;
    }

    //查询扩展信息
    public MemUserExtInfo queryExtraInfo(Long userId){

        MemUserExtInfoExample infoExample = new MemUserExtInfoExample();
        infoExample.createCriteria().andUserIdEqualTo(userId);

        List<MemUserExtInfo> extInfoList = userExtInfoMapper.selectByExample(infoExample);

        if(!CollectionUtils.isEmpty(extInfoList)){
            return extInfoList.get(0);
        }
        return null;
    }

    /**
     * 批量查询
     */

    public List<MemUser> queryByUserId(List<Long> userIds){

        MemUserExample userExample = new MemUserExample();
        userExample.createCriteria().andUserIdIn(userIds);

        return memUserMapper.selectByExample(userExample);
    }

    public List<MemUser> queryByMobiles(List<String> mobiles){

        return memUserExtMapper.selectByMobiles(mobiles);
    }

    public List<MemUser> queryByEmails(List<String> emails){

        MemUserExample userExample = new MemUserExample();
        userExample.createCriteria().andEmailIn(emails);

        return memUserMapper.selectByExample(userExample);
    }
}
