package com.shiqkuangsan.mycustomviews.bean;

import com.shiqkuangsan.mycustomviews.utils.dialogList.MiListInterface;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/5/28
 * 内容：
 * 最后修改：
 */
public class DialogTestBean implements MiListInterface {
    private String userName;
    private String userSex;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public DialogTestBean(String userName, String userSex) {
        this.userName = userName;
        this.userSex = userSex;
    }

    @Override
    public String getShowData() {
        return userName;
    }
}
