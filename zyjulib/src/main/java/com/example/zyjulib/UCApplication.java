package com.example.zyjulib;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.alibaba.fastjson.JSON;
import com.example.zyjulib.resource.Contect;
import com.example.zyjulib.utile.CommonUtils;
import com.example.zyjulib.utile.LogUtils;
import com.githang.androidcrash.AndroidCrash;
import com.githang.androidcrash.reporter.httpreporter.CrashHttpReporter;
import com.githang.androidcrash.reporter.mailreporter.CrashEmailReporter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.example.zyjulib.resource.Contect.SpConstant.USER_INFO;
import static com.example.zyjulib.resource.Contect.isTestHttp;


public class UCApplication extends Application {


    /**
     * 屏幕宽度
     */
    private static int screenWidth;
    /**
     * 屏幕高度
     */
    private static int screenHeight;

    private static Context context;

    private static Handler mHandler;

    /**
     * 主线程id
     */
    private static int mianThreadId;

    private static UserInfo mUserInfo;


    /**
     * 判断用户是否在登录状态
     * 0-未登录,1-登录中,2-已登录
     */
    private static int isLogin;


    /**
     * 开启Activity的个数(作为是否在后台判断)
     */
    public static int startActivityCount;

    public static List<Activity> activityList;
    private static Activity mCurrentActivity;

    private IntentFilter intentFilter;


    public static final String CONV_TITLE = "conv_title";
    public static final int IMAGE_MESSAGE = 1;
    public static final int TAKE_PHOTO_MESSAGE = 2;
    public static final int TAKE_LOCATION = 3;
    public static final int FILE_MESSAGE = 4;
    public static final int TACK_VIDEO = 5;
    public static final int TACK_VOICE = 6;
    public static final int BUSINESS_CARD = 7;
    public static final int REQUEST_CODE_SEND_FILE = 26;


    public static final int RESULT_CODE_ALL_MEMBER = 22;
    public static Map<Long, Boolean> isAtMe = new HashMap<>();
    public static Map<Long, Boolean> isAtall = new HashMap<>();

    public static long registerOrLogin = 1;
    public static final int REQUEST_CODE_TAKE_PHOTO = 4;
    public static final int REQUEST_CODE_SELECT_PICTURE = 6;
    public static final int REQUEST_CODE_CROP_PICTURE = 18;
    public static final int REQUEST_CODE_CHAT_DETAIL = 14;
    public static final int RESULT_CODE_FRIEND_INFO = 17;
    public static final int REQUEST_CODE_ALL_MEMBER = 21;
    public static final int RESULT_CODE_EDIT_NOTENAME = 29;
    public static final String NOTENAME = "notename";
    public static final int REQUEST_CODE_AT_MEMBER = 30;
    public static final int RESULT_CODE_AT_MEMBER = 31;
    public static final int RESULT_CODE_AT_ALL = 32;
    public static final int SEARCH_AT_MEMBER_CODE = 33;

    public static final int RESULT_BUTTON = 2;
    public static final int START_YEAR = 1900;
    public static final int END_YEAR = 2050;
    public static final int RESULT_CODE_SELECT_FRIEND = 23;

    public static final int REQUEST_CODE_SELECT_ALBUM = 10;
    public static final int RESULT_CODE_SELECT_ALBUM = 11;
    public static final int RESULT_CODE_SELECT_PICTURE = 8;
    public static final int REQUEST_CODE_BROWSER_PICTURE = 12;
    public static final int RESULT_CODE_BROWSER_PICTURE = 13;
    public static final int RESULT_CODE_SEND_LOCATION = 25;
    public static final int RESULT_CODE_SEND_FILE = 27;
    public static final int REQUEST_CODE_SEND_LOCATION = 24;
    public static final int REQUEST_CODE_FRIEND_INFO = 16;
    public static final int RESULT_CODE_CHAT_DETAIL = 15;
    public static final int REQUEST_CODE_FRIEND_LIST = 17;
    public static final int ON_GROUP_EVENT = 3004;
    public static final String DELETE_MODE = "deleteMode";
    public static final int RESULT_CODE_ME_INFO = 20;

