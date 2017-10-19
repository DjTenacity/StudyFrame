package com.dj.studyframe.http.interfaces;

import org.apache.http.HttpEntity;

/**
 * Comment:处理结果 回调
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/19
 */
public interface IHttpListener {

    /**
     * 网络访问框架处调用
     */
    void onSuccess(HttpEntity httpEntity);

    void onFail();
}
