package com.example.zyjulib.net.netUtil;

/**
 * 异步网络请求回调接口
 */
public interface HttpResponseCallback<T> {
    void onSuccess(int HandlerSuccess, T result);
    void onFailure(int HandlerFailure, String error);
}
