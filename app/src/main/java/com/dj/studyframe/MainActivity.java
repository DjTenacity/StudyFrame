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
import com.dj.studyframe.http.Volley;
import com.dj.studyframe.http.interfaces.IDataListener;

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

        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User where = new User();
                where.setName("gdjLove");
                where.setUser_Id(6);

                List<User> userList = baseDao.query(where);
                Log.w("queryRESULT", "查询数据");

                if (userList != null && userList.size() > 0) {

                    Log.w("USER", "查询到  " + userList.size() + "  条数据");
                    for (User user2 : userList) {
                        Log.w("USER", user2.toString());
                    }
                }
            }
        });
    }

    public void save(View v) {
        User user = new User("gdj", "ps123456");
        User user2 = new User();
        user2.password = "22222";
        user2.name = "woaini";
        user2.setUser_Id(2);

        User user3 = new User("gdjLove", "ps123456");
        user3.setUser_Id(6);

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
        user.setUser_Id(5);
        baseDao.updata(user, where);

    }

    public void delete(View view) {
        User user2 = new User();
        user2.setName("lovedj");
        Log.i("USER", "删除数据" + user2.toString());

        baseDao.delete(user2);
    }

    public void queryList(View view) {

    }

    public  static  final String url="http://v.juhe.cn/toutiao/index?type=top&key=29da5e8be9ba88b932394b7261092f71";

    void http() {//适合小数据,大并发

        User user = new User();

        Volley.sendRequset(user, "url", String.class, new IDataListener() {
            @Override
            public void onSuccess(Object o) {

            }

            @Override
            public void onFail() {

            }
        });
    }
}
