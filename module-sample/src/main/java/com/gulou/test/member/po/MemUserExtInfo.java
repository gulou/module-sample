package com.gulou.test.member.po;

/**
 * @author 
 */
public class MemUserExtInfo {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 资金密码
     */
    private String tradePwd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTradePwd() {
        return tradePwd;
    }

    public void setTradePwd(String tradePwd) {
        this.tradePwd = tradePwd;
    }
}