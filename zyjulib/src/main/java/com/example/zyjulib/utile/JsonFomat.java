package com.example.zyjulib.utile;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Iterator;

/**
 * Created by lgs on 2017/12/15 0015.
 * 功能介绍：
 * 1, 模仿网页端BeJson格式化的功能：从控制台可以方便查看，复制JSON。
 * 2，数据量大：因为Log输出的字数限制，在JSON数据量大的时候会分批输出。要是放到beJson,hijson里需手动处理
 * 3，数据量小：JSON数据量少,可以从控制台输出复制。
 * 4, 需要配置参数：域名，URL,过滤名，是否开启日志等,。
 * 5，支持解析：JSONObject,JSONArray。
 */

public class JsonFomat {
    private final static int SUBSTRINGLENG = 4;
    private final static String DELDATE = "}\r\n";
    private static long currentTime;
    private static boolean isdelCb;//是否删除多余的JSONObject(默认的JSONArry会被嵌套了一个JSONObject)
    private static StringBuilder josnBuilder = new StringBuilder();//最终输出的数据容器
    private static JSONObject parseResponse;

    /***
     * 功能：线程同步,输出日志
     * @param response  必传
     * @param ip  输出的域名
     * @param url 要输出的：Url,会在控制台输出，不填就不输出，非必传
     * @param params 输出的参数
     * @param TagName 输出台标签名：必传。
     * @parac Debug true 表示输出日志，上线设置false。
     * */
    public static void outputFormatJson(String TagName, String ip, String url, String params, Object response) {
        synchronized (JsonFomat.class) {
            try {
                currentTime = System.currentTimeMillis();
                if (response instanceof JSONArray)
                {
                    isdelCb = true;
                    JSONObject tempJson = new JSONObject();
                    parseResponse = (JSONObject)tempJson.put("", (JSONArray) response);
                } else {
                    isdelCb = false;
                    parseResponse = ((JSONObject) response);
                }
                synchronized (JsonFomat.class)
                {
                    outputFormatJson(TagName, ip, url, params, parseResponse, isdelCb);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /***
     * 功能：
     * */
    private static void outputFormatJson(String TagName, String ip, String url, String params, JSONObject response, boolean isdelCb) {
        startaparse(TagName, ip, url, params, response, isdelCb);
    }
    /**
     * 功能 ：添加外层花括号，域名，参数
     *
     * @param isdelCb :对JSONArry去掉外层手动添加的JSONObject
     */
    private static void startaparse(String TagName, String ip, String url, String params, JSONObject response, boolean isdelCb) {
        if (checkParams(TagName, response)) {
            return;
        }
        josnBuilder.setLength(0);
        appendSb("请求的接口:" + " " + ip + url, false);
        appendSb("传递参数:" + " " + params, false);
        appendSb("开始输出接口返回数据...", false);
        appendSb("{", false);
        prinfrmatJson(TagName, url, response);
        appendSb("}", false);
        appendSb("输出接口返回数据完毕:" + " 解析耗时" + (System.currentTimeMillis() - currentTime) + "毫秒;"+url, false);
        if (isdelCb) {
            josnBuilder.delete(josnBuilder.indexOf("{"), josnBuilder.indexOf("["));
            josnBuilder.delete(josnBuilder.lastIndexOf(DELDATE), josnBuilder.lastIndexOf(DELDATE) + SUBSTRINGLENG);
        }
        logOut(TagName, josnBuilder.toString());
    }
    /**
     * 功能：检查参数是否异常：
     *
     * @param TagName  必填
     * @param response 必填
     */
    private static boolean checkParams(String TagName, JSONObject response) {
        if (null == response || TextUtils.isEmpty(response.toString()) || TextUtils.isEmpty(TagName)) {
            return true;
        }
        return false;
    }
    /**
     * 功能：遍历所有子json对象,并对孩子进行递归操作
     * 对JSONobject,JSONArray,Object。进行区分判断。
     */
    private static void prinfrmatJson(String TagName, String url, JSONObject response) {
        try {
            Iterator<String> jsonobject = response.keySet().iterator();
            while (jsonobject.hasNext()) {

                String key = jsonobject.next();

                if (response.get(key) instanceof JSONObject) {

                    appendSb("\"" + key + "\"" + ":{", false);

                    prinfrmatJson(TagName, url, (JSONObject) response.get(key));

                    boolean isendValue = jsonobject.hasNext();//判断是否还有下一个元素

                    appendSb("  }", isendValue);

                } else if (response.get(key) instanceof JSONArray) {

                    appendSb("\"" + key + "\"" + ":[", false);

                    itemrArray(TagName, url, (JSONArray) (response.get(key)));

                    boolean isendValue = jsonobject.hasNext();//判断是否还有下一个元素

                    appendSb(" " + " ]", isendValue);

                } else if (response.get(key) instanceof Object) {

                    boolean isendValue = jsonobject.hasNext();//判断是否还有下一个元素
                    getTypeData(TagName, response, key, !isendValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 功能：对基本类型进行转换（String,其他的类型其实不用处理）
     * 说明：对null,进行非空处理，对字符串进行添加 "" 操作
     */
    private static void getTypeData(String TagName, JSONObject response, String key, boolean isEndValue) {
        try {
            if (response.get(key) instanceof Integer) {
                int value = (int) response.get(key);
                appendSb("\t" + "\"" + key + "\"" + ":" + value + "", !isEndValue);

            } else if (response.get(key) instanceof String || null == response.get(key) || TextUtils.equals("null", response.get(key).toString())) {

                if (response.get(key) instanceof String) {

                    String value = (String) response.get(key);
                    if (null == value) {
                        appendSb("\t" + "\"" + key + "\"" + ":" + null, !isEndValue);
                    } else {
                        appendSb("\t" + "\"" + key + "\"" + ":" + "\"" + value + "\"", !isEndValue);
                    }
                } else if (TextUtils.equals("null", response.get(key).toString())) {
                    appendSb("\t" + "\"" + key + "\"" + ":" + null, !isEndValue);

                } else {
                    String value = (String) response.get(key);
                    if (null == value) {
                        appendSb("\t" + "\"" + key + "\"" + ":" + null, !isEndValue);

                    } else {
                        appendSb("\t" + "\"" + key + "\"" + ":" + "\"" + value + "\"", !isEndValue);
                    }
                }
            } else if (response.get(key) instanceof Float) {
                float value = (float) response.get(key);

                appendSb("\t" + "\"" + key + "\"" + ":" + "\"" + value + "\"", !isEndValue);

            } else if (response.get(key) instanceof Double) {

                double value = (double) response.get(key);

                appendSb("\t" + "\"" + key + "\"" + ":" + "\"" + value + "\"", !isEndValue);

            } else if (response.get(key) instanceof Boolean) {

                boolean value = (boolean) response.get(key);

                appendSb("\t" + "\"" + key + "\"" + ":" + "\"" + value + "\"", !isEndValue);

            } else if (response.get(key) instanceof Character) {

                char value = (char) response.get(key);

                appendSb("\t" + "\"" + key + "\"" + ":" + "\"" + value + "\"", !isEndValue);

            } else if (response.get(key) instanceof Long) {

                long value = (long) response.get(key);

                appendSb("\t" + "\"" + key + "\"" + ":" + "\"" + value + "\"", !isEndValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 功能：对JSONArray进行遍历
     * @param TagName
     * @param url
     * @param response;
     */
    private static void itemrArray(String TagName, String url, JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                if (response.get(i) instanceof JSONObject) {
                    appendSb("{", false);
                    prinfrmatJson(TagName, url, (JSONObject) response.get(i));
                    appendSb("  }", response.length() > i + 1);

                } else if (response.get(i) instanceof JSONArray) {
                    itemrArray(TagName, url, (JSONArray) response.get(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     * 功能：添加数据，以及逗号，换行。
     * @param addComma 逗号。
     * */
    private static void appendSb(String append, boolean addComma) {
        josnBuilder.append(append);
        if (addComma) {
            josnBuilder.append(",");
        }
        josnBuilder.append("\r\n");
    }

    /**
     * 功能： LOG输出长度有限制。（需分层输出）
     *
     * @parac max:通过测试不建议修改数据值，修改成4000,会丢失数据。
     * @param tag：
     * @param content
     */
    private static void logOut(String tag, String content) {
        int max = 2048;
        long length = content.length();
        if (length < max || length == max)
        {
            LogUtils.i(tag, content);
        }
        else {
            while (content.length() > max) {
                String logContent = content.substring(0, max);
                content = content.replace(logContent, "");
                Log.i(tag, logContent);
            }
            Log.i(tag, content);
        }
    }
}