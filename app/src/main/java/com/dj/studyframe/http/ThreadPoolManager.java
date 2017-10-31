package com.dj.studyframe.http;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Comment:线程管理
 *
 * @author :DJ鼎尔东 / 1757286697@qq.cn
 * @version : Administrator1.0
 * @date : 2017/10/19
 */
public class ThreadPoolManager {
    private static final String TAG ="loveDj" ;
    private static  ThreadPoolManager instance=new ThreadPoolManager();

    private LinkedBlockingQueue<Future<?>> taskQuene=new LinkedBlockingQueue<>();

    private ThreadPoolExecutor threadPoolExecutor;
    public static ThreadPoolManager getInstance() {

        return instance;
    }
    private ThreadPoolManager()
    {
        threadPoolExecutor=new ThreadPoolExecutor(4,10,10, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4), handler);
        threadPoolExecutor.execute(runnable);
    }

    public <T> boolean removeTask(FutureTask futureTask)
    {
        boolean result=false;
        /**
         * 阻塞式队列是否含有线程
         */
        if(taskQuene.contains(futureTask))
        {
            taskQuene.remove(futureTask);
        }else
        {
            result=threadPoolExecutor.remove(futureTask);
        }
        return  result;
    }


    private Runnable runnable =new Runnable() {
        @Override
        public void run() {
            while (true)
            {
                FutureTask futureTask=null;

                try {
                    /**
                     * 阻塞式函数
                     */
                    Log.i(TAG,"等待队列     "+taskQuene.size());
                    futureTask= (FutureTask) taskQuene.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(futureTask!=null)
                {
                    threadPoolExecutor.execute(futureTask);
                }
                Log.i(TAG,"线程池大小      "+threadPoolExecutor.getPoolSize());
            }
        }
    };
    public <T> void execte(FutureTask<T> futureTask) throws InterruptedException {
        taskQuene.put(futureTask);
    }
    /**
     * 请求队列,
     */
    private RejectedExecutionHandler handler=new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                taskQuene.put(new FutureTask<Object>(r,null) {
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}