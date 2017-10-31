package com.dj.studyframe.http.interfaces;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/19
 */
public interface IDataListener<M> {

    /**
     * 回调结果给调用层
     */
    void onSuccess(M m);

    void onErro();
}
