package com.greendao.demo.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/2/26
 * 内容：
 * 最后修改：
 */

@Entity
public class GreenDaoBean {
    @Id
    Long GreenDaoId;//主键
    @Index
    Integer GreenDaoIndex;//索引

    String GreenDaoName;

    @Override
    public String toString() {
        return "GreenDaoBean{" +
                "GreenDaoId=" + GreenDaoId +
                ", GreenDaoIndex=" + GreenDaoIndex +
                ", GreenDaoName='" + GreenDaoName + '\'' +
                '}';
    }

    public String getGreenDaoName() {
        return this.GreenDaoName;
    }

    public void setGreenDaoName(String GreenDaoName) {
        this.GreenDaoName = GreenDaoName;
    }

    public Integer getGreenDaoIndex() {
        return this.GreenDaoIndex;
    }

    public void setGreenDaoIndex(Integer GreenDaoIndex) {
        this.GreenDaoIndex = GreenDaoIndex;
    }

    public Long getGreenDaoId() {
        return this.GreenDaoId;
    }

    public void setGreenDaoId(Long GreenDaoId) {
        this.GreenDaoId = GreenDaoId;
    }

    @Generated(hash = 274596026)
    public GreenDaoBean(Long GreenDaoId, Integer GreenDaoIndex, String GreenDaoName) {
        this.GreenDaoId = GreenDaoId;
        this.GreenDaoIndex = GreenDaoIndex;
        this.GreenDaoName = GreenDaoName;
    }

    @Generated(hash = 826843181)
    public GreenDaoBean() {
    }

}
