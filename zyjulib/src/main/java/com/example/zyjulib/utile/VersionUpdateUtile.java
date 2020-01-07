package com.example.zyjulib.utile;

import android.app.Activity;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.zyjulib.resource.OKHttpUpdateHttpServiceImpl;
import com.xuexiang.xupdate.XUpdate;
import com.xuexiang.xupdate._XUpdate;
import com.xuexiang.xupdate.entity.UpdateError;
import com.xuexiang.xupdate.listener.OnUpdateFailureListener;
import com.xuexiang.xupdate.service.OnFileDownloadListener;
import com.xuexiang.xupdate.utils.UpdateUtils;

import java.io.File;

import static com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION;


public class VersionUpdateUtile {
    private final Activity activity;
    private File filed;
    private boolean isLoading = false;


    public VersionUpdateUtile(Activity activity) {
        this.activity = activity;
        init();
    }

    public VersionUpdateUtile init() {
        XUpdate.get()
                .debug(true)
                .isWifiOnly(true)                                               //默认设置只在wifi下检查版本更新
                .isGet(true)                                                    //默认设置使用get请求检查版本
                .isAutoMode(false)                                              //默认设置非自动模式，可根据具体使用配置
                .param("versionCode", UpdateUtils.getVersionCode(activity))         //设置默认公共请求参数
                .param("appKey", CommonUtils.getContext().getPackageName())
                .setOnUpdateFailureListener(new OnUpdateFailureListener() {     //设置版本更新出错的监听
                    @Override
                    public void onFailure(UpdateError error) {
                        if (error.getCode() != CHECK_NO_NEW_VERSION) {          //对不同错误进行处理
                            ToastUtil.getInstance().showToast(error.toString());
                        }
                    }
                })
                .supportSilentInstall(true)                                     //设置是否支持静默安装，默认是true
                .setIUpdateHttpService(new OKHttpUpdateHttpServiceImpl())           //这个必须设置！实现网络请求功能。
                .init(activity.getApplication());                                                    //这个必须初始化
        return this;
    }


    //下载apk
    public void DownLoadApk(final Activity activity, String url) {
        if (!isLoading) {
            XUpdate.newBuild(activity)
                    .apkCacheDir(CommonUtils.getFileDownRootPath()) //设置下载缓存的根目录
                    .build()
                    .download(url, new OnFileDownloadListener() {   //设置下载的地址和下载的监听
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onStart() {
                            filed = null;
                            isLoading = true;
                            //开始,显示进度条
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.getInstance().showToast("开始下载,下拉状态栏查看进度...");
                                }
                            });
                        }

                        @Override
                        public void onProgress(float progress, long total) {
                            //下载中...
                            LogUtils.e("下载中..." + progress + "/" + total);
                        }

                        @Override
                        public boolean onCompleted(File file) {
                            CommonUtils.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    filed = file;
                                    isLoading = false;
                                    //下载完毕,返回文件.apk下载完毕，文件路径：" + file.getPath()
                                    _XUpdate.startInstallApk(activity, file); //填写文件所在的路径
                                }
                            });


                            return false;
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            LogUtils.e(throwable.getLocalizedMessage());
                        }
                    });
        } else {
            ToastUtil.getInstance().showToast("下载中...");
        }
    }
}
