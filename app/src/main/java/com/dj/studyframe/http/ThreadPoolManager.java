package com.dj.studyframe.http;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
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
    private static ThreadPoolManager instance = new ThreadPoolManager();

    /**
     * 阻塞式队列
     */
    private LinkedBlockingDeque<Future<?>> taskQueue = new LinkedBlockingDeque<>();

    /**
     * 线程池
     */
    private ThreadPoolExecutor threadPoolExecutor;

    public static ThreadPoolManager getInstance() {
        return instance;
    }

    public ThreadPoolManager() {
        threadPoolExecutor = new ThreadPoolExecutor(4, 10, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4), handler);

        threadPoolExecutor.execute(runnable);
    }

    public <T> void excute(FutureTask<T> futrueTask) throws InterruptedException {
        taskQueue.put(futrueTask);
    }


    private Runnable runnable = new Runnable() {

        @Override
        public void run() {
            while (true) {
                FutureTask futrueTask = null;
                try {
                    /**阻塞式函数*/
                    Log.w("等待队列", taskQueue.size() + "数目");
                    futrueTask = (FutureTask) taskQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (futrueTask != null) {
                    threadPoolExecutor.execute(futrueTask);
                }
                Log.w("线程池大小", threadPoolExecutor.getPoolSize() + "");
            }
        }
    };

    /**
     * 请求队列,
     */
    private RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                taskQueue.put(new FutureTask(r, null) {
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}
