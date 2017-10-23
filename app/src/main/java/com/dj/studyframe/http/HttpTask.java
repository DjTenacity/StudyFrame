package com.dj.studyframe.http;

import com.alibaba.fastjson.JSON;
import com.dj.studyframe.http.interfaces.IHttpListener;
import com.dj.studyframe.http.interfaces.IHttpService;

import java.io.UnsupportedEncodingException;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/19
 */
public class HttpTask<T> implements Runnable {

    private IHttpService httpService;

    public HttpTask(RequestHolder<T> requestHolder) {

        httpService = requestHolder.getHttpService();
        httpService.setHttpListener(requestHolder.getHttpListener());
        httpService.setUrl(requestHolder.getUrl());

        //增加方法
        IHttpListener httpListener = requestHolder.getHttpListener();
        httpListener.addHttpHeader(httpService.getHttpHeardMap());

        T request = requestHolder.getRequestInfo();
        if (request != null) {
            String requestInfo = JSON.toJSONString(request);
            try {
                httpService.setRequestData(requestInfo.getBytes("UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void run() {
        httpService.excute();
    }
}
