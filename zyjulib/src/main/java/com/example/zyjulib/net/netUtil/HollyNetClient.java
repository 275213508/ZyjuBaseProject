package com.example.zyjulib.net.netUtil;


import com.example.zyjulib.interf.CommonInterface;
import com.example.zyjulib.utile.CommonUtils;
import com.example.zyjulib.utile.LogUtils;
import com.example.zyjulib.utile.ToastUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Response;

/**
 * @author yangyi
 * 网络请求
 */
public class HollyNetClient {

    /**
     * 同步Get请求(要对参数进行编码)
     *
     * @param url           请求地址
     * @param mCommonAction 请求参数(Map集合)
     * @param type          类型  0-不带参数,1-json,callbackName包裹
     * @return
     */
    public static String requestInterfaceSyncByGet(String url, CommonAction mCommonAction, int type) throws IOException {
        HollyphoneParameters parameters = new HollyphoneParameters();
        if (type == 0) {
            parameters.addCommonAction(mCommonAction);
        } else if (type == 1) {
            parameters.add("json", mCommonAction.getJsonString());
            parameters.add("callbackName", "whCallback");
        }
        //对参数进行编码
        String encodeUrl = NetEncode.encodeUrl(parameters);
        return OkhttpNetClient.request_Sync_Get(url, encodeUrl);
    }


    /**
     * 同步Post请求
     *
     * @param url           请求地址
     * @param mCommonAction 请求参数(Map集合)
     * @return
     */
    public static String requestInterfaceSyncByPost(String url, CommonAction mCommonAction) {
        String rlt = null;
        try {
            LogUtils.e("http请求开始:");
            long systime = System.currentTimeMillis();
            rlt = OkhttpNetClient.request_Sync_Post(url, mCommonAction.getJsonString());
            LogUtils.e("http请求开始到结束时间:" + (System.currentTimeMillis() - systime));
        } catch (final IOException e) {
            CommonUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.getInstance().showToast(e.getMessage());
                }
            });
        }

        return rlt;
    }



    /**
     * 文件下载
     */
    public static void requestFileDown(String url, String downFile, CommonInterface.CommonResultCallBackProgress callBack) {
        if (!CommonUtils.isOpenNet()) {
            callBack.onFailure("无可用网络");
        }
        try {
            Response response = OkhttpNetClient.commonRequest(url);
            long totalSize = response.body().contentLength();
            InputStream inputStream = response.body().byteStream();
            File parentFile = new File(downFile).getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(downFile);
            int len;
            long currentSize = 0;
            byte[] buffer = new byte[2048];
            while ((len = inputStream.read(buffer)) != -1) {

                // 处理下载的数据
                out.write(buffer, 0, len);
                currentSize += len;
                callBack.onProgress(currentSize, totalSize);
            }
            inputStream.close();
            out.close();
            callBack.onSuccess("");
        } catch (IOException e) {
            new File(downFile).delete();
            callBack.onFailure("下载失败");
        }
    }
}
