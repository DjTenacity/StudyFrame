package com.dj.studyframe.db.entity;

import com.dj.studyframe.db.annotion.DbTable;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/17
 */
@DbTable("tb_file")
public class FileBean {

    public FileBean(String addTime, String path) {
        this.addTime = addTime;
        this.path = path;
    }

    public String addTime;
    public String path;

}
