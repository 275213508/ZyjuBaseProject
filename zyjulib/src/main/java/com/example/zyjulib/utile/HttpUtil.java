package com.example.zyjulib.utile;

import android.app.Activity;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpUtil {

    /**
     * 采用okhttp方式进行网络加载
     */
    //okHttp的必要配置
    private static volatile OkHttpClient mOkHttpClient;
//    private static  OkHttpClient mOkHttpClient;

    private HttpUtil() {
    }

    private static OkHttpClient initOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (HttpUtil.class) {
                if (mOkHttpClient == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .connectTimeout(10000, TimeUnit.MILLISECONDS)
                            .writeTimeout(10000, TimeUnit.MILLISECONDS)
                            .readTimeout(10000, TimeUnit.MILLISECONDS);
//                            .proxy(Proxy.NO_PROXY);
                    mOkHttpClient = builder.build();
                }
            }
        }
        return mOkHttpClient;

    }

    public interface GetCallBack {
        public void getResponse(String str);
    }


    //接口的对接

    /**
     * @param urlString
     * @param data
     * @param getCallBack
     */
    public static void getJsonDataFromNet(final Activity activity, String urlString, Map<String, Object> data, final GetCallBack getCallBack) {
        //得到完整的地址
        if (data != null && data.size() > 0) {
            urlString = urlString + "?" + A(data);
        }

        LogUtils.e("ttt", urlString);
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            if (activity.isDestroyed()) {

                return;
            }
        } else {
            if (activity.isFinishing()) {
                return;
            }
        }

        Request.Builder requestBuilder = new Request.Builder().cacheControl(CacheControl.FORCE_NETWORK).url(urlString);
        requestBuilder.method("GET", null);
        Request request = requestBuilder.tag(activity).build();
        Call mcall = initOkHttpClient().newCall(request);
        mcall.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (android.os.Build.VERSION.SDK_INT >= 17) {
                    if (activity.isDestroyed()) {
                        call.cancel();
                        return;
                    }
                } else {
                    if (activity.isFinishing()) {
                        call.cancel();
                        return;
                    }
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getCallBack.getResponse("{\"errmsg\":\"连接服务器失败，请稍后重试！\",\"errcode\":\"-1\"}");
                    }
                });
                call.cancel();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (android.os.Build.VERSION.SDK_INT >= 17) {
                    if (activity.isDestroyed()) {
                        call.cancel();
                        return;
                    }
                } else {
                    if (activity.isFinishing()) {
                        call.cancel();
                        return;
                    }
                }

                if (response.isSuccessful()) {


                    try {
                        ResponseBody body = response.body();
                        getInputStream2String(activity, body.byteStream(), getCallBack);
                        body.close();
                    } catch (Exception e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getCallBack.getResponse("{\"errmsg\":\"服务器出错，请稍后重试！\",\"errcode\":\"-1\"}");
                            }
                        });
                    }

                } else {

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            getCallBack.getResponse("{\"errmsg\":\"服务器出错，请稍后重试！\",\"errcode\":\"-1\"}");
                        }
                    });
                }
                //关闭防止内存泄漏
                if (response.body() != null) {
                    response.body().close();
                }
                call.cancel();
            }
        });

    }

    /**
     * get请求参数的拼接
     *
     * @param data
     * @return
     */
    private static String A(Map<String, Object> data) {
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
     * post请求
     */
    PostCallBack postCallBack;

    public interface PostCallBack {

        public void getResponse(String str);
    }
    private static void getInputStream2String(Activity activity, InputStream in, final GetCallBack getCallBack) throws IOException {
        final StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getCallBack.getResponse(out.toString());
            }
        });

    }
    public static void postJsonDataFromNet(final Activity activity, String urlString, Map<String, Object> datas, final PostCallBack postCallBack) {
        //得到完整的地址
        if (datas != null && datas.size() > 0) {
            urlString = urlString + "?" + A(datas);
        }
        SortedMap<String, Object> data = new TreeMap<>();
        if (datas != null && datas.size() > 0) {
            data.putAll(datas);
        }



        String jsonData = JSON.toJSONString(data);

        LogUtils.e("ttt", jsonData + "..." + urlString);

        if (android.os.Build.VERSION.SDK_INT >= 17) {
            if (activity.isDestroyed()) {
                return;
            }
        } else {
            if (activity.isFinishing()) {
                return;
            }
        }
        //形式为json字符串提交
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData);
        Request request = new Request.Builder().cacheControl(CacheControl.FORCE_NETWORK)
                .tag(activity)
                .url(urlString)
                .post(body)
                .build();
        Call call = initOkHttpClient().newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e("zzz",e.toString()+"///");

                if (android.os.Build.VERSION.SDK_INT >= 17) {
                    if (activity.isDestroyed()) {
                        call.cancel();
                        return;
                    }
                } else {
                    if (activity.isFinishing()) {
                        call.cancel();
                        return;
                    }
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        postCallBack.getResponse("{\"errmsg\":\"连接服务器出错，请稍后重试！\",\"errcode\":\"-1\"}");
                    }
                });
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (android.os.Build.VERSION.SDK_INT >= 17) {
                    if (activity.isDestroyed()) {
                        call.cancel();
                        return;
                    }
                } else {
                    if (activity.isFinishing()) {
                        call.cancel();
                        return;
                    }
                }
