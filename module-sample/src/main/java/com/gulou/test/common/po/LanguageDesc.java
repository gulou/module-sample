package com.gulou.test.common.po;

import java.util.Date;

/**
 * @author 
 */
public class LanguageDesc {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 业务编号(用于查询)
     */
    private Long bizNo;

    /**
     * 语言描述id
     */
    private Long languageDescId;

    /**
     * 语言
     */
    private Integer language;

    /**
     * 描述
     */
    private String description;

    /**
     * 额外信息
     */
    private String extraInfo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createAt;

    /**
     * 更新时间
     */
    private Date updateAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBizNo() {
        return bizNo;
    }

    public void setBizNo(Long bizNo) {
        this.bizNo = bizNo;
    }

    public Long getLanguageDescId() {
        return languageDescId;
    }

    public void setLanguageDescId(Long languageDescId) {
        this.languageDescId = languageDescId;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}