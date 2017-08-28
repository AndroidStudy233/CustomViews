package com.greendao.demo.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/*************************************************
 * <p>版权所有：2016-深圳市赛为安全技术服务有限公司</p>
 * <p>项目名称：安全眼</p>
 * <p/>
 * <p>类描述：${todo}(用一句话描述该文件做什么)</p>
 * <p>创建人：余志伟</p>
 * <p>创建时间 : 2017/8/25</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 *********************************/
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "user_name")
    private String  userName;
    @Property(nameInDb = "age")
    private int age;
    @Property(nameInDb = "sex")
    private String sex;
    @Generated(hash = 1887126858)
    public User(Long id, String userName, int age, String sex) {
        this.id = id;
        this.userName = userName;
        this.age = age;
        this.sex = sex;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getSex() {
        return this.sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
}
