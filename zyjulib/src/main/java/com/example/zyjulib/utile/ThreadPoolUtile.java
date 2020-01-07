package com.example.zyjulib.utile;

import com.lzh.easythread.AsyncCallback;
import com.lzh.easythread.Callback;
import com.lzh.easythread.EasyThread;

import java.util.concurrent.Callable;

/**
 * 作者 曾跃举
 * 时间 2019/1/1118:19
 * Created by 180713 on 2019/1/11.
 */

public class ThreadPoolUtile {
    private static ThreadPoolUtile instentc;
    private Callback onClickListener = new DefaultCallback();
    private EasyThread executor;
    //参数初始化
    private final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    //核心线程数量大小
    private final int corePoolSize = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    //线程池最大容纳线程数
    private final int maximumPoolSize = CPU_COUNT * 2 + 1;
    private int ThreadCount = maximumPoolSize;
    private String ThreadName;

    private ThreadPoolUtile() {
    }

    public static synchronized ThreadPoolUtile getInstenc() {
        if (instentc == null) {
            instentc = new ThreadPoolUtile();
        }
        return instentc;
    }

    private final EasyThread io;
    private final EasyThread cache;
    private final EasyThread calculator;
    private final EasyThread file;
//
    public EasyThread getIO() {
        return io;
    }

    public EasyThread getCache() {
        return cache;
    }

    public EasyThread getCalculator() {
        return calculator;
    }

    public EasyThread getFile() {
        return file;
    }

    {
        io = EasyThread.Builder.createFixed(6).setName("IO").setPriority(7).setCallback(onClickListener).build();
        cache = EasyThread.Builder.createCacheable().setName("cache").setCallback(onClickListener).build();
        calculator = EasyThread.Builder.createFixed(4).setName("calculator").setPriority(Thread.MAX_PRIORITY).setCallback(onClickListener).build();
        file = EasyThread.Builder.createFixed(4).setName("file").setPriority(3).setCallback(onClickListener).build();
    }

    private EasyThread init() {
        LogUtils.i("允许最大线程数:" + ThreadCount);
//        executor = EasyThread.Builder.createCacheable().setName(ThreadName).setPriority(Thread.MAX_PRIORITY).setCallback(onClickListener).build();
        executor = cache;
        return executor;
    }

    /**
     * @param callable  执行任务
     * @param rcallable 执行结束回调
     * @return
     */
    public <T> void ExecutorAsync(Callable<T> callable, AsyncCallback<T> rcallable) {
        ThreadPoolUtile.getInstenc().setThreadName("CACHEABLE").getExecutor().async(callable, rcallable);
    }


    /**
     * 开启线程
     *
     * @param threadName
     * @param callable   工作线程
     * @param rcallable  返回主线程执行
     * @param <T>
     */
    public <T> void ExecutorAsync(String threadName, Callable<T> callable, AsyncCallback<T> rcallable) {
        ThreadPoolUtile.getInstenc().setThreadName(threadName).getExecutor().async(callable, rcallable);
    }

    /**
     * 开启线程
     *
     * @param callable
     * @param <T>
     */
    public <T> void ExecutorAsync(Callable<T> callable) {
        ThreadPoolUtile.getInstenc().setThreadName("CACHEABLE").getExecutor().async(callable, null);
    }


    public ThreadPoolUtile setThreadCount(int count) {
        ThreadCount = count;
        return this;
    }

    public EasyThread getExecutor() {
        if (executor == null) {
            init();
        }
        return executor;
    }

    public ThreadPoolUtile setListenner(Callback callback) {
        onClickListener = callback;
        return this;
    }

    public ThreadPoolUtile setThreadName(String name) {
        ThreadName = name;
        return this;
    }

    private static class DefaultCallback implements Callback {

        @Override
        public void onError(String threadName, Throwable t) {
            LogUtils.e("Task with thread has occurs an error: %s", threadName+";"+ t.getMessage());
        }

        @Override
        public void onCompleted(String threadName) {
            LogUtils.d("Task with thread completed"+";"+ threadName);
        }

        @Override
        public void onStart(String threadName) {
            LogUtils.d("Task with thread start running!"+";"+ threadName);
        }
    }
}
