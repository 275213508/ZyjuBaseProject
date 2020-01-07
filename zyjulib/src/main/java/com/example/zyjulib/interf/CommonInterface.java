package com.example.zyjulib.interf;


import android.app.Dialog;

import java.util.List;

public interface CommonInterface {
    public interface OnPermissionCallBack{
        void onPrompt();
        void onPermissionGranted();
        void onPermissionDenied();
    }
    public interface CommonResultCallBack{
        void onSuccess(String success);
        void onFailure(String error);
    }

    public interface CommonResultCallBackProgress extends CommonResultCallBack{
        void onProgress(long currentSize, long totalSize);
    }
    public interface PositiveListener{
        void onPositive();
    }

    public interface PositiveCallBackListener{
        void onPositive(String callback);
    }

    public interface NegativeListener{
        void onNegative();
    }

    public interface PositiveDoubleCallBackListener{
        void onDoublePositive(String message0, String message1);
    }

    public interface NegativeCallBackListener{
        void onNegative(String callback);
    }

    public interface PosAndNegtiveListener{
        void onPosittive();
        void onNegative();
    }

    public interface SelectItemListener{
        void onSelect(int position);
    }

    public interface MulteSelectListener{
        void onSelect(List<String> selects);
    }

    public interface DialogOnClick{
        void onClick(Dialog dialog, String item);
    }


    public interface CommonCallBack<T>{
        void onSuccess(T t);
        void onFailure();
    }

    public interface CommonCallBackProgress extends CommonCallBack{
        void onProgress(long currentSize, long totalSize);
    }

    public interface OSSCallBackListener{
        void onSuccess();
        void onFailure();
    }

    public interface OSSCallBackProgressListener extends OSSCallBackListener{
        void onProgress(long currentSize, long totalSize);
    }

    public interface OnSendMessageCallBackListener{
        void onSuccess();
        void onFailure(String error);
    }

    public interface OnSendMessageProgressCallBackListener extends OnSendMessageCallBackListener{
        void onProgress(long currentSize, long totalSize);
    }

    public interface OnSelectListener<T>{
        void onSelect(T item);
    }
}
