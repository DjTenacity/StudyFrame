package com.dj.studyframe.http.download;

import com.dj.studyframe.http.HttpTask;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/22
 */
public class DownloadItemInfo extends BaseEntity<DownloadItemInfo> {
    public DownloadItemInfo(String url, String filePath) {
        this.url = url;
        this.filePath = filePath;
    }

    public DownloadItemInfo( ) {
    }

    private long currentLength;

    private long totalLength;

    private String url ;

    private String filePath;
    //
    //transient排除序列化
    private  transient  HttpTask httpTask;
    //下载的状态
    private DownloadStatus status;

    public DownloadStatus getStatus() {
        return status;
    }

    public void setStatus(DownloadStatus status) {
        this.status = status;
    }

    public long getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(long currentLength) {
        this.currentLength = currentLength;
    }

    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public HttpTask getHttpTask() {
        return httpTask;
    }

    public void setHttpTask(HttpTask httpTask) {
        this.httpTask = httpTask;
    }
}
