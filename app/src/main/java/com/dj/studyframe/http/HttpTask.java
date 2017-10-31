package com.dj.studyframe.http;

import com.alibaba.fastjson.JSON;
import com.dj.studyframe.http.interfaces.IHttpListener;
import com.dj.studyframe.http.interfaces.IHttpService;

import java.util.concurrent.FutureTask;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/19
 */
public class HttpTask<T> implements Runnable {

    private IHttpService httpService;
    private FutureTask futureTask;
    public HttpTask(RequestHolder<T> requestHolder)
    {
        httpService=requestHolder.getHttpService();
        httpService.setHttpListener(requestHolder.getHttpListener());
        httpService.setUrl(requestHolder.getUrl());
        //增加方法
        IHttpListener httpListener=requestHolder.getHttpListener();
        httpListener.addHttpHeader(httpService.getHttpHeardMap());
        try {
            T request=requestHolder.getRequestInfo();
            if(request!=null)
            {
                String requestInfo= JSON.toJSONString(request);
                httpService.setRequestData(requestInfo.getBytes("UTF-8"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        httpService.excute();
    }
    /**
     * 新增方法
     */
    public void start()
    {
        futureTask=new FutureTask(this,null);
        try {
            ThreadPoolManager.getInstance().execte(futureTask);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * 新增方法
     */
    public  void pause()
    {
        httpService.pause();
        if(futureTask!=null)
        {
            ThreadPoolManager.getInstance().removeTask(futureTask);
        }

    }
}
