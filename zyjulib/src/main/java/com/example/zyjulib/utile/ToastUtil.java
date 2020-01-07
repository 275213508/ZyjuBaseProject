package com.example.zyjulib.utile;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;


/**
 * 作者： Created by 180727a
 * 时间:  2018/9/18
 */
public class ToastUtil {
    private static Toast mToast;
    private static ToastUtil mInstance;

    private ToastUtil() {

    }

    public synchronized static ToastUtil getInstance() {
        synchronized (ToastUtil.class) {
            if (mInstance == null) {
                mInstance = new ToastUtil();
            }
            return mInstance;
        }
    }

    public void showToast(Context context, String msg) {
//        if (mToast != null)
//            mToast.cancel();
//        mToast = FancyToast.makeText(context, msg, Toast.LENGTH_SHORT);
//        mToast.show();
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    public void showToast(String msg) {
//        if(mToast!=null)
//        mToast.cancel();
//        mToast= Toasty.normal(CommonUtils.getContext(), msg, Toast.LENGTH_SHORT);
        if(!TextUtils.isEmpty(msg)&& !(msg.contains("188.131")||msg.contains("140.143"))) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(CommonUtils.getContext(),msg,Toast.LENGTH_SHORT);
            mToast.show();
        }
//            mToast = Toast.makeText(CommonUtils.getContext(),msg,Toast.LENGTH_SHORT);
    }

}
