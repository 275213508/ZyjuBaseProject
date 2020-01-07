package com.example.zyjulib.utile;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * Created by 180713 on 2018/8/15.
 */

public class isSDCard {
    public  String getSDPath() {
        File sdDir = null;
        //判断sd卡是否存在
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取根目录
            Log.e("qq", "外部存储可用..." + sdDir.toString());
        }
        return sdDir.toString();
    }
}
