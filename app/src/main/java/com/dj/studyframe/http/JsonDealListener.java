package com.dj.studyframe.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.dj.studyframe.http.interfaces.IDataListener;
import com.dj.studyframe.http.interfaces.IHttpListener;

import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Comment:
 * M --->对应响应类
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/19
 */
public class JsonDealListener<M> implements IHttpListener {
    private Class<M> responceClass;
    private IDataListener<M> dataListener;
    /**
     * 获取主线程的Handle
     * 通过handle切换至主线程
     */
    Handler handler=new Handler(Looper.getMainLooper());

    public JsonDealListener(Class<M> responceClass, IDataListener<M> dataListener) {
        this.responceClass = responceClass;
        this.dataListener = dataListener;
    }

    @Override
    public void onSuccess(HttpEntity httpEntity) {
        InputStream inputStream=null;
        try {
            inputStream=httpEntity.getContent();
            String content=getContent(inputStream);
            final M responce= JSON.parseObject(content,responceClass);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dataListener.onSuccess(responce);
                }
            });
        } catch (IOException e) {
            dataListener.onErro();
        }

    }

    @Override
    public void onFail() {

    }

    @Override
    public void addHttpHeader(Map<String, String> headerMap) {

    }




    private String getContent(InputStream inputStream) {
        String content=null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder sb = new StringBuilder();

            String line = null;

            try {

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

            } catch (IOException e) {

                System.out.println("Error=" + e.toString());
                dataListener.onErro();
            } finally {

                try {

                    inputStream.close();

                } catch (IOException e) {

                    System.out.println("Error=" + e.toString());

                }

            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
            dataListener.onErro();
        }
        return content;
    }
}