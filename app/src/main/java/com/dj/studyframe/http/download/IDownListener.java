
package com.dj.studyframe.http.download;

import com.dj.studyframe.http.interfaces.IHttpListener;
import com.dj.studyframe.http.interfaces.IHttpService;

/**
 * Comment:
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/22
 */
public interface IDownListener extends IHttpListener {
    void setHttpService(IHttpService httpService);

    void setCancleCallble();

    void setPauseCallble();
}
