package com.shiqkuangsan.mycustomviews.bean;

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

    private int _id;

    @PrimaryKey
    @Required   // 表示该字段需要被赋值
    private String name;
    @Required
    private String age;

    private String hospital;
    private String skill;
    private int gender;

    public Doctor() {
    }

    public Doctor( String name, String age, String hospital, String skill) {
        this.name = name;
        this.age = age;
        this.hospital = hospital;
        this.skill = skill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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
