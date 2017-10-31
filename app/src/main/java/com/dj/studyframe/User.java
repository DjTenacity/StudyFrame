package com.dj.studyframe;

import com.dj.studyframe.db.annotion.DbTable;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/15
 */
@DbTable("tb_user")
public class User {


    public String name;

    public String password;

    public String user_id;
    public  Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    /**
//     * 必须要有无参数的构造函数,反射调用的就是这个
//     */
//    public User() {
//    }
//
//    public User(String name) {
//        this.name = name;
//    }
//
//    public User(String name, String password) {
//        this.name = name;
//        this.password = password;
//    }
//
//
//    /**
//     * 必须要用int的封装类型,基本数据类型int有默认值->0
//     */
//    public int user_Id = 0;
//    //通过注解找到name
//    @DBFiled("name")
//    public String name;
//
//    @DBFiled("password")
//    public String password;
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Integer getUser_Id() {
//        return user_Id;
//    }
//
//    public void setUser_Id(Integer user_Id) {
//        this.user_Id = user_Id;
//    }


}