    public static final String DRAFT = "draft";
    public static final String CONV_TYPE = "conversationType"; //value使用 ConversationType
    public static final String ROOM_ID = "roomId";
    public static final String GROUP_ID = "groupId";
    public static final String POSITION = "position";
    public static final String MsgIDs = "msgIDs";
    public static final String MSG_JSON = "msg_json";
    public static final String MSG_LIST_JSON = "msg_list_json";
    public static final String NAME = "name";
    public static final String ATALL = "atall";
    public static final String SEARCH_AT_MEMBER_NAME = "search_at_member_name";
    public static final String SEARCH_AT_MEMBER_USERNAME = "search_at_member_username";
    public static final String SEARCH_AT_APPKEY = "search_at_appkey";

    public static final String MEMBERS_COUNT = "membersCount";

    public static String PICTURE_DIR = "sdcard/JChatDemo/pictures/";
    private static final String JCHAT_CONFIGS = "JChat_configs";
    public static String FILE_DIR = "sdcard/JChatDemo/recvFiles/";
    public static String VIDEO_DIR = "sdcarVIDEOd/JChatDemo/sendFiles/";
    public static String THUMP_PICTURE_DIR;
    public static final String TARGET_ID = "targetId";
    public static final String ATUSER = "atuser";
    public static final String TARGET_APP_KEY = "targetAppKey";
    public static int maxImgCount;               //允许选择图片最大数
    public static final String GROUP_NAME = "groupName";
    public static String groupAvatarPath;


    public static List<UserInfo> mFriendInfoList = new ArrayList<>();
    public static List<UserInfo> mSearchGroup = new ArrayList<>();
    public static List<UserInfo> mSearchAtMember = new ArrayList<>();
    public static List<UserInfo> alreadyRead = new ArrayList<>();
    public static List<UserInfo> unRead = new ArrayList<>();
    public static List<String> forAddFriend = new ArrayList<>();
    public static List<String> forAddIntoGroup = new ArrayList<>();
    public static ArrayList<String> selectedUser;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("日志:", "onCreate: " + isTestHttp);
        if (!isTestHttp) {
            initEmailReporter();
        }
//        initHttpReporter();

        LogUtils.e("UCApplication启动");
        //计算屏幕宽高
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        context = getApplicationContext();
        mHandler = new Handler();
        mianThreadId = android.os.Process.myTid();

        activityList = new ArrayList<>();


