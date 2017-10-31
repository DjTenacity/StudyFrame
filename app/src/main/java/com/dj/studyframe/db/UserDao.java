package com.dj.studyframe.db;

import android.util.Log;

import com.dj.studyframe.User;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/15
 */
public class UserDao extends BaseDao<User>  {

    @Override
    public String createTable() {
        return "create table if not exists tb_user( name TEXT, password TEXT, user_id Text,status Integer);";
    }

    @Override
    public Long insert(User entity) {
        List<User> list = query(new User());
        User where = null;
        for (User user : list) {
            where = new User();
            where.setUser_id(user.getUser_id());
            user.setStatus(0);
            Log.i(TAG, "用户" + user.getName() + "更改为未登录状态");
            update(user, where);
        }
        Log.i(TAG, "用户" + entity.getName() + "登录");
        entity.setStatus(1);
        return super.insert(entity);
    }

    @Override
    public List<User> query(String sql) {
        return null;
    }

    /**
     * 得到当前登录的User
     *
     * @return
     */
    public User getCurrentUser() {
        User user = new User();
        user.setStatus(1);
        List<User> list = query(user);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
//    @Override
//    protected String createTable() {
//       // return "create table if not exists tb_user(user_Id int,name varchar(20),password var char(10))";
//        return "create table if not exists tb_user(user_Id int,name varchar(20),password varchar(10))";
//    }
//
//    @Override
//    public int delete(Object where) {
//        return 0;
//    }
//
//    @Override
//    public List query(Object where) {
//        return null;
//    }
//
//    @Override
//    public List query(Object where, String orderBy, Integer startIndex, Integer limit) {
//        return null;
//    }
}
