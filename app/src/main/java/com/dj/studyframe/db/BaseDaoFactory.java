package com.dj.studyframe.db;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    //-------------添加-------------
    private SQLiteDatabase userDatabase;
    private Map<String, BaseDao> map = Collections.synchronizedMap(new HashMap<String, BaseDao>());

    private static BaseDaoFactory instance = new BaseDaoFactory();

    /**
     * 单例
     */
    private BaseDaoFactory() {
        File file = new File(Environment.getExternalStorageDirectory(), "update");
        if (!file.exists()) {
            file.mkdirs();
        }
        sqliteDatabasePath = file.getAbsolutePath() + "/user.db";
        openDatabase();
    }

    /***打开或者创建数据库*/
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
        if (map.get(clazz.getSimpleName()) != null) {
            return (T) map.get(clazz.getSimpleName());
        }
        try {
            baseDao = clazz.newInstance();
            baseDao.init(entityClazz, sqLiteDatabase);
            map.put(clazz.getSimpleName(), baseDao);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return (T) baseDao;
    }

    public synchronized <T extends BaseDao<M>, M> T getUserHelper(Class<T> clazz, Class<M> entityClass) {
        userDatabase = SQLiteDatabase.openOrCreateDatabase(PrivateDataBaseEnums.database.getValue(), null);
        BaseDao baseDao = null;
        try {
            baseDao = clazz.newInstance();
            baseDao.init(entityClass, userDatabase);
            map.put(clazz.getSimpleName(), baseDao);
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
