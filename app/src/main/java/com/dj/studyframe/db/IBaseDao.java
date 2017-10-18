package com.dj.studyframe.db;

import java.util.List;

/**
 * Comment:数据库,
 * 升级 --> 将旧表备份重命名,然后再将以前表的数据存到新表里面去,再将旧表的数据进行一个拷贝
 *
 * @param <T> the type parameter
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/15
 */
public interface IBaseDao<T> {

    /**
     * Insert long.
     * 插入数据
     *
     * @param entity the entity
     * @return the long
     */
    Long insert(T entity);

    /**
     * 改
     *
     * @param entity the entity
     * @param where  the where
     * @return the long
     */
    int updata(T entity, T where);

    /**
     * 删
     *
     * @param where the where
     * @return the int
     */
    int delete(T where);

    /**
     * 查
     *
     * @param where the where
     * @return the list
     */
    List<T> query(T where);

    /**
     * Query list.
     *
     * @param where      the where
     * @param orderBy    排序方式
     * @param startIndex the start index
     * @param limit      限制
     * @return the list
     */
    List<T> query(T where, String orderBy, Integer startIndex, Integer limit);

    //void query(String sql);,多表查询
}
