package com.dj.studyframe.db;

import java.util.List;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/17
 */
public class FileDao extends BaseDao {
    @Override
    protected String createTable() {
        return "create table if not exists tb_file(path varchar(20),addTime var char(10))";
    }

    @Override
    public int delete(Object where) {
        return 0;
    }

    @Override
    public List query(Object where) {
        return null;
    }

    @Override
    public List query(Object where, String orderBy, Integer startIndex, Integer limit) {
        return null;
    }

    @Override
    public List query(String sql) {
        return null;
    }
}
