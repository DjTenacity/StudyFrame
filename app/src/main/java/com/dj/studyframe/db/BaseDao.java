package com.dj.studyframe.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.dj.studyframe.db.annotion.DBFiled;
import com.dj.studyframe.db.annotion.DbTable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/15
 */
public abstract class BaseDao<T> implements IBaseDao<T> {
    /**
     * 持有数据库操作类的应用
     */
    private SQLiteDatabase database;
    /**
     * 保证实例化一次
     */
    private boolean isInit = false;
    /**
     * 持有操作数据库表所对应的java类型
     */
    private Class<T> entityClass;

    private String tableName;
    /**
     * 维护着,表名与类的成员变量名的映射关系,
     * key-->表名
     * value-->Field
     **/
    private HashMap<String, Field> cacheMap;

    /**
     * 实例化一次
     **/
    protected synchronized boolean init(Class<T> entity, SQLiteDatabase sqLiteDatabase) {

        if (!isInit) {
            entityClass = entity;
            database = sqLiteDatabase;
            //通过注解获取到表名,所以说注解只是提供信息
            if (entity.getAnnotation(DbTable.class) == null) {
                tableName = entity.getClass().getSimpleName();
            } else {
                tableName = entity.getAnnotation(DbTable.class).value();
            }
            if (!database.isOpen()) {
                return false;
            }
            if (!TextUtils.isEmpty(createTable())) {
                database.execSQL(createTable());
            }
            cacheMap = new HashMap<>();
            initCacheMap();

            isInit = true;
        }
        return isInit;
    }

    /**
     * 维护映射关系
     */
    public void initCacheMap() {
         /*
        第一条数据  查0个数据
         */
        String sql = "select * from " + this.tableName + " limit 1 , 0";
        //String sql = "select * from" + this.tableName + "limit 1 , 0";-->报错
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, null);
            /**标的列名数组**/
            String[] columnNames = cursor.getColumnNames();

            Field[] columnFields = entityClass.getFields();

