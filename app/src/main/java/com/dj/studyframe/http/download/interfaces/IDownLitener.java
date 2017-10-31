package com.dj.studyframe.http.download.interfaces;

import com.dj.studyframe.http.interfaces.IHttpListener;
import com.dj.studyframe.http.interfaces.IHttpService;

public interface IDownLitener  extends IHttpListener {

    void setHttpServive(IHttpService httpServive);


    void  setCancleCalle();


    void  setPuaseCallble();

}
