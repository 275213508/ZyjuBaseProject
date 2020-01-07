package com.example.zyjulib.utile;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * 作者 曾跃举
 * 时间 2018/12/1711:24
 * Created by 180713 on 2018/12/17.
 */

public class SoftIntupUtile {
    public static void closeHideSoftInput(Activity context) {
        if(context!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
            if(inputMethodManager!=null)
            inputMethodManager.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    public static void closeHideSoftInput(View view) {
        if (view == null || view.getWindowToken() == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    public static void showInput(final EditText et) {
        if(et!=null) {
            et.requestFocus();
            InputMethodManager imm = (InputMethodManager) CommonUtils.getContext().getSystemService(INPUT_METHOD_SERVICE);
            if(imm!=null)
            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