            for (Field field : columnFields) {
                field.setAccessible(true);
            }
            /**开始找对应关系,级联可以实现,不过..复杂*/
            for (String columnName : columnNames) {
                /**如果找到对应的field就赋值给他*/
                Field columnField = null;
                for (Field field : columnFields) {
                    String fieldName = null;
                    if (field.getAnnotation(DBFiled.class) != null) {
                        fieldName = field.getAnnotation(DBFiled.class).value();
                    } else {
                        fieldName = field.getName();
                    }
                    /**如果表的列名等于了成员变领的注解名字**/
                    if (columnName.equals(fieldName)) {
                        columnField = field;
                        break;
                    }
                }
                //找到了对应关系
                if (columnField != null) {
                    cacheMap.put(columnName, columnField);
                }
            }
        } catch (Exception e) {
        } finally {
            cursor.close();
        }
    }

    private Map<String, String> getValues(T entity) {

        HashMap<String, String> result = new HashMap<>();
        Iterator fieldsIterator = cacheMap.values().iterator();
        /**循环遍历 映射map 的Filed**/
        while (fieldsIterator.hasNext()) {
            /***/
            Field colmunToFiled = (Field) fieldsIterator.next();
            String cacheKey = null;
            String cacheValue = null;
            if (colmunToFiled.getAnnotation(DBFiled.class) == null) {
                cacheKey = colmunToFiled.getName();
            } else {
                cacheKey = colmunToFiled.getAnnotation(DBFiled.class).value();
            }
            try {
                if (null == colmunToFiled.get(entity)) {
                    continue;
                }
                cacheValue = colmunToFiled.get(entity).toString();

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            result.put(cacheKey, cacheValue);
        }
        return result;
    }

    @Override
    public Long insert(T entity) {
        Map<String, String> map = getValues(entity);
        ContentValues values = getContentValues(map);
        Long result = database.insert(tableName, null, values);
        Log.w("insertRESULT", result + "result");

        return result;
    }

    /**
     * 将map转换成contentValues
     */
    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        Set keys = map.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(key);
            if (value != null) {
                contentValues.put(key, value);
            }
        }
        return contentValues;
    }

    @Override
    public int updata(T entity, T where) {
        int result = -1;
        Map values = getValues(entity);
        /**将条件对象转换map*/
        Map whereMap = getValues(where);
        Condition condition = new Condition(whereMap);
        ContentValues contentValues = getContentValues(values);
        result = database.update(tableName, contentValues, condition.getWhereClause(), condition.getWhereArgs());

        return result;
    }


    public int delete(T where) {
        Map map=getValues(where);

        Condition condition=new Condition(map);
        /**
         * id=1 数据
         * id=?      new String[]{String.value(1)}
         */
        int result=database.delete(tableName,condition.getWhereClause(),condition.getWhereArgs());
        Log.w("deleteRESULT", result + "result");

        return result;
    }

    public int delete2(T where) {
        Map map = getValues(where);
        Condition condition = new Condition(map);
        /**
         * id=1 数据
         * id=? new String[]{String.valuse(1)}*/
        int result = database.delete(tableName, condition.getWhereClause(), condition.getWhereArgs());
        Log.w("deleteRESULT", result + "result");

        return result;
    }

    @Override
    public List<T> query(T where) {
        Log.w("queryRESULT", "query0result");

        return query(where, null, null, null);
    }

    /**
     * 查
     */
    @Override
    public List<T> query(T where, String orderBy, Integer startIndex, Integer limit) {
        Map map = getValues(where);
        //限制条件语句
        String limitStr = null;
        if (startIndex != null && limit != null) {
            limitStr = startIndex + " , " + limit;
        }
        Log.w("queryRESULT", "query0result");

        Condition condition = new Condition(map);
        Cursor cursor = database.query(tableName, null, condition.getWhereClause(), condition.getWhereArgs(),
                null, null, orderBy, limitStr);

        Log.w("queryRESULT", "query1result");

        List<T> result = getResult(cursor, where);

        return result;
    }

    //内连接,两个数据库通过字段连接,拿第一数据库的查询结果去查第二个数据库的
    private List<T> getResult(Cursor cursor, T where) {
        Log.w("queryRESULT", "query2result");

        ArrayList list = new ArrayList();
        Object item;
        while (cursor.moveToNext()) {
            try {
                item = where.getClass().newInstance();
                /**
                 * 列名 name
                 * 成员变量名 Field
                 * **/
                Iterator iterator = cacheMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    /**得到列名*/
                    String colomunName = (String) entry.getKey();
                    /**然后以列名拿到 列名在游标的位置*/
                    Integer columnIndex = cursor.getColumnIndex(colomunName);

                    Field field = (Field) entry.getValue();
                    Class type = field.getType();
                    if (columnIndex != -1) {
                        if (type == String.class) {
                            //反射创建方式赋值
                            String content = cursor.getString(columnIndex);
                            field.set(item, content);
                        } else if (type == Double.class) {
                            field.set(item, cursor.getDouble(columnIndex));
                        } else if (type == Long.class) {
                            field.set(item, cursor.getLong(columnIndex));
                        } else if (type == Integer.class) {
                            field.set(item, cursor.getInt(columnIndex));
                        } else if (type == byte[].class) {
                            field.set(item, cursor.getBlob(columnIndex));
                        } else {
                            /**不支持的类型*/
                            continue;
                        }
                    }
                }

                list.add(item);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }

        }
        Log.w("queryRESULT", "query" + list.size() + "result");

        return list;
    }

    private List<T> getResult2(Cursor cursor, T where) {
        ArrayList list = new ArrayList();

        Object item;
        while (cursor.moveToNext()) {
            try {
                item = where.getClass().newInstance();
                /**
                 * 列名  name
                 * 成员变量名  Filed;
                 */
                Iterator iterator = cacheMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    /**
                     * 得到列名
                     */
                    String colomunName = (String) entry.getKey();
                    /**
                     * 然后以列名拿到  列名在游标的位子
                     */
                    Integer colmunIndex = cursor.getColumnIndex(colomunName);

                    Field field = (Field) entry.getValue();

                    Class type = field.getType();
                    if (colmunIndex != -1) {
                        if (type == String.class) {
                            //反射方式赋值
                            field.set(item, cursor.getString(colmunIndex));
                        } else if (type == Double.class) {
                            field.set(item, cursor.getDouble(colmunIndex));
                        } else if (type == Integer.class) {
                            field.set(item, cursor.getInt(colmunIndex));
                        } else if (type == Long.class) {
                            field.set(item, cursor.getLong(colmunIndex));
                        } else if (type == byte[].class) {
                            field.set(item, cursor.getBlob(colmunIndex));
                            /*
                            不支持的类型
                             */
                        } else {
                            continue;
                        }
                    }

                }
                list.add(item);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return list;
    }


    /**
     * 创建表
     */
    protected abstract String createTable();

    /**
     * 封装修改语句
     **/
    class Condition {
        /**
         * 查询条件
         * name=? && password =?
         */
        private String whereClause;
        private String[] whereArgs;


        public Condition(Map<String, String> whereClause) {
            ArrayList list = new ArrayList();
            StringBuilder stringBuilder = new StringBuilder();

            //恒成立,把前面语句和后面语句连接起来,防止查询语句第一个字就是 and
            stringBuilder.append(" 1=1 ");
            Set keys = whereClause.keySet();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = whereClause.get(key);

                if (value != null) {
                    /**拼接 sql条件查询语句
                     * 1=1 and name=? and password=?
                     */
                    stringBuilder.append(" and " + key + " =? ");
                    /**
                     * ?---->value
                     */
                    list.add(value);
                }
            }
            //得到查询的条件
            this.whereClause = stringBuilder.toString();
            this.whereArgs = (String[]) list.toArray(new String[list.size()]);
        }

        public String getWhereClause() {
            return whereClause;
        }

        public String[] getWhereArgs() {
            return whereArgs;
        }
    }
}
