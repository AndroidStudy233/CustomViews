package com.shiqkuangsan.mycustomviews.bean;

import java.security.PrivilegedAction;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by shiqkuangsan on 2017/1/6.
 * <p>
 * ClassName: Doctor
 * Author: shiqkuangsan
 * Description: 医生bean类
 */
public class Doctor extends RealmObject {

    @PrimaryKey
    private int _id;

    @Required
    private String name;

    private int age;
    private String hospital;
    private String skill;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
