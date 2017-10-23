package com.dj.studyframe.http.download;

import android.util.Log;

import com.dj.studyframe.http.interfaces.IHttpListener;
import com.dj.studyframe.http.interfaces.IHttpService;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/22
 */
public class FileDownHttpService implements IHttpService {
    private HttpClient httpClient = new DefaultHttpClient();
    private String url;
    /**
     * 即将添加到请求头的信息
     */
    private Map<String, String> headerMap = Collections.synchronizedMap(new HashMap<String, String>());
    /**
     * 含有请求处理的接口
     */
    private IHttpListener httpListener;

    private byte[] requestData;
    /**
     * httpClient获取网络的回调
     */
    private HttpResponseHandler httpRespnceHandler = new HttpResponseHandler();
    private HttpGet httpGet;


    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void excute() {
        httpGet = new HttpGet(url);
        constrcutHeader();
//        ByteArrayEntity byteArrayEntity=new ByteArrayEntity(requestDate);
//        httpPost.setEntity(byteArrayEntity);
        try {
            httpClient.execute(httpGet, httpRespnceHandler);
        } catch (IOException e) {
            httpListener.onFail();
        }
    }

    private void constrcutHeader() {
        Iterator iterator = headerMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = headerMap.get(key);
            Log.w("请求头信息key", key + "value" + value);

            httpGet.addHeader(key, value);
        }
    }

    //将headerMap暴露到DownLoadListener


    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    @Override
    public void setHttpListener(IHttpListener listener) {
        this.httpListener = listener;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
    }

    @Override
    public void pause() {

    }

    @Override
    public Map<String, String> getHttpHeardMap() {
        return null;
    }

    @Override
    public boolean cancle() {
        return false;
    }

    @Override
    public boolean isCancle() {
        return false;
    }


    @Override
    public boolean isPause() {
        return false;
    }

    class HttpResponseHandler extends BasicResponseHandler {
        @Override
        public String handleResponse(HttpResponse response) throws ClientProtocolException {
            //响应码
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                httpListener.onSuccess(response.getEntity());
            } else {
                //404,500等
                httpListener.onFail();
            }

            return null;
        }
    }
}
