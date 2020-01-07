/*
 * Copyright (C) 2018 xuexiangjys(xuexiangjys@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.zyjulib.resource;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.alibaba.fastjson.JSON;
import com.example.zyjulib.R;
import com.example.zyjulib.interf.CommonInterface;
import com.example.zyjulib.net.netUtil.HollyNetClient;
import com.example.zyjulib.net.netUtil.OkhttpNetClient;
import com.example.zyjulib.utile.CommonUtils;
import com.example.zyjulib.utile.LogUtils;
import com.example.zyjulib.utile.ThreadPoolUtile;
import com.lzh.easythread.AsyncCallback;
import com.xuexiang.xupdate._XUpdate;
import com.xuexiang.xupdate.proxy.IUpdateHttpService;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.Callable;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * 使用okhttp
 *
 * @author xuexiang
 * @since 2018/7/10 下午4:04
 */
public class OKHttpUpdateHttpServiceImpl implements IUpdateHttpService {

    private final ToMainActivityBroadcastReceiver receiver;
    private final PendingIntent resultPendingIntent;
    private File filed;

    public OKHttpUpdateHttpServiceImpl() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("isToFirstFragment");
        receiver = new ToMainActivityBroadcastReceiver();
        CommonUtils.getContext().registerReceiver(receiver, filter);
        Intent resultIntent = new Intent();
        resultIntent.setAction("isToFirstFragment");
        resultIntent.putExtra("isupdata", true);
        resultPendingIntent = PendingIntent.getBroadcast(
                CommonUtils.getContext(), 10086, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        mNotificationManager = (NotificationManager) CommonUtils.getContext().getSystemService(NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(CommonUtils.getContext().getPackageName(), R.layout.view_down_file);
        remoteViews.setImageViewResource(R.id.img_view_down_file, android.R.mipmap.sym_def_app_icon);
        remoteViews.setTextViewText(R.id.tv_file_down_name, "下载进度:");
        remoteViews.setProgressBar(R.id.pb_file_down_progress, 100, 1, false);
        remoteViews.setTextViewText(R.id.tv_file_down_progress, 1 + "%");
        mBuilder = new NotificationCompat.Builder(CommonUtils.getContext());
        mBuilder.setSmallIcon(android.R.mipmap.sym_def_app_icon);
        mBuilder.setLargeIcon(CommonUtils.getBitmap(android.R.mipmap.sym_def_app_icon));
        mBuilder.setContentIntent(resultPendingIntent);
//        mBuilder.setSound(null);
        mBuilder.setVibrate(new long[0]);
        mBuilder.setOngoing(true);
        mBuilder.setChannelId("101");
        mBuilder.setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE | NotificationCompat.DEFAULT_SOUND);
        mBuilder.setLights(0, 0, 0);
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBuilder.setContent(remoteViews);
            channel = new NotificationChannel("101", "更新进度通知", NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
        } else {
            mBuilder.setContentTitle("下载进度:");
            mBuilder.setContentText("已下载" + progress + "%");

        }
        notifi = mBuilder.build();
    }

