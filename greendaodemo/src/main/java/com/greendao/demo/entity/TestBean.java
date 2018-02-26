package com.greendao.demo.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/2/26
 * 内容：数据库升级测试类
 * 最后修改：
 */
@Entity
public class TestBean {

    @Id
    Long TestId;
    String UserName;

    @Generated(hash = 939263048)
    public TestBean(Long TestId, String UserName) {
        this.TestId = TestId;
        this.UserName = UserName;
    }

    @Generated(hash = 2087637710)
    public TestBean() {
    }

    public Long getTestId() {
        return this.TestId;
    }

    public void setTestId(Long TestId) {
        this.TestId = TestId;
    }

    public String getUserName() {
        return this.UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
}