        //设置activity前后台监听
        registerActivityLifecycleCallbacks(new MyActivityLifecycleCallbacks());
    }


    /**
     * 使用EMAIL发送日志
     */
    private void initEmailReporter() {
        CrashEmailReporter reporter = new CrashEmailReporter(this) {
            /**
             * 重写此方法，可以弹出自定义的崩溃提示对话框，而不使用系统的崩溃处理。
             * @param thread
             * @param ex
             */
            @Override
            public void closeApp(Thread thread, Throwable ex) {
                CommonUtils.reStartApp();
//                final Activity activity = getCurrentActivity();
////                 自定义弹出对话框
//                new AlertDialog.Builder(activity).
//                        setMessage("程序发生异常，即将重启软件").
//                        setCancelable(false).
//                        setPositiveButton("确定", (dialog, which) -> CommonUtils.reStartApp()).create().show();

            }
        };

        reporter.setReceiver(Contect.EmailConfig.ReceiveEmail);
        reporter.setSender(Contect.EmailConfig.SendEmail);
        reporter.setSendPassword(Contect.EmailConfig.SendEmailPassword);//邮箱登陆授权码
        reporter.setSMTPHost(Contect.EmailConfig.SMTPHost);
        reporter.setPort(Contect.EmailConfig.Port);
        AndroidCrash.getInstance().setCrashReporter(reporter).init(this);
    }

    /**
     * 使用HTTP发送日志
     */
    private void initHttpReporter() {
        CrashHttpReporter reporter = new CrashHttpReporter(this) {
            /**
             * 重写此方法，可以弹出自定义的崩溃提示对话框，而不使用系统的崩溃处理。
             * @param thread
             * @param ex
             */
            @Override
            public void closeApp(Thread thread, Throwable ex) {
                final Activity activity = getCurrentActivity();
                Toast.makeText(activity, "发生异常", Toast.LENGTH_SHORT).show();
//                 自定义弹出对话框
                new AlertDialog.Builder(activity).
                        setMessage("程序发生异常，即将重启软件").
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                CommonUtils.reStartApp();
                            }
                        }).setCancelable(false).create().show();
            }
        };

        reporter.setUrl("http://crashreport.jd-app.com/your_receiver").setFileParam("fileName")
                .setToParam("to").setTo("275213508@qq.com")
                .setTitleParam("subject").setBodyParam("message");
        reporter.setCallback(new CrashHttpReporter.HttpReportCallback() {
            @Override
            public boolean isSuccess(int i, String s) {
                return s.endsWith("ok");
            }
        });
        AndroidCrash.getInstance().setCrashReporter(reporter).init(this);
    }


    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        LogUtils.d("onTerminate");
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        // 低内存的时候执行
        LogUtils.d("onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        // 程序在内存清理的时候执行
        LogUtils.d("onTrimMemory");
        super.onTrimMemory(level);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    //    public <T> void ThreadPoolAsyncEx(String threadName,Callable<T> callable){
//        ThreadPoolUtile.getInstenc().setThreadName(threadName).getExecutor().async(callable);
//    }

    /**
     * 不去手动将UserInfo置null,在登录过程中仅仅将mUserInfo的值改变
     */
    public static synchronized UserInfo getUserInfo() {
        if (mUserInfo == null) {
            if (getIsLogin() != 2) {//没登陆
                mUserInfo = new UserInfo();
                SharedPreferences sp = getContext().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
                mUserInfo.setId(CommonUtils.getAndroidId());
                mUserInfo.setUuid(sp.getString(Contect.SpConstant.SP_USER_ANDROID_ID, CommonUtils.getAndroidId()));
                return mUserInfo;
            } else {//已登录
                mUserInfo = new UserInfo();
                SharedPreferences sp = getContext().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
                String userjson = sp.getString(Contect.SpConstant.SP_LOGIN_REQUEST_USER_INFO, "");
                UserInfo info = JSON.parseObject(userjson, UserInfo.class);
                String userjson1 = sp.getString(Contect.SpConstant.SP_Q_USER_INFO, "");
                UserInfo info1 = JSON.parseObject(userjson1, UserInfo.class);
                mUserInfo.setId(info.getId());
                mUserInfo.setUuid(info.getUuid());
                mUserInfo.setBgImage(info.getBgImage());
                mUserInfo.setToken(info.getToken());
                mUserInfo.setName(info1.getName());
                mUserInfo.setNickName(info1.getNickName());
                mUserInfo.setIcon(info1.getIcon());
                mUserInfo.setPhone(info1.getPhone());
                mUserInfo.setSex(info1.getSex());
                mUserInfo.setAddress(info1.getAddress());
                mUserInfo.setXxaddress(info1.getXxaddress());
                mUserInfo.setPersonalizedSign(info1.getPersonalizedSign());
                mUserInfo.setIdCode(info1.getIdCode());
                mUserInfo.setLevel(info1.getLevel());
                mUserInfo.setSynopsis(info1.getSynopsis());
                mUserInfo.setMail(info1.getMail());
            }
        }
        return mUserInfo;
    }


    /**
     * 重置UserInfo属性
     */
    public static void restoreUserInfo() {
//        String islogin =  CommonUtils.getStringSharedPreferences(USER_INFO, Contect.SpConstant.SP_ISLOGIN, "0");
//        UCApplication.setIsLogin(Integer.parseInt(islogin));
        mUserInfo = getUserInfo();
        SharedPreferences sp = getContext().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        String userjson = sp.getString(Contect.SpConstant.SP_LOGIN_REQUEST_USER_INFO, "");
        UserInfo info = new UserInfo();
        if (!userjson.isEmpty()) {
            info = JSON.parseObject(userjson, UserInfo.class);
        }
        String userjson1 = sp.getString(Contect.SpConstant.SP_Q_USER_INFO, "");
        UserInfo info1 = new UserInfo();
        if (!userjson1.isEmpty()) {
            info1 = JSON.parseObject(userjson1, UserInfo.class);
        }
        mUserInfo.setId(info.getId().isEmpty() ? mUserInfo.getId() : info.getId());
        if (UCApplication.getIsLogin() != 2) {
            mUserInfo.setUuid(sp.getString(Contect.SpConstant.SP_USER_ANDROID_ID, CommonUtils.getAndroidId()));
            mUserInfo.setToken("");
        } else {
            mUserInfo.setUuid(info.getUuid());
            mUserInfo.setToken(info.getToken());
        }
        mUserInfo.setName(info1.getName());
        mUserInfo.setBgImage(info1.getBgImage());
        mUserInfo.setNickName(info1.getNickName());
        mUserInfo.setIcon(info1.getIcon());
        mUserInfo.setPhone(info1.getPhone());
        mUserInfo.setSex(info1.getSex());
        mUserInfo.setAddress(info1.getAddress());
        mUserInfo.setXxaddress(info1.getXxaddress());
        mUserInfo.setPersonalizedSign(info1.getPersonalizedSign());
        mUserInfo.setIdCode(info1.getIdCode());
        mUserInfo.setLevel(info1.getLevel());
        mUserInfo.setSynopsis(info1.getSynopsis());
        mUserInfo.setMail(info1.getMail());
//        CommonUtils.EventPostSticky(Contect.eventPost.upAllActivity);
    }

    /**
     * .获取顶部statusBar高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 获取底部navigationBar高度
     *
     * @return
     */
    private int getNavigationBarHeight() {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 获取设备是否存在NavigationBar
     *
     * @return
     */
    public static boolean checkDeviceHasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            //do something
        }
        return hasNavigationBar;
    }

    //退出登录的时候清空数据
    public static void clearDate() {
        //初始化登录状态
        isLogin = 0;
    }

    /**
     * 0-未登录,1-登录中,2-已登录
     *
     * @param islogin
     */
    public static void setIsLogin(int islogin) {
        isLogin = islogin;
        CommonUtils.saveStringSharedPreferences(USER_INFO, Contect.SpConstant.SP_ISLOGIN, islogin + "");
        if (islogin == 2) {
            CommonUtils.EventPost(Contect.eventPost.Login);
        } else {
            CommonUtils.EventPost(Contect.eventPost.LoginOut);
        }
    }

    public static int getIsLogin() {
        return isLogin;
    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static int getMainThreadId() {
        return mianThreadId;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private class MyActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            setCurrentActivity(activity);
            activityList.add(activity);
            LogUtils.e("app_state", "onActivityCreated     " + activity.toString() + "   ;activityCount.size = " + activityList.size());
        }

        @Override
        public void onActivityStarted(Activity activity) {
            startActivityCount++;
            LogUtils.e("app_state", "onActivityStarted    " + activity.toString() + "   ;activityCount = " + startActivityCount);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            LogUtils.e("app_state", "onActivityResumed    " + activity.toString());

        }

        @Override
        public void onActivityPaused(Activity activity) {
            LogUtils.e("app_state", "onActivityPaused    " + activity.toString());
        }

        @Override
        public void onActivityStopped(Activity activity) {
            startActivityCount--;
            LogUtils.e("app_state", "onActivityStopped    " + activity.toString() + "   ;activityCount = " + startActivityCount);

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            LogUtils.e("app_state", "onActivitySaveInstanceState    " + activity.toString());

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            activityList.remove(activity);
            LogUtils.e("app_state", "onActivityDestroyed    " + activity.toString() + "   ;activityCount.size = " + activityList.size());

        }
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    private void setCurrentActivity(Activity activity) {
        mCurrentActivity = activity;
    }
}
