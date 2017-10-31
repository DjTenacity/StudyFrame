package com.dj.studyframe.db;

import com.dj.studyframe.Photo;

import java.util.List;

public class PhotoDao  extends BaseDao<Photo>{

    @Override
    public List<Photo> query(String sql) {
        return null;
    }

    @Override
    public String createTable() {
        return "create table if not exists tb_photo(\n" +
                "                time TEXT,\n" +
                "                path TEXT,\n" +
                "                to_user TEXT\n" +
                "                )";
    }
}
