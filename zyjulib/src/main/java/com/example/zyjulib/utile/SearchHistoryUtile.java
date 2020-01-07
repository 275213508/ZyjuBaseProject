package com.example.zyjulib.utile;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.zyjulib.UCApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.zyjulib.resource.Contect.SpConstant.SP_SHORCH_HISTORY;


public class SearchHistoryUtile {
    /**
     *   保存搜索记录
     */
    public void saveSearchHistory(String inputText,String type) {
        SharedPreferences sp = CommonUtils.getContext().getSharedPreferences(SP_SHORCH_HISTORY, Context.MODE_PRIVATE);
        if (TextUtils.isEmpty(inputText)) {
            return;
        }
        String longHistory = sp.getString(type+ UCApplication.getUserInfo().getUuid(), "");  //获取之前保存的历史记录
        String[] tmpHistory = longHistory.split(","); //逗号截取 保存在数组中
        List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory)); //将改数组转换成ArrayList
        SharedPreferences.Editor editor = sp.edit();
        if (historyList.size() > 0) {
            //1.移除之前重复添加的元素
            for (int i = 0; i < historyList.size(); i++) {
                if (inputText.equals(historyList.get(i))) {
                    historyList.remove(i);
                    break;
                }
            }
            historyList.add(0, inputText); //将新输入的文字添加集合的第0位也就是最前面(2.倒序)
//            if (historyList.size() > 10) {
//                historyList.remove(historyList.size() - 1); //3.最多保存10条搜索记录 删除最早搜索的那一项
//            }
            //逗号拼接
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < historyList.size(); i++) {
                sb.append(historyList.get(i) + ",");
            }
            //保存到sp
            editor.putString(type+UCApplication.getUserInfo().getUuid(), sb.toString());
            editor.commit();
        } else {
            //之前未添加过
            editor.putString(type+UCApplication.getUserInfo().getUuid(), inputText + ",");
            editor.commit();
        }
    }
    /**
     * 获取搜索记录
     */
    public  List<String> getSearchHistory(String type){
        SharedPreferences sp = CommonUtils.getContext().getSharedPreferences(SP_SHORCH_HISTORY, Context.MODE_PRIVATE);
        String longHistory =sp.getString(type+UCApplication.getUserInfo().getUuid(), "");
        String[] tmpHistory = longHistory.split(","); //split后长度为1有一个空串对象
        List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory));
        if (historyList.size() == 1 && historyList.get(0).equals("")) { //如果没有搜索记录，split之后第0位是个空串的情况下
            historyList.clear();  //清空集合，这个很关键
        }
        return historyList;
    }
    public void clearSearchHistory(String type){
        SharedPreferences sp = CommonUtils.getContext().getSharedPreferences(SP_SHORCH_HISTORY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(type+UCApplication.getUserInfo().getUuid(),"");
        editor.commit();
    }
}
