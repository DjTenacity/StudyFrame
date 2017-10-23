package com.dj.studyframe.http.download;

import android.os.Environment;
import android.util.Log;

import com.dj.studyframe.http.HttpTask;
import com.dj.studyframe.http.RequestHolder;
import com.dj.studyframe.http.ThreadPoolManager;
import com.dj.studyframe.http.interfaces.IHttpListener;
import com.dj.studyframe.http.interfaces.IHttpService;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.FutureTask;

/**
 * Comment:下载文件,根据单一原则,并不放在volley里面
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/22
 */
public class DownFileManager implements IDownloadServiceCallable {
    private static final String TAG = "DownFileManager";
    private byte[] lock = new byte[0];

    /**
     * 下载文件
     */
    public void down(String url) {
        String[] preFixs = url.split("/");
        String afterFix = preFixs[preFixs.length - 1];
        File file = new File(Environment.getExternalStorageDirectory(), afterFix);

        //实例化DownLoadItem
        DownloadItemInfo downloadItemInfo = new DownloadItemInfo(url, file.getAbsolutePath());

        synchronized (lock) {
            //设置请求下载的策略
            IHttpService httpService = new FileDownHttpService();
            Map<String, String> map = httpService.getHttpHeardMap();

            //把map给IhttpListener,处理请求参数

            RequestHolder requestHolder = new RequestHolder();
            /**处理结果的策略**/
            IHttpListener httpListener = new DownloadListener(downloadItemInfo, this, httpService);

            requestHolder.setUrl(url);
            requestHolder.setHttpListener(httpListener);
            requestHolder.setHttpService(httpService);

            HttpTask httpTask = new HttpTask(requestHolder);
            try {
                ThreadPoolManager.getInstance().excute(new FutureTask<Object>(httpTask, null));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDownloadStatusChanged(DownloadItemInfo downloadItemInfo) {

    }

    @Override
    public void onTotalLengthReceived(DownloadItemInfo downloadItemInfo) {

    }

    @Override
    public void onCurrentSizeChanged(DownloadItemInfo downloadItemInfo, double downLenth, long speed) {
        Log.i(TAG, "下载速度：" + speed / 1000 + "k/s");
        Log.i(TAG, "-----路径  " + downloadItemInfo.getFilePath() + "  下载长度  " + downLenth + "   速度  " + speed);
    }

    @Override
    public void onDownloadSuccess(DownloadItemInfo downloadItemInfo) {
        Log.i(TAG, "下载成功    路劲  " + downloadItemInfo.getFilePath() + "  url " + downloadItemInfo.getUrl());
    }


    @Override
    public void onDownloadPause(DownloadItemInfo downloadItemInfo) {

    }

    @Override
    public void onDownloadError(DownloadItemInfo downloadItemInfo, int var2, String var3) {

    }
}
