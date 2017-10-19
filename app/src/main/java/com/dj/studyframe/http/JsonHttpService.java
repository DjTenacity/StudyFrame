package com.dj.studyframe.http;

import com.dj.studyframe.http.interfaces.IHttpListener;
import com.dj.studyframe.http.interfaces.IHttpService;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/19
 */
public class JsonHttpService implements IHttpService {
    private IHttpListener httpListener;
    private HttpClient httpClient = new DefaultHttpClient();
    private HttpPost httpPost;

    private String url;
    private byte[] requestData;

    /**
     * httpClient 获取网络的回调
     */
    private HttpResponseHandler httpResponseHandler;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void excute() {/**线程中执行*/
        httpPost = new HttpPost(url);
        ByteArrayEntity byteArrayEntity = new ByteArrayEntity(requestData);
        httpPost.setEntity(byteArrayEntity);

        try {
            httpClient.execute(httpPost, httpResponseHandler);
        } catch (IOException e) {
            httpListener.onFail();
        }
    }

    @Override
    public void setHttpListener(IHttpListener listener) {
        this.httpListener = listener;
    }

    @Override
    public void setRequestData(byte[] requestData) {
        this.requestData = requestData;
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
