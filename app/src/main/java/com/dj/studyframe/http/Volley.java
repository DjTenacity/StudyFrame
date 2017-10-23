package com.dj.studyframe.http;

import com.dj.studyframe.http.interfaces.IDataListener;
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
public class Volley {
    /**
     * Send requset.
     *
     * @param <T> 请求参数类型
     * @param <M> 响应参数类型
     */
    public static <T, M> void sendRequset(T requestInf, String url,
                                          Class<M> response, IDataListener dataListener) {
        RequestHolder<T> requestHolder = new RequestHolder<>();
        requestHolder.setUrl(url);
        /**策略模式*/
        IHttpService httpService = new JsonHttpService();
        IHttpListener httpListener = new JsonDealListener<>(response, dataListener);

        requestHolder.setHttpService(httpService);
        requestHolder.setHttpListener(httpListener);
        requestHolder.setRequestInfo(requestInf);

        HttpTask httpTask = new HttpTask<>(requestHolder);
        try {
            ThreadPoolManager.getInstance().excute(new FutureTask<Object>(httpTask, null));
        } catch (InterruptedException e) {
            dataListener.onFail();
        }
    }
}
