package com.gulou.test.common.po;

/**
 * @author 
 */
public class RespCodeRecord {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 返回码
     */
    private Long code;

    /**
     * 语言描述id
     */
    private Long descId;

    /**
     * 备注
     */
    private String memo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Long getDescId() {
        return descId;
    }

    public void setDescId(Long descId) {
        this.descId = descId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}