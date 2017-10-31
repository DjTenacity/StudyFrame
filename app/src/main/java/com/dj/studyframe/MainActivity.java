package com.dj.studyframe;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.dj.studyframe.db.BaseDaoFactory;
import com.dj.studyframe.db.PhotoDao;
import com.dj.studyframe.db.UserDao;
import com.dj.studyframe.db.update.UpdateManager;
import com.dj.studyframe.http.Volley;
import com.dj.studyframe.http.download.DownFileManager;
import com.dj.studyframe.http.interfaces.IDataListener;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    //IBaseDao<User> baseDao;
    UserDao baseDao;
    UpdateManager updateManager;
    int i = 0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studyDbFrame();
    }

    private void studyDbFrame() {
        baseDao = BaseDaoFactory.getInstance().getDataHelper(UserDao.class, User.class);

        findViewById(R.id.tv1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                User where = new User();
//                where.setName("gdjLove");
//                where.setUser_Id(6);
//                List<User> userList = baseDao.query(where);

                User user = new User();
                user.setName("V00" + (i++));
                user.setPassword("123456");
                user.setName("张DJ" + i);
                user.setUser_id("N000" + i);
                baseDao.insert(user);
                updateManager.checkThisVersionTable(MainActivity.this);

            }
        });
    }


//    public void updata(View v) {
//        //条件,只要满足,就会改
//        User where = new User();
//        where.setName("woaini");
//        User user = new User("lovedj", "ps12345678");
//        user.setUser_Id(5);
//        baseDao.update(user, where);
//    }

    public void insert(View view) {
        Photo photo = new Photo();
        photo.setPath("data/data/my.jpg");
        java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        photo.setTime(dateFormat.format(new Date()));
        PhotoDao photoDao = BaseDaoFactory.getInstance().getUserHelper(PhotoDao.class, Photo.class);
        photoDao.insert(photo);
    }

    public void write(View view) {
        /**
         * 写入版本
         */
        updateManager.saveVersionInfo(this, "V002");

    }

    public void update(View view) {
        updateManager.checkThisVersionTable(this);

        updateManager.startUpdateDb(this);
    }

    public void delete(View view) {
        User user2 = new User();
        user2.setName("lovedj");
        Log.i("USER", "删除数据" + user2.toString());

        baseDao.delete(user2);
    }

    public void queryList(View view) {

    }

    public static final String url = "http://v.juhe.cn/toutiao/index?type=top&key=29da5e8be9ba88b932394b7261092f71";

    void http() {//适合小数据,大并发

        Volley.sendRequest(null, url, NewsPager.class, new IDataListener<NewsPager>() {
            @Override
            public void onSuccess(NewsPager loginRespense) {
                Log.i("LoveDj", loginRespense.toString());
            }

            @Override
            public void onErro() {
                Log.i("LoveDj", "获取失败");

            }
        });


        DownFileManager downFileService = new DownFileManager();
        downFileService.download("http://gdown.baidu.com/data/wisegame/8be18d2c0dc8a9c9/WPSOffice_177.apk");

    }
}
