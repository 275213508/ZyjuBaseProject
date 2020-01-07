package com.example.zyjulib.net.netUtil;


import com.example.zyjulib.utile.CommonUtils;
import com.example.zyjulib.utile.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @author yangyi
 * 网络请求，采用Okhttp作为底层
 */
public class OkhttpNetClient {

    // 发送json格式
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static ErrorInterceptor.Builder builder=new ErrorInterceptor.Builder();
    private static ErrorInterceptor interceptor = builder.build();
    private static OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
//            .addInterceptor(interceptor)
            .retryOnConnectionFailure(true)//错误重联
            .build();
    static {
//        mOkHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
//        mOkHttpClient.setWriteTimeout(30, TimeUnit.SECONDS);
//        mOkHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
    }

    public static boolean request_baidu() {
        String url = "http://www.baidu.com";
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            return false;
        }
    }

    public static Response commonRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = mOkHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response;
        } else {
            LogUtils.e("http.error:"+response+"");
//            throw new IOException("Unexpected code " + response);
            throw new IOException("请求错误,请稍后再试");
        }
    }

    /**
     * 同步get请求
     */
    public static String request_Sync_Get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = mOkHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            LogUtils.e("http.error:"+response+"");
            //            throw new IOException("Unexpected code " + response);
            throw new IOException("请求错误,请稍后再试");
        }
    }

    /**
     * 同步get请求
     *
     * @param url   访问地址
     * @param param 字符串参数(注意要编码)
     * @throws IOException
     */
    public static String request_Sync_Get(String url, String param) throws IOException {
        url = url + "?" + param;
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = mOkHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            LogUtils.e("http.error:"+response+"");
            //            throw new IOException("Unexpected code " + response);
            throw new IOException("请求错误,请稍后再试");
        }
    }

    /**
     * @param url
     * @param param
     * @return
     * @throws IOException
     */
    public static String request_CheckVersionCode_Sync_Get(String url, String param, String vCode) throws IOException {
        url = url + "?" + param;
        Request request = new Request.Builder()
                .addHeader("Versioncode", vCode)
                .url(url)
                .build();
        Response response = mOkHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            LogUtils.e("http.error:"+response+"");
            //            throw new IOException("Unexpected code " + response);
            throw new IOException("请求错误,请稍后再试");
        }
    }


    /**
     * 同步post请求,参数是json字符串
     *
     * @param url  访问地址
     * @param json 参数
     * @return
     * @throws IOException
     */
    public static String request_Sync_Post(String url, String json) throws IOException {
        if(!CommonUtils.isOpenNet()){
            throw new IOException("无可用网络");
        }
        LogUtils.d("http消息:","url = " + url );
        LogUtils.json("http消息;参数: ", json);
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
//                .addHeader("Connection", "close")
                .url(url)
                .post(body)
                .build();
        Response response = mOkHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
//            return postInputStream2String(response.body().byteStream());
            String res = response.body().string();
//            LogUtils.json("http消息", "参数返回:\n " + res);
            LogUtils.d("http消息", "参数返回:\n " + res);
            if (response.body() != null) {
                response.body().close();
            }
            return res;
        } else {
            LogUtils.e("http.error:"+response+"");
            throw new IOException("请求错误,请稍后再试");
        }
    }
    private static String postInputStream2String(InputStream in) throws IOException {
        final StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n));
        }
        LogUtils.d("http消息:", "参数返回: " + out.toString());
        return out.toString();
    }
}
