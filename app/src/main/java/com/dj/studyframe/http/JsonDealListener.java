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

/**
 * Comment:
 * M --->对应响应类
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/19
 */
public class JsonDealListener<M> implements IHttpListener {

    private Class<M> response;
    /**
     * 回调调用层的接口
     **/
    private IDataListener<M> dataListener;

    Handler handler = new Handler(Looper.getMainLooper());

    public JsonDealListener(Class<M> response, IDataListener<M> dataListener) {
        this.response = response;
        this.dataListener = dataListener;
    }

    @Override
    public void onSuccess(HttpEntity httpEntity) {
        InputStream inputStream = null;
        try {
            inputStream = httpEntity.getContent();
            /**得到网络返回的数据
             * 子线程*/
            String content = getContent(inputStream);
            final M m = JSON.parseObject(content, response);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    dataListener.onSuccess(m);
                }
            });

        } catch (IOException e) {
            dataListener.onFail();
        }
    }

    @Override
    public void onFail() {

    }

    private String getContent(InputStream inputStream) {
        String content = null;
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
                dataListener.onFail();
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
            dataListener.onFail();
        }
        return content;
    }

}