    @Override
    public void asyncGet(@NonNull final String url, @NonNull final Map<String, Object> params, final @NonNull Callback callBack) {
        ThreadPoolUtile.getInstenc().ExecutorAsync(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String param = ParamsEncode(params);
                String resss = OkhttpNetClient.request_Sync_Get(url, param);
                return resss;
            }
        }, new AsyncCallback<String>() {
            @Override
            public void onSuccess(String s) {
                callBack.onSuccess(s);
            }

            @Override
            public void onFailed(Throwable throwable) {
                callBack.onError(throwable);
            }
        });
    }

    @Override
    public void asyncPost(@NonNull final String url, @NonNull final Map<String, Object> params, final @NonNull Callback callBack) {
        //这里默认post的是Form格式，使用json格式的请修改 post -> postString
        ThreadPoolUtile.getInstenc().ExecutorAsync(new Callable<String>() {
            @Override
            public String call() throws Exception {
                SortedMap<String, Object> data = new TreeMap<>();
                if (params != null && params.size() > 0) {
                    data.putAll(params);
                }
                String jsonData = JSON.toJSONString(data);
                String rlt = OkhttpNetClient.request_Sync_Post(url, jsonData);
                return rlt;
            }
        }, new AsyncCallback<String>() {
            @Override
            public void onSuccess(String s) {
                callBack.onSuccess(s);
            }

            @Override
            public void onFailed(Throwable throwable) {
                callBack.onError(throwable);
            }
        });

    }

    private NotificationManager mNotificationManager;
    private RemoteViews remoteViews;
    private Notification notifi;
    private NotificationCompat.Builder mBuilder;
    private int progress = 0;

    /**
     * 下载更新文件
     *
     * @param url
     * @param path     文件保存路径
     * @param fileName 文件名
     * @param callback
     */
    @Override
    public void download(@NonNull final String url, @NonNull final String path, @NonNull final String fileName, final @NonNull DownloadCallback callback) {

        ThreadPoolUtile.getInstenc().ExecutorAsync(new Callable<String>() {
            @Override
            public String call() throws Exception {
                CommonUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mNotificationManager.cancel(101);
                        callback.onStart();
                    }
                });
                HollyNetClient.requestFileDown(url,
                        path + "/" + fileName, new CommonInterface.CommonResultCallBackProgress() {
                            @Override
                            public void onProgress(long currentSize, long totalSize) {
                                int currentProgress = (int) (((currentSize * 1.0) / totalSize) * 100);
                                if (currentProgress - progress > 2 || currentProgress == 100) {
                                    CommonUtils.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            LogUtils.i("下载进度:" + progress);
                                            progress = currentProgress;
                                            callback.onProgress(progress, totalSize);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                                remoteViews.setProgressBar(R.id.pb_file_down_progress, 100, (int) progress, false);
                                                remoteViews.setTextViewText(R.id.tv_file_down_progress, "已下载" + progress + "%");
                                            } else {
                                                mBuilder.setContentText("已下载" + progress + "%");
                                                mBuilder.setProgress(100, progress, false);
//                                                notifi = mBuilder.build();
                                            }
                                            mNotificationManager.notify(101, notifi);
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onSuccess(String success) {

                                filed = new File(path + "/" + fileName);
                                callback.onSuccess(filed);
//                                mNotificationManager.cancel(101);
                            }

                            @Override
                            public void onFailure(final String error) {
                                try {
                                    throw new Exception();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                return path + "/" + fileName;
            }
        }, new AsyncCallback<String>() {
            @Override
            public void onSuccess(String s) {
            }

            @Override
            public void onFailed(Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }

    @Override
    public void cancelDownload(@NonNull String url) {

    }

    private Map<String, String> transform(Map<String, Object> params) {
        Map<String, String> map = new TreeMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toString());
        }
        return map;
    }

    /**
     * get请求参数的拼接
     *
     * @param data
     * @return
     */
    private static String ParamsEncode(Map<String, Object> data) {
        StringBuffer buffer = new StringBuffer();

        Set<String> keys = data.keySet();
        for (String key : keys) {
            buffer.append(key);
            buffer.append("=");
            buffer.append(data.get(key));
            buffer.append("&");
        }
        buffer.deleteCharAt(buffer.length() - 1);
        String str = buffer.toString();
        return str;
    }

    /**
     * 跳转到主页面的广播接收者
     * Created by Think on 2017/10/19.
     */
    public class ToMainActivityBroadcastReceiver extends BroadcastReceiver {
        public static final String IS_TO_FIRST_FRAGMENT = "isToFirstFragment";

        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.i("下载完成.....通知");
            if (progress==100&&intent.getBooleanExtra("isupdata", false)) {
                //用这个方法实现点击notification后的事件  不知为何不能自动清掉已点击的notification  故自己手动清就ok了
                _XUpdate.startInstallApk(CommonUtils.getContext(), filed); //填写文件所在的路径
                mNotificationManager.cancel(101);
                CommonUtils.getContext().unregisterReceiver(receiver);
            }
        }
    }
}