//                final String str = response.body().string();
                if (response.isSuccessful()) {

//                    activity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            postCallBack.getResponse(str);
//                        }
//                    });
                    try {
                        ResponseBody body = response.body();
                        postInputStream2String(activity,body.byteStream(),postCallBack);
                        body.close();
                    }catch (Exception e){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                postCallBack.getResponse("{\"errmsg\":\"服务器出错，请稍后重试！\",\"errcode\":\"-1\"}");
                            }
                        });
                    }

                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            postCallBack.getResponse("{\"errmsg\":\"服务器出错，请稍后重试！\",\"errcode\":\"-1\"}");
                        }
                    });
                }
                //关闭防止内存泄漏
                if (response.body() != null) {
                    response.body().close();
                }
                call.cancel();
            }

        });

    }
    public static void postJsonDataFromNet(final Activity activity, String urlString, List<String> list, final PostCallBack postCallBack) {
        //得到完整的地址
//        if (data != null && data.size() > 0) {
//            urlString = urlString + "?" + A(data);
//        }
//        SortedMap<String, Object> data = new TreeMap<>();
//        if (datas != null && datas.size() > 0) {
//            data.putAll(datas);
//        }



        String jsonData = JSON.toJSONString(list);

        LogUtils.e("ttt", jsonData + "..." + urlString);

        if (android.os.Build.VERSION.SDK_INT >= 17) {
            if (activity.isDestroyed()) {
                return;
            }
        } else {
            if (activity.isFinishing()) {
                return;
            }
        }
        //形式为json字符串提交
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData);
        Request request = new Request.Builder().cacheControl(CacheControl.FORCE_NETWORK)
                .tag(activity)
                .url(urlString)
                .post(body)
                .build();
        Call call = initOkHttpClient().newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e("zzz",e.toString()+"///");

                if (android.os.Build.VERSION.SDK_INT >= 17) {
                    if (activity.isDestroyed()) {
                        call.cancel();
                        return;
                    }
                } else {
                    if (activity.isFinishing()) {
                        call.cancel();
                        return;
                    }
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        postCallBack.getResponse("{\"errmsg\":\"连接服务器出错，请稍后重试！\",\"errcode\":\"-1\"}");
                    }
                });
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (android.os.Build.VERSION.SDK_INT >= 17) {
                    if (activity.isDestroyed()) {
                        call.cancel();
                        return;
                    }
                } else {
                    if (activity.isFinishing()) {
                        call.cancel();
                        return;
                    }
                }
