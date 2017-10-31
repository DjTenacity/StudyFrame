package com.dj.studyframe.http.interfaces;

import java.util.Map;

/**
 * Comment:获取网络
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/19
 */
public interface IHttpService {

    /**
     * 设置url
     */
    void setUrl(String url);

    /**
     * 执行获取网络
     */
    void excute();

    /**
     * 设置处理接口
     */
    void setHttpListener(IHttpListener listener);

    /**
     * 设置请求参数
     * String  1
     * byte[]  2
     */
    void setRequestData(byte[] requestData);

    void pause();

    /**
     * 以下方法是额外添加的
     * 获取请求头的map
     */
    Map<String, String> getHttpHeardMap();

    boolean cancle();

    boolean isCancle();

    boolean isPause();
}
