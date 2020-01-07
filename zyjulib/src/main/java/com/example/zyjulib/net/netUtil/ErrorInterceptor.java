package com.example.zyjulib.net.netUtil;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ErrorInterceptor implements Interceptor {

    //创建map集合，将添加的公共参数保存到map中

    private Map<Object, Object> mUrlParamsMap = new HashMap<>();
  /*//创建map集合，保存添加到请求头的公共参数,添加公共请求头也一样
private Map mHeaderParamsMap=new HashMap<>();*/

    @Override
    public Response intercept(Chain chain) throws IOException {
//获得请求连接
        Request oldRequest = chain.request();//旧连接
        Request newRequest;//添加公共参数后的新连接
        /**
         * 这个时添加请求头的

         */
    /*//新请求(为什么说新操作是从旧请求里面提取内容信息)
Request.Builder requestBuilder=oldRequest.newBuilder();
requestBuilder.method(oldRequest.method(),oldRequest.body());
//将公共参数添加到请求头header中去
if (mHeaderParamsMap.size()>0){//如果添加了请求头公共参数，Map.size>0
//遍历集合，将参数添加到请求头header中
for (Map.Entry params:mHeaderParamsMap.entrySet()){
requestBuilder.header(params.getKey(),params.getValue());
}
}
//将公共参数添加到请求头后，建立新请求连接
Request newRequest=requestBuilder.build();*/
//获取并判断网络请求的方法
        String method = oldRequest.method();
        if (method.equals("GET") && mUrlParamsMap.size() > 0) {//判断，并且判断是否有添加公共参数请求
//添加公共参数，构建一个新的httpurl请求链接
            HttpUrl modifieUrl = null;//注意：就算下面没有添加公共参数成功，也会执行下去的
//遍历map集合，将公共参数添加到url中
            for (Map.Entry params : mUrlParamsMap.entrySet())
                modifieUrl = oldRequest.url().newBuilder()
                        .addQueryParameter(params.getKey().toString(), params.getValue().toString())
                        .build();
//初始化新的request请求链接
            newRequest = oldRequest.newBuilder().url(modifieUrl).build();
            //再次发起网络请求，并获得返回的结果
            Response response = chain.proceed(newRequest);
            String content = response.body().string();//拿到返回结果的内容，进行分析
//获得返回结果的类型
            MediaType contentType = response.body().contentType();
            //生成新的response返回。注意：如果网络请求的response，如果去除后直接返回，将会抛异常
//注意：response只去一次值，取完后立即清空销毁，所以最好别在这打印日志
            return response.newBuilder()
                    .body(ResponseBody.create(contentType, content))
                    .build();
        } else if (method.equals("POST")) {
            if (oldRequest.body() instanceof RequestBody) {
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                RequestBody formBody = (RequestBody) oldRequest.body();
                for (Map.Entry params : mUrlParamsMap.entrySet()) {
                    formBody = bodyBuilder//POST请求方法添加公共参数
                            .add(params.getKey().toString(), params.getValue().toString())
                            .build();
                }
                newRequest = oldRequest.newBuilder().post(formBody).build();
                Response response = chain.proceed(newRequest);
                String content = response.body().string();
                MediaType contentType = response.body().contentType();
                return response.newBuilder()
                        .body(ResponseBody.create(contentType, content))
                        .build();
            }
        }
        return chain.proceed(oldRequest);
    }
    /**
     * 创建一个静态类，用于添加公共参数到map集合缓存
     */
    public static class Builder {
//创建拦截器对象，得到Map存储添加进来的公共参数
        ErrorInterceptor mHttpInterceptor;
        public Builder() {//构造方法中初始话拦截器
            mHttpInterceptor = new ErrorInterceptor();
        }
        /**
         * 将公共参数添加到map集合
         *
         * @param key
         * @param value
         * @return
         */
        public Builder addUrlParams(String key, String value) {
            mHttpInterceptor.mUrlParamsMap.put(key, value);
            return this;
        }
        public Builder addUrlParams(String key, double value) {
            return addUrlParams(key, String.valueOf(value));
        }
        public Builder addUrlParams(String key, int value) {
            return addUrlParams(key, String.valueOf(value));
        }
        public Builder addUrlParams(String key, long value) {
            return addUrlParams(key, String.valueOf(value));
        }
        public Builder addUrlParams(String key, float value) {
            return addUrlParams(key, String.valueOf(value));
        }
        /**
         * 返回建立拦截器
         *
         * @return
         */
        public ErrorInterceptor build() {
            return mHttpInterceptor;
        }
    }
}

//    
//    
//    
//    
//    
//    private static final String USER_AGENT_HEADER_NAME = "User-Agent";
//    private final String userAgentHeaderValue;
//
//    public ErrorInterceptor(String userAgentHeaderValue) {
//        this.userAgentHeaderValue = Preconditions.checkNotNull(userAgentHeaderValue);
//    }
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//
//        
//
//
//
//        final Request originalRequest = chain.request();
//        final Request requestWithUserAgent = originalRequest.newBuilder()
//                .removeHeader(USER_AGENT_HEADER_NAME)
//                .addHeader(USER_AGENT_HEADER_NAME, userAgentHeaderValue)
//                .build();
//        return chain.proceed(requestWithUserAgent);
//    }
//}
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//        // 拦截请求，获取到该次请求的request
//        Request request = chain.request();
//        // 执行本次网络请求操作，返回response信息
//        Response response = chain.proceed(request);
//        LogUtils.e("zp_test", "url: " + request.url().uri().toString());
//        ResponseBody responseBody = response.body();
//        String content = responseBody.string();
//        JSONObject jsonObject = JSONObject.parseObject(content);
//        String error = jsonObject.getString("error");
//        final HttpErrorBean errordat = JSONObject.parseObject(error, HttpErrorBean.class);
//        MediaType mediaType = response.body().contentType();
//
//        if (0 == errordat.getReturnCode()) {
//
//        } else if (1 == errordat.getReturnCode()) {
//            UCApplication.getHandler().post(new Runnable() {
//                @Override
//                public void run() {
//                    ToastUtil.getInstance().showToast("未登录"+errordat.getReturnUserMessage());
//                }
//            });
//        } else if (1001 == errordat.getReturnCode()) {
//            CommonUtils.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    ToastUtil.getInstance().showToast(errordat.getReturnUserMessage()+";"+errordat.getReturnUserMessage());
//                }
//            });
//            return response;
//        }
//
//        return response.newBuilder()
//                .body(ResponseBody.create(mediaType, content))
//                .build();
//    }
//}