//                final String str = response.body().string();
                if (response.isSuccessful()) {

//                    activity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            postCallBack.getResponse(str);
//                        }
//                    });
                    try {
                        ResponseBody body = response.body();
                        postInputStream2String(activity,body.byteStream(),postCallBack);
                        body.close();
                    }catch (Exception e){
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                postCallBack.getResponse("{\"errmsg\":\"服务器出错，请稍后重试！\",\"errcode\":\"-1\"}");
                            }
                        });
                    }

                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            postCallBack.getResponse("{\"errmsg\":\"服务器出错，请稍后重试！\",\"errcode\":\"-1\"}");
                        }
                    });
                }
                //关闭防止内存泄漏
                if (response.body() != null) {
                    response.body().close();
                }
                call.cancel();
            }

        });

    }
      /* post
     * @param activity
     * @param in
     * @param postCallBack
     * @throws IOException
     */
    private static void postInputStream2String(Activity activity, InputStream in, final PostCallBack postCallBack) throws IOException {
        final StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1;) {
            out.append(new String(b, 0, n));
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                postCallBack.getResponse(out.toString());
            }
        });

    }
    public static void postUpJsonDataFromNet(final Activity activity, String urlString, Map<String, Object> datas, List<File> fileList,  final PostCallBack postCallBack) {
        //得到完整的地址
//        if (data != null && data.size() > 0) {
//            urlString = urlString + "?" + A(data);
//        }
        SortedMap<String, Object> data = new TreeMap<>();
        if (datas != null && datas.size() > 0) {
            data.putAll(datas);
        }

//        Gson gson = new Gson();
//        String jsonData = gson.toJson(data);

//        Log.i("ttt", jsonData + "..." + urlString);

        if (android.os.Build.VERSION.SDK_INT >= 17) {
            if (activity.isDestroyed()) {
                return;
            }
        } else {
            if (activity.isFinishing()) {
                return;
            }
        }


//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData);
        //形式为表单格式
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder();
        multipartBodyBuilder.setType(MultipartBody.FORM);
        //遍历map中所有参数到builder
        if (data != null) {
            for (String key : data.keySet()) {
                multipartBodyBuilder.addFormDataPart(key, data.get(key) + "");
            }
        }
        //遍历paths中所有图片绝对路径到builder，并约定key如“upload”作为后台接受多张图片的key
        if (fileList != null && fileList.size() > 0) {

            for (int i = 0; i < fileList.size(); i++) {
                multipartBodyBuilder.addFormDataPart("headPic", fileList.get(i).getName(), RequestBody.create(MediaType.parse("image/png"), fileList.get(i)));
            }
        }
        //构建请求体
        RequestBody requestBody = multipartBodyBuilder.build();
        Request request = new Request.Builder().cacheControl(CacheControl.FORCE_NETWORK)
                .tag(activity)
                .url(urlString)
                .post(requestBody)
                .build();
        Call call = initOkHttpClient().newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (android.os.Build.VERSION.SDK_INT >= 17) {
                    if (activity.isDestroyed()) {
                        call.cancel();
                        return;
                    }
                } else {
                    if (activity.isFinishing()) {
                        call.cancel();
                        return;
                    }
                }
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        postCallBack.getResponse("{\"errmsg\":\"连接服务器出错，请稍后重试！\",\"errcode\":\"-1\"}");
                    }
                });
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (android.os.Build.VERSION.SDK_INT >= 17) {
                    if (activity.isDestroyed()) {
                        call.cancel();
                        return;
                    }
                } else {
                    if (activity.isFinishing()) {
                        call.cancel();
                        return;
                    }
                }
//                final String str = response.body().string();
                if (response.isSuccessful()) {

//                    activity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            postCallBack.getResponse(str);
//                        }
//                    });
                    try {
                        ResponseBody body = response.body();
                        postInputStream2String(activity, body.byteStream(), postCallBack);
                        body.close();
                    } catch (Exception e) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                postCallBack.getResponse("{\"errmsg\":\"服务器出错，请稍后重试！\",\"errcode\":\"-1\"}");
                            }
                        });
                    }
                } else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            postCallBack.getResponse("{\"errmsg\":\"服务器出错，请稍后重试！\",\"errcode\":\"-1\"}");
                        }
                    });
                }
                //关闭防止内存泄漏
                if (response.body() != null) {
                    response.body().close();
                }
                call.cancel();
            }

        });
    }
    /**
     * 统一为商城模块请求添加头信息
     *
     * @return
     */
    private static Request.Builder addHeaders(Map<String, Object> headerData, int tag) {
        Request.Builder builder = new Request.Builder().cacheControl(CacheControl.FORCE_NETWORK)
                .addHeader("Authorization", "APPCODE " + "");


        return builder;
    }
}
