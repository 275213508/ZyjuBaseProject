package com.example.zyjulib.utile;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.zyjulib.resource.Contect.isTestHttp;


public class LogUtils {


    private static boolean LOG_WRITE_TO_FILE = false;// 日志写入文件开关
    //可以全局控制是否打印log日志
    public static boolean isPrintLog = isTestHttp;//日志打印开关
    private static int LOG_MAXLENGTH = 2048;

    private static String LOG_PATH_SDCARD_DIR = CommonUtils.getFileDownRootPath()+"/log/";// 日志文件在sdcard中的路径


    public static void v(String msg) {
        v("LogUtil", msg);
    }

    public static void v(String tagName, String msg) {
        if (isPrintLog) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.v(tagName + ";line:"+i, " \n"+msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.v(tagName + ";line:"+i, " \n"+msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void d(String msg) {
        d("LogUtil", msg);
    }
    public static void d(String tagName, String msg) {
        if (isPrintLog) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.d(tagName + ";line:"+i, " \n"+msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.d(tagName + ";line:"+i, " \n"+msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void i(String msg) {
        i("LogUtil", msg);
    }

    public static void i(String tagName, String msg) {
        if (isPrintLog) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.i(tagName + ";line:"+i, " \n"+msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.i(tagName + ";line:"+i, " \n"+msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void w(String msg) {
        w("LogUtil", msg);
    }

    public static void w(String tagName, String msg) {
        if (isPrintLog) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.w(tagName + ";line:"+i, " \n"+msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.w(tagName + ";line:"+i, " \n"+msg.substring(start, strLength));
                    break;
                }
            }
        }
    }

    public static void e(String msg) {
        e("LogUtil", msg);
    }
    public static void e(String tagName, String msg) {
        if (isPrintLog) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAXLENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.e(tagName + ";line:"+i," \n"+ msg.substring(start, end));
                    start = end;
                    end = end + LOG_MAXLENGTH;
                } else {
                    Log.e(tagName + ";line:"+i," \n"+ msg.substring(start, strLength));
                    break;
                }
            }
        }
        if (LOG_WRITE_TO_FILE)
            writeLogtoFile("e", tagName, msg + '\n' );
    }


    public static void e_max(String tag, String msg) {
        if(isPrintLog) {
            if (tag == null || tag.length() == 0
                    || msg == null || msg.length() == 0)
                return;
            int segmentSize = 3 * 1024;
            long length = msg.length();
            if (length <= segmentSize) {// 长度小于等于限制直接打印
                Log.e(tag, msg);
            } else {
                while (msg.length() > segmentSize) {// 循环分段打印日志
                    String logContent = " \n"+msg.substring(0, segmentSize);
                    msg = msg.replace(logContent, "");
                    Log.e(tag, logContent);
                }
                Log.e(tag, msg);// 打印剩余日志
            }
        }
    }

    /**
     * 打开日志文件并写入日志
     *
     * @return
     *
     **/
    public static void writeLogtoFile(String logtype, String tag, String text) {// 新建或打开日志文件
        SimpleDateFormat logfile = new SimpleDateFormat("yyyy-MM-dd");
        String needWriteMessage = logfile.format(new Date()) + " " + logtype + " " + tag+" Log.txt";
        File file = new File(LOG_PATH_SDCARD_DIR, needWriteMessage);
        try {
            File path = new File(LOG_PATH_SDCARD_DIR);
            if (!path.exists()) {
                path.mkdirs();
            }
            FileWriter filerWriter = new FileWriter(file, true);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(text);
//            bufWriter.newLine();
            bufWriter.flush();
            bufWriter.close();
            filerWriter.close();
            Log.e(tag,"写入sdcard成功！！！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印 Json
     */
    public static void json(String json) {
        if(isPrintLog) {
            String text = stringToJSON(json);
            if (text != null && !"".equals(text)) {
                // 打印 Json 数据最好换一行再打印会好看一点
                text = " \n" + text;

                int segmentSize = 3 * 1024;
                long length = text.length();
                if (length <= segmentSize) {
                    // 长度小于等于限制直接打印
                    Log.d("LogUtil", text);
                } else {
                    // 循环分段打印日志
                    while (text.length() > segmentSize) {
                        String logContent = text.substring(0, segmentSize);
                        text = text.replace(logContent, "");
                        Log.d("LogUtil", logContent);
                    }
                    // 打印剩余日志
                    Log.d("LogUtil", text);
                }
            }
        }
    }
    public static void json(String tag,String json) {
        if(isPrintLog) {
            String text = stringToJSON(json);
            if (text != null && !TextUtils.isEmpty(text)) {
                // 打印 Json 数据最好换一行再打印会好看一点
                text = " \n" + text;

                int segmentSize = 3 * 1024;
                long length = text.length();
                if (length <= segmentSize) {
                    // 长度小于等于限制直接打印
                    Log.d(tag, text);
                } else {
                    // 循环分段打印日志
                    while (text.length() > segmentSize) {
                        String logContent = text.substring(0, segmentSize);
                        text = text.replace(logContent, "");
                        Log.d(tag, logContent);
                    }
                    // 打印剩余日志
                    Log.d(tag, text);
                }
            }
        }
    }

    /**
     * 将字符串格式化成JSON的格式
     */
    private static String stringToJSON(String strJson) {
        if (strJson == null) {
            return null;
        }
        // 计数tab的个数
        int tabNum = 0;
        StringBuilder builder = new StringBuilder();
        int length = strJson.length();

        char last = 0;
        for (int i = 0; i < length; i++) {
            char c = strJson.charAt(i);
            if (c == '{') {
                tabNum++;
                builder.append(c).append("\n")
                        .append(getSpaceOrTab(tabNum));
            } else if (c == '}') {
                tabNum--;
                builder.append("\n")
                        .append(getSpaceOrTab(tabNum))
                        .append(c);
            } else if (c == ',') {
                builder.append(c).append("\n")
                        .append(getSpaceOrTab(tabNum));
            } else if (c == ':') {
                if (i > 0 && strJson.charAt(i - 1) == '"') {
                    builder.append(c).append(" ");
                } else {
                    builder.append(c);
                }
            } else if (c == '[') {
                tabNum++;
                char next = strJson.charAt(i + 1);
                if (next == ']') {
                    builder.append(c);
                } else {
                    builder.append(c).append("\n")
                            .append(getSpaceOrTab(tabNum));
                }
            } else if (c == ']') {
                tabNum--;
                if (last == '[') {
                    builder.append(c);
                } else {
                    builder.append("\n").append(getSpaceOrTab(tabNum)).append(c);
                }
            } else {
                builder.append(c);
            }
            last = c;
        }
        return builder.toString();
    }

    private static String getSpaceOrTab(int tabNum) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tabNum; i++) {
            sb.append('\t');
        }
        return sb.toString();
    }

}
