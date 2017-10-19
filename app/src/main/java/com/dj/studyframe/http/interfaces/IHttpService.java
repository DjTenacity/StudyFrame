package com.dj.studyframe.http.interfaces;

import org.apache.http.HttpEntity;

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
     */
    void setRequestData(byte[] requestData);

}
