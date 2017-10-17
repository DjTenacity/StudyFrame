package com.dj.studyframe.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/15
 */
public class BaseDaoFactory {

    private String sqliteDatabasePath;

    private SQLiteDatabase sqLiteDatabase;

    private static BaseDaoFactory instance = new BaseDaoFactory();

    /**
     * 单例
     */
    public BaseDaoFactory() {
        sqliteDatabasePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/user.db";
        //getDataDirectory().getAbsolutePath()
        openDatabase();
    }

    /***打开或则创建数据库*/
    private void openDatabase() {
        this.sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqliteDatabasePath, null);
    }

    /**
     * Gets data helper.
     *
     * @param <T>         继承BaseDao
     * @param <M>         插入的类型的对象
     * @param clazz       the clazz
     * @param entityClazz the entity clazz
     * @return the data helper
     */
    public synchronized <T extends BaseDao<M>, M> T getDataHelper(Class<T> clazz, Class<M> entityClazz) {

        BaseDao baseDao = null;
        try {
            baseDao = clazz.newInstance();
            baseDao.init(entityClazz, sqLiteDatabase);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T) baseDao;
    }

    public static BaseDaoFactory getInstance() {
        return instance;
    }
}
