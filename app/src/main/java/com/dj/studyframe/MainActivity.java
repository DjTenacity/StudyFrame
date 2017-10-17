package com.dj.studyframe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dj.studyframe.db.BaseDaoFactory;
import com.dj.studyframe.db.FileDao;
import com.dj.studyframe.db.IBaseDao;
import com.dj.studyframe.db.UserDao;
import com.dj.studyframe.db.entity.FileBean;
import com.dj.studyframe.db.entity.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    IBaseDao<User> baseDao;
    IBaseDao<FileBean> fileDao;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studyDbFrame();
    }

    private void studyDbFrame() {
        baseDao = BaseDaoFactory.getInstance().getDataHelper(UserDao.class, User.class);
        fileDao = BaseDaoFactory.getInstance().getDataHelper(FileDao.class, FileBean.class);

    }

    public void save(View v) {
        User user = new User("gdj", "ps123456");
        User user2 = new User();
        user2.password = "22222";
        user2.name = "woaini";
        User user3 = new User("gdjLove", "ps123456");

        baseDao.insert(user);
        baseDao.insert(user2);
        baseDao.insert(user3);

        FileBean fileBean = new FileBean("data/data/path", "2017-1017-2155");
        fileDao.insert(fileBean);
    }

    public void updata(View v) {
        //条件,只要满足,就会改
        User where = new User();
        where.setName("woaini");

        User user = new User("lovedj", "ps12345678");
        baseDao.updata(user, where);

    }

    public void delete(View view) {
        User user2 = new User();
        user2.name = "woaini";

        baseDao.delete(user2);
    }

    public void query(View view) {
        User user = new User("lovedj", "ps12345678");



        List<User> userList = baseDao.query(user);
        for (User user2 : userList) {
            Log.w("USER", user2.toString());
        }
    }


}
