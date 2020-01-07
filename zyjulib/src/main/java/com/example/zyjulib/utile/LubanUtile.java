package com.example.zyjulib.utile;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.example.zyjulib.resource.Contect.config.ZoomIgnoreBy;


public class LubanUtile {
    public static LubanUtile instenc;

    public static LubanUtile getInstance() {
        if (instenc == null) {
            instenc = new LubanUtile();
        }
        return instenc;
    }

    /**
     * 压缩图片
     * 无返回
     * @param list 要压缩的数据
     * @param onCompressListener 压缩监听
     * @return
     */
    public LubanUtile YSImg(ArrayList<String> list, OnCompressListener onCompressListener) {
        Luban.with(CommonUtils.getContext())
                .load(list)
                .ignoreBy(ZoomIgnoreBy)
                .setTargetDir(CommonUtils.getPublicLubanFileDownDir())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path));
                    }
                })
                .setCompressListener(onCompressListener).launch();
        return this;

    }

    /**
     * 带返回路径的压缩文件
     * @param list 要压缩的数据
     * @return 压缩后的文件
     */
    public ArrayList<File> synYSImg(ArrayList<String> list) {
        ArrayList<File> files = new ArrayList<>();
        try {
            files = (ArrayList<File>) Luban.with(CommonUtils.getContext())
                    .load(list)
                    .ignoreBy(ZoomIgnoreBy)
                    .setTargetDir(CommonUtils.getPublicLubanFileDownDir())
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path));
                        }
                    })
                    .get();
        } catch (IOException e) {
        }
        return files;

    }
}
