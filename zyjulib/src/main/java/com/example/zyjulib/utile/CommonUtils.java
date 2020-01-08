package com.example.zyjulib.utile;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.zyjulib.EventBusEntity;
import com.example.zyjulib.GlideCircleWithBorder;
import com.example.zyjulib.R;
import com.example.zyjulib.UCApplication;
import com.example.zyjulib.interf.ICell;
import com.example.zyjulib.resource.Contect;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.zyjulib.resource.Contect.SpConstant.USER_INFO;


public class CommonUtils {

//-------------------------------------UCApplication----------------------------------

    /**
     * 获取Context
     */
    public static Context getContext() {
        return UCApplication.getContext();
    }


    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth() {
        return UCApplication.getScreenWidth();
    }

    public static int getDefoultCDWidth() {
        int defoutH = (int) (((float) (CommonUtils.getScreenWidth() - 20) / 2));
        return defoutH;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight() {
        return UCApplication.getScreenHeight();
    }

    /**
     * 获取主线程中Handler
     */
    public static Handler getHandler() {
        return UCApplication.getHandler();
    }

    /**
     * 获取主线程id
     */
    public static int getMainThreadId() {
        return UCApplication.getMainThreadId();
    }

    /**
     * 判断是否运行在主线程
     */
    public static boolean isRunMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    /**
     * 在子线程中更新UI
     */
    public static void runOnUiThread(Runnable runnable) {
        //判断当前是否是主线程
        if (isRunMainThread()) {
            runnable.run();
        } else {
            //该Handler是主线程创建的
            getHandler().post(runnable);
        }
    }

    /**
     * 判断某个activity是否在前台显示
     */
    public static boolean isForeground(Activity activity) {
        return isForeground(activity, activity.getClass().getName());
    }



    /**
     * 判断某个界面是否在前台,返回true，为显示,否则不是
     */
    @TargetApi(Build.VERSION_CODES.Q)
    public static boolean isForeground(Activity context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

//--------------------------------------获取资源文件-----------------------------------

    /**
     * 获取字符串资源
     */
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    /**
     * 获取Drawable路径
     *
     * @param drawableId
     * @return
     */
    public static String getDrawablePath(int drawableId) {
        Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + getContext().getResources().getResourcePackageName(drawableId) + "/"
                + getContext().getResources().getResourceTypeName(drawableId) + "/"
                + getContext().getResources().getResourceEntryName(drawableId));
        return uri.getPath();
    }

    /**
     * 获取图片资源
     */
    public static Drawable getDrawable(int id) {
        return getContext().getResources().getDrawable(id);
    }

    public static Bitmap getBitmap(int id) {
        return BitmapFactory.decodeResource(getContext().getResources(), id);
    }

    /**
     * 获取大小资源
     */
    public static int getDimension(int id) {
        return getContext().getResources().getDimensionPixelSize(id);
    }

    /**
     * 获取颜色资源
     */
    public static int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    /**
     * 获取字符串数组资源
     */
    public static List<String> getStringArray(int id) {
        //由于通过Arrays.asList转化的是不支持add和remove
        return new ArrayList<>(Arrays.asList(getContext().getResources().getStringArray(id)));
    }

    /**
     * 把modelA对象的属性值赋值给bClass对象的属性。
     *
     * @param modelA
     * @param bClass
     * @param <T>
     * @return
     */
    public static <A, T> T EntityAToEntityB(A modelA, Class<T> bClass) {
        try {
            Gson gson = new Gson();
            String gsonA = gson.toJson(modelA);
            T instanceB = gson.fromJson(gsonA, bClass);
            return instanceB;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从asset目录下读取fileName文件内容
     *
     * @param fileName 待读取asset下的文件名
     * @return 得到省市县的String
     */
    public static String getJson(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = CommonUtils.getContext().getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

//--------------------------------------首选项------------------------------------------------------

    /**
     * 在首选项中保存int类型的数据
     *
     * @param spName 首选项的名称
     * @param key    键名
     * @param value  键值(int类型)
     */
    public static void saveIntSharedPreferences(String spName, String key, int value) {
        SharedPreferences sp = getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        edit.putInt(key, value);
        edit.commit();
    }

    /**
     * 在首选项中保存long类型的数据
     *
     * @param spName 首选项的名称
     * @param key    键名
     * @param value  键值(long类型)
     */
    public static void saveLongSharedPreferences(String spName, String key, long value) {
        SharedPreferences sp = getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        edit.putLong(key, value);
        edit.commit();
    }

    /**
     * 在首选项中保存boolean类型的数据
     *
     * @param spName 首选项的名称
     * @param key    键名
     * @param value  键值(boolean类型)
     */
    public static void saveBooleanSharedPreferences(String spName, String key, boolean value) {
        SharedPreferences sp = getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }

    /**
     * 在首选项中保存String类型的数据
     *
     * @param spName 首选项的名称
     * @param key    键名
     * @param value  键值(String类型)
     */
    public static void saveStringSharedPreferences(String spName, String key, String value) {
        SharedPreferences sp = getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }

    /**
     * 在首选项中获取int类型的数据
     *
     * @param spName   首选项的名称
     * @param key      键名
     * @param defValue 默认值(int)
     */
    public static int getIntSharedPreferences(String spName, String key, int defValue) {
        SharedPreferences sp = getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    /**
     * 在首选项中获取long类型的数据
     *
     * @param spName   首选项的名称
     * @param key      键名
     * @param defValue 默认值(long)
     */
    public static long getLongSharedPreferences(String spName, String key, long defValue) {
        SharedPreferences sp = getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }

    /**
     * 在首选项中获取boolean类型的数据
     *
     * @param spName   首选项的名称
     * @param key      键名
     * @param defValue 默认值(boolean)
     */
    public static boolean getBooleanSharedPreferences(String spName, String key, boolean defValue) {
        SharedPreferences sp = getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 在首选项中获取String类型的数据
     *
     * @param spName   首选项的名称
     * @param key      键名
     * @param defValue 默认值(String)
     */
    public static String getStringSharedPreferences(String spName, String key, String defValue) {
        SharedPreferences sp = getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    /**
     * 在首选项中添加jsonString类型的数据
     */
    public static void addJsonStringSharedPreferences(String spName, String key, String inputText) {
        SharedPreferences sp = CommonUtils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        if (TextUtils.isEmpty(inputText)) {
            return;
        }
        String longHistory = sp.getString(key, "");  //获取之前保存的历史记录
        String[] tmpHistory = longHistory.split(","); //逗号截取 保存在数组中
        List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory)); //将改数组转换成ArrayList
        Editor editor = sp.edit();
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
            editor.putString(key, sb.toString());
            editor.commit();
        } else {
            //之前未添加过
            editor.putString(key, inputText + ",");
            editor.commit();
        }
    }

    /**
     * 在首选项中获取jsonString
     */
    public static List<String> getJsonStringSharedPreferences(String spName, String key, String defValue) {
        SharedPreferences sp = CommonUtils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        String longHistory = sp.getString(key, defValue);
        String[] tmpHistory = longHistory.split(","); //split后长度为1有一个空串对象
        List<String> historyList = new ArrayList<String>(Arrays.asList(tmpHistory));
        if (historyList.size() == 1 && historyList.get(0).equals("")) { //如果没有搜索记录，split之后第0位是个空串的情况下
            historyList.clear();  //清空集合，这个很关键
        }
        return historyList;
    }

    /**
     * 在首选项中删除jsonString记录
     */
    public static void clearStringSharedPreferences(String spName, String key) {
        SharedPreferences sp = CommonUtils.getContext().getSharedPreferences(spName, Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(key, "");
        editor.commit();
    }

    public static Bitmap bitMapScale(Bitmap bitmap,float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale); //长和宽放大缩小的比例
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    public static void fullScreen(Activity activity, boolean isshow) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (isshow) {
                    window.setStatusBarColor(Color.TRANSPARENT);
                } else {
                    window.setStatusBarColor(Color.BLACK);
                }
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    /**
     * 提示未登录时使用
     * 本地退出登陆
     */
    public static void LocalLoginOut() {
        UCApplication.setIsLogin(0);
        CommonUtils.saveStringSharedPreferences(USER_INFO, Contect.SpConstant.SP_Q_USER_INFO, "");
        UCApplication.restoreUserInfo();
    }

    /**
     * 全面屏pop高度设置
     *
     * @param mPopupWindow
     * @param needFullScreen
     */
    public static void fitPopupWindowOverStatusBar(PopupWindow mPopupWindow, boolean needFullScreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");
                mLayoutInScreen.setAccessible(true);
                mLayoutInScreen.set(mPopupWindow, needFullScreen);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置状态栏透明
     *
     * @param activity
     */
    public static void fullScreenWHITE(Activity activity, boolean isshow) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (isshow) {
                    window.setStatusBarColor(Color.TRANSPARENT);
                } else {
                    //设置状态栏文字颜色及图标为深色
                    window.getDecorView().setSystemUiVisibility(
//                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|  //是否占位
                            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//文字颜色及图标为深色
                    window.setStatusBarColor(Color.WHITE);
                }
                //导航栏颜色也可以正常设置

//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    public static void fullScreen(Activity activity, int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
                //导航栏颜色也可以正常设置

//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }


//--------------------------------------------测量--------------------------------------------------


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
//        Resources resources = getContext().getResources();
//        DisplayMetrics metrics = resources.getDisplayMetrics();
//        return dpValue * (metrics.densityDpi / 160f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 改变phoneWindow的背景色
     */
    public static void changeBackground(Activity activity, final float alpha) {
        WindowManager.LayoutParams params = activity.getWindow().getAttributes();
        params.alpha = alpha;
        activity.getWindow().setAttributes(params);
    }


    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getContext().getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }


    public static void EventPostSticky(EventBusEntity eventBusEntity) {
        EventBus.getDefault().postSticky(eventBusEntity);
    }

    public static void EventPost(EventBusEntity eventBusEntity) {
        EventBus.getDefault().post(eventBusEntity);
    }

    public static void EventPostSticky(int eventBus) {
        EventBus.getDefault().postSticky(new EventBusEntity(eventBus));
    }

    public static void EventPost(int eventBus) {
        EventBus.getDefault().post(new EventBusEntity(eventBus));
    }

    //--------------------------------------------图片处理-----------------------------------------------
    public static RequestManager getGlid() {
        return Glide.with(getContext());
    }


    /**
     * 带白色圆边的
     *
     * @param path
     * @param imageView
     */
    public static void WithBorderImg(Object path, ImageView imageView) {
        getGlid().load(path)
                .apply(new RequestOptions()
                        .placeholder(android.R.drawable.ic_menu_report_image).centerCrop()
                        .transform(new GlideCircleWithBorder(getContext(), 3, Color.WHITE)))
                .into(imageView);
    }

    /**
     * 带白色圆边的圆形图
     *
     * @param path
     * @param imageView
     * @param borderWidth 白边宽度
     */
    public static void WithBorderImg(Object path, ImageView imageView, int borderWidth) {
        getGlid().load(path)
                .apply(new RequestOptions()
                        .placeholder(android.R.drawable.ic_menu_report_image).centerCrop()
                        .transform(new GlideCircleWithBorder(getContext(), borderWidth, Color.WHITE)))
                .into(imageView);
    }

    /**
     * 等比例加载图片
     *
     * @param path
     * @param imageView
     */
    public static void setHWImg(Object path, final ImageView imageView) {
        getGlid().asBitmap().load(path).apply(new RequestOptions().placeholder(android.R.drawable.ic_menu_report_image).error(android.R.drawable.ic_menu_report_image).centerCrop()).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                int imageWidth = resource.getWidth();
                int imageHeight = resource.getHeight();
                LogUtils.i("imageView.getWidth() = " + imageView.getWidth());
                int height = getScreenWidth() * imageHeight / imageWidth;
                ViewGroup.LayoutParams para = imageView.getLayoutParams();
                para.height = height;
                para.width = getScreenWidth();
                imageView.setImageBitmap(resource);
            }
        });
    }

    public static void setPhotoDefImage(Object path, ImageView imageView) {
        getGlid().load(path)
                .apply(new RequestOptions()
                        .placeholder(android.R.drawable.ic_menu_report_image).centerCrop())
                .into(imageView);
    }

    public static void setPhotoImage(Object path, ImageView imageView) {
        if (!path.equals(imageView.getTag())) {
            imageView.setTag(null);//需要清空tag，否则报错
            RequestOptions options = RequestOptions.bitmapTransform(new CircleCrop()).circleCrop().placeholder(android.R.drawable.ic_menu_report_image);
            Glide.with(CommonUtils.getContext()).load(path)
                    .apply(options).error(Glide.with(CommonUtils.getContext()).load(android.R.drawable.ic_menu_report_image).apply(options))
                    .into(imageView);
            imageView.setTag(path);
        }
    }

    public static void setImage(Object path, ImageView imageView) {
        getGlid().load(path)
                .into(imageView);
    }

    public static void setImageRes(Object path, int res, ImageView imageView) {
        RequestBuilder<Drawable> resss = Glide.with(getContext()).load(path)
                .apply(new RequestOptions()
                        .placeholder(res).centerCrop());
        Glide.with(getContext()).load(path)
                .apply(new RequestOptions()
                        .placeholder(res).centerCrop()).error(resss).into(imageView);
    }

    public static void WithBorderImg(Object path, int res, ImageView imageView) {
        getGlid().load(path)
                .apply(new RequestOptions()
                        .placeholder(res).centerCrop().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .transform(new GlideCircleWithBorder(getContext(), 3, Color.WHITE)))
                .into(imageView);
    }

    public static void setHttpImage(String path, final int res, final CircleImageView imageView) {
//        imageView.setErrorImage(res);
//        if(path!=null&&!path.isEmpty())
//        imageView.setImageURL(path + "");
        RequestOptions op = new RequestOptions().placeholder(res).diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        RequestBuilder<Bitmap> er = Glide.with(getContext()).asBitmap().load(path)
                .apply(op);
        Glide.with(getContext()).asBitmap()
                .load(path)
                .apply(op)
                .error(er)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        if (imageView != null)
                            imageView.setImageBitmap(resource);
                    }
                });
    }

    /**
     * 是否横屏
     *
     * @param context
     * @return true为横屏，false为竖屏
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否是平板
     * 这个方法是从 Google I/O App for Android 的源码里找来的，非常准确。
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * 照片模糊
     *
     * @param src
     * @return
     */
    public static Bitmap blur(Bitmap src) {
        Bitmap out = Bitmap.createBitmap(src);
        // 创建RenderScript内核对象
        RenderScript script = RenderScript.create(CommonUtils.getContext());
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(script, Element.U8_4(script));

        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间。
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去。
        Allocation inAllo = Allocation.createFromBitmap(script, src);
        Allocation outAllo = Allocation.createFromBitmap(script, out);

        // 设置渲染的模糊程度, 25f是最大模糊度
        blur.setRadius(25f);
        // 设置blurScript对象的输入内存
        blur.setInput(inAllo);
        // 将输出数据保存到输出内存中
        blur.forEach(outAllo);
        // 将数据填充到Allocation中
        outAllo.copyTo(out);

        return out;
    }

    /**
     * view截图
     *
     * @param view
     * @return
     */
    public static Bitmap takeScreenShot(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap res = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return res;
    }

    /**
     * 创建图片
     * 根据图片路径获取Bitmap对象
     */
    public static Bitmap createBitmap(String path) {
        if (path == null) {
            return null;
        }
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(path, newOpts);
    }

    /**
     * 读取图像的旋转度
     */
    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    public static int readBitmapDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    // 旋转图片
    public static Bitmap rotateBitmap(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        return resizedBitmap;
    }



    /**
     * 压缩图片(尺寸压缩,改变像素点,File和bitmap大小都变)
     *
     * @param bitmap 要压缩的bitmap
     *               return 返回压缩后的图片路径
     */
    public static String compressPicture(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options options = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        options.inJustDecodeBounds = true;
        //获取options
        BitmapFactory.decodeStream(isBm, null, options);
        // 获取压缩比
        options.inSampleSize = calculateInSampleSize(options, getScreenWidth(), getScreenHeight());
        options.inJustDecodeBounds = false;
        //获取压缩后图片
        Bitmap bm = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length, options);
        FileOutputStream fos = null;
        //获取图片压缩后路径
        String compressPicturePath = getCompressPicturePath("");
        try {
            fos = new FileOutputStream(compressPicturePath);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            return "";
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return compressPicturePath;
    }

    /**
     * 压缩图片(尺寸压缩)
     *
     * @param path 要压缩图片的路径
     * @return 返回压缩后图片路径
     */
    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    public static String compressPicture(String path) {
        //判断是否需要压缩
        if (new File(path).length() / 1024 < 100) {//小于1M,不需要压缩
            return path;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // 获取压缩比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        options.inJustDecodeBounds = false;

        Bitmap bm = BitmapFactory.decodeFile(path, options);
        if (bm == null) {
            return "";
        }
        int degree = readBitmapDegree(path);
        bm = rotateBitmap(degree, bm);
        FileOutputStream fos = null;
        //获取图片压缩后路径
        String compressPicturePath = getCompressPicturePath(new File(path).getName());
        try {
            fos = new FileOutputStream(compressPicturePath);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (FileNotFoundException e) {
            return "";
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return compressPicturePath;
    }


    //Bitmap对象保存味图片文件
    public static File saveBitmapFile(Bitmap bitmap) {
        String path = CommonUtils.getPicturePath() + "/FenXiangTu.png";
        File file = new File(path);//将要保存图片的路径
        if (file.exists()) {
            file.delete();
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round((float) height / (float) reqHeight);
            int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }


    /**
     * 将图片保存到内存卡
     */
    public static void saveBitmapToSD(Bitmap bitmap) {
        File appDir = new File(getPicturePath());
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getContext().getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
    }

    /**
     * 将图片保存到内存卡
     */
    public static void saveBitmapToSD(Bitmap bitmap, String path) {
        File appDir = new File(path).getParentFile();
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        try {
            FileOutputStream fos = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(getContext().getContentResolver(), path, new File(path).getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }

    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 网络图片转换为Bitmap
     *
     * @param src
     * @return
     */
    public static Bitmap returnBitMap(final String src) {
        try {

            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;

        } catch (IOException e) {
            // Log exception
            return null;
        }

    }

    public static byte[] decodeBitmap(String path) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;// 设置成了true,不占用内存，只获取bitmap宽高
        BitmapFactory.decodeFile(path, opts);
        opts.inSampleSize = computeSampleSize(opts, -1, 1024 * 800);
        opts.inJustDecodeBounds = false;// 这里一定要将其设置回false，因为之前我们将其设置成了true
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.inDither = false;
        opts.inPurgeable = true;
        opts.inTempStorage = new byte[16 * 1024];
        FileInputStream is = null;
        Bitmap bmp = null;
        ByteArrayOutputStream baos = null;
        try {
            is = new FileInputStream(path);
            bmp = BitmapFactory.decodeFileDescriptor(is.getFD(), null, opts);
            double scale = getScaling(opts.outWidth * opts.outHeight,
                    1024 * 600);
            Bitmap bmp2 = Bitmap.createScaledBitmap(bmp,
                    (int) (opts.outWidth * scale),
                    (int) (opts.outHeight * scale), true);
            bmp.recycle();
            baos = new ByteArrayOutputStream();
            bmp2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            bmp2.recycle();
            return baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.gc();
        }
        return baos.toByteArray();
    }

    private static double getScaling(int src, int des) {
        /**
         * 48 目标尺寸÷原尺寸 sqrt开方，得出宽高百分比 49
         */
        double scale = Math.sqrt((double) des / (double) src);
        return scale;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
//--------------------------------------------路径--------------------------------------------------

    public static String getPicturePath() {
        if (!isExistSD()) {
            return getString(R.string.noSD);
        }
        File file = new File(Contect.filePath.picture);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 判断SDCard是否存在,并可写
     *
     * @return
     */
    public static boolean checkSDCard() {
        String flag = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(flag);
    }

    /**
     * 手机拍照返回的图片路径[在拍照的时候已经判断是否存在SD卡,所以此次不需要判断]
     *
     * @return
     */
    public static String getPhotoPicturePath() {
        File file = new File(getPicturePath());
        return file.getAbsolutePath() + "/photo.jpg";
    }

    /**
     * 获取压缩图片路径
     *
     * @param fileName 图片名字
     */
    public static String getCompressPicturePath(String fileName) {
        File file = new File(getPicturePath());
        if (!file.exists()) {
            file.mkdirs();
        }
        UUID uuid = UUID.randomUUID();
        String suffixName = getSuffixName(fileName);
        if (TextUtils.isEmpty(suffixName)) {
            return file + "/" + uuid + ".jpg";
        } else {
            return file + "/" + uuid + suffixName;
        }
    }

    public static String getPublicFileDownDir() {
        if (!isExistSD()) {
            return getString(R.string.noSD);
        }
        File file = new File(Contect.filePath.publicFileDowns);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }
    public static String getPublicLubanFileDownDir() {
        if (!isExistSD()) {
            return getString(R.string.noSD);
        }
        File file = new File(Contect.filePath.publicYaSuoImgFiles);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getAbsolutePath();
    }

    /**
     * 获取文件下载的根路径
     */
    public static String getFileDownRootPath() {
        if (!isExistSD()) {
            return (getString(R.string.noSD));
        }
//        UserInfo userInfo = UCApplication.getUserInfo();
        return Environment.getExternalStorageDirectory() + "/GuaTian/";
    }

    public static String getFileDownPath(String fileName) {
        return getFileDownRootPath() + fileName;
    }

//------------------------------------------布局---------------------------------------------------

    /**
     * ListView添加空布局
     *
     * @param absListView 要设置空布局的ListView
     * @param empty       空布局
     * @param position    设置的位置
     */
    public static void setAbsListViewEmpty(AbsListView absListView, View empty, int position) {
        ViewGroup viewGroup = (ViewGroup) absListView.getParent();
        viewGroup.addView(empty, position);
        absListView.setEmptyView(empty);
    }


    /**
     * 使ExpandableListView展开
     */
    public static void expandExpandableListView(ExpandableListView expandableListView, BaseExpandableListAdapter adapter) {
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }
    }


//--------------------------------------------时间--------------------------------------------------

    /**
     * 将时间字符串转换为Date类型
     *
     * @param time 字符串时间
     * @param type 例如“yyyy-MM-dd HH:mm:ss”
     * @return
     */
    public static Date getDate(String time, String type) {
        SimpleDateFormat format = new SimpleDateFormat(type);
        try {
            return format.parse(time);
        } catch (ParseException e) {
            return new Date(0);
        }
    }

    /**
     * 将Date类型转换为字符串
     */
    public static String getDate(Date date, String type) {
        SimpleDateFormat format = new SimpleDateFormat(type);
        return format.format(date);
    }

    /**
     * 将long时间型转为为String类型
     */
    public static String getDate(long date, String type) {
        return getDate(new Date(date), type);
    }

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Calendar getCalendarZoneTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Calendar getCalendarLastOrNextTime(Date date, int distanct) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, distanct);
        return calendar;
    }

    /**
     * 获取日期0点时刻的时间
     *
     * @return
     */
    public static long getDateZoneTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime().getTime();
    }

    /**
     * 获取某日期是星期几
     * 0对于星期天
     */
    public static int getIntWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }


    public static String getStringWeek(Date date) {
        int intWeek = getIntWeek(date);
        switch (intWeek) {
            case 0:
                return "周日";
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            default:
                return "";
        }
    }

    /**
     * 获取某日期前后几天的日期
     *
     * @param date    当前日期
     * @param distant 负数是前几天，整数是后几天
     * @return
     */
    public static Date getPreAndNextDate(Date date, int distant) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, distant);
        return calendar.getTime();
    }

    /**
     * 获取当前日期上几个月或下几个月对应日期
     *
     * @param distant 距离
     */
    public static Date getPreAndNextMonth(Date date, int distant) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, distant);
        return calendar.getTime();
    }

    /**
     * 获取该日期上下个月的1号日期
     *
     * @param date 当前日期
     * @param type 0-获取上个月,1-获取下个月
     */
    public static Date getPreOrNextMonthDate(Date date, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        if (type == 0) {
            calendar.add(Calendar.MONTH, -1);
        } else if (type == 1) {
            calendar.add(Calendar.MONTH, +1);
        }
        return calendar.getTime();
    }

    /**
     * 获取当期日期本月1号日期
     *
     * @param date
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取当期日期本月最后一天
     */
    public static Date getLastDayofMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //设置该月1号日期
        calendar.set(Calendar.DATE, 1);
        //设置下个月1号日期
        calendar.add(Calendar.MONTH, 1);
        //下个月
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 获取本月天数
     */
    public static int getMothDayNum(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    /**
     * 检查日期是否有效
     * @param year 年
     * @param month 月
     * @param day 日
     * @return boolean
     */
    public static boolean getDateIsTrue(String year, String month, String day){
        try {
            String data = year + month + day;
            SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyyMMdd");
            simpledateformat.setLenient(false);
            simpledateformat.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            LogUtils.e("AppSysDateMgr-->>getDateIsTrue", e.getMessage().toString());
            return false;
        }
        return true;
    }
    /**
     * 根据时分秒的long行，转换为HH:mm类型
     */
    public static String getHoursMinute(long time) {
        StringBuilder sb = new StringBuilder();
        int hour = (int) (time / 3600000);
        int minute = (int) (((time) % 3600000) / 60000);
        if (hour < 10) {
            sb.append("0" + hour + ":");
        } else {
            sb.append(hour + ":");
        }

        if (minute < 10) {
            sb.append("0" + minute);
        } else {
            sb.append(minute + "");
        }
        return sb.toString();
    }

    public static long getHoursMinute(String time) {
        String[] split = time.split(":");
        String hour = split[0];
        String minute = split[1];
        if (hour.startsWith("0")) {
            hour = hour.replaceFirst("0", "");
        }

        if (minute.startsWith("0")) {
            minute = minute.replaceFirst("0", "");
        }
        int hourInt = Integer.parseInt(hour);
        int minuteInt = Integer.parseInt(minute);
        return (long) ((hourInt * 3600000) + (minuteInt * 60000));
    }

    /**
     * 判断时间是否是昨天，今天还是以前
     *
     * @param time
     * @return
     */
    public static String formartData(String time) {
        Date date = getDate(time, "yyyy-MM-dd HH:mm:ss");
        if (date == null) {
            return "";
        }
        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天
        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            return "今天 " + time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {
            return "昨天 " + time.split(" ")[1];
        } else {
            return time;
        }
    }

    /**
     * 判断时间是否是昨天，今天还是以前
     *
     * @param time long型时间
     * @return
     */
    public static String formartData(long time) {
        Date date = new Date(time);
        return formartData(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
    }

    /**
     * 转换为时间
     *
     * @param time
     * @return x天x小时x分
     */
    public static String generateTime(long time) {
        int totalSeconds = (int) (time / 1000);
        int day = totalSeconds / 3600 / 24;
        int hours = totalSeconds / 3600 % 24;
        int minutes = (totalSeconds / 60) % 60;
        int seconds = totalSeconds % 60;
        String timee = "";
        if (day > 0) {
//            timee = String.format("%2d天%2d小时%2d分", day, hours, minutes, seconds);
            timee = String.format("%2d天", day);
        } else if (hours > 0) {
            timee = String.format("%2d小时", hours);
        } else if (minutes > 0) {
            timee = String.format("%2d分钟", minutes);
        } else {
            timee = getString(R.string.newly);
        }

        return timee;
    }
//-------------------------------------------View---------------------------------------------------

    /**
     * 检测制定View是否被遮住显示不全
     *
     * @return
     */
    public static boolean isCover(View view) {
        boolean cover = false;
        Rect rect = new Rect();
        cover = view.getGlobalVisibleRect(rect);

        if (cover) {
            if (rect.width() >= view.getMeasuredWidth() && rect.height() >= view.getMeasuredHeight()) {
                return !cover;
            }
        }
        return true;
    }

    /**
     * 检测制定View是否完全被遮住
     *
     * @return
     */
    public static boolean isCoverAll(View view) {
        boolean cover = false;
        Rect rect = new Rect();
        cover = view.getGlobalVisibleRect(rect);
        LogUtils.i("isCoverAll 1 = " + cover);
        if (cover) {
            if (rect.width() >= view.getMeasuredWidth() && rect.height() >= view.getMeasuredHeight()) {
                LogUtils.i("isCoverAll 2 = " + cover);
                return !cover;
            }
        }
        LogUtils.i("isCoverAll 3 = " + cover);
        return true;
    }

    /**
     * 检测制定View是否被遮住显示不全
     *
     * @return
     */
    public static boolean isCover(View view, ICell.ICell2<Integer, Boolean> iCell) {
        boolean cover = false;
        Rect rect = new Rect();
        cover = view.getGlobalVisibleRect(rect);
        if (cover) {
            if (rect.width() >= view.getMeasuredWidth() && rect.height() >= view.getMeasuredHeight()) {
                iCell.cell(rect.height(), !cover);
                return !cover;
            }
        }
        iCell.cell(rect.height(), cover);
        return true;
    }

//-------------------------------------------文件---------------------------------------------------

    /**
     * 转化尺寸大小的
     *
     * @param totalSize
     * @return
     */
    public static String FormetFileSize(long totalSize) {
        DecimalFormat df = new DecimalFormat("#.00");
        String result = "";
        if (totalSize < 1024) {
            result = totalSize == 0 ? "0B" : df.format((double) totalSize) + "B";
        } else if (totalSize < 1048576) {
            result = df.format((double) totalSize / 1024) + "K";
        } else if (totalSize < 1073741824) {
            result = df.format((double) totalSize / 1048576) + "M";
        } else {
            result = df.format((double) totalSize / 1073741824) + "G";
        }
        return result;
    }

    /**
     * 获取文件后缀名 包括.
     *
     * @param fileName
     */
    public static String getSuffixName(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf("."));
        } else {
            return "";
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String path) {
        return new File(path).getName();
    }


    /**
     * 判断网络状态
     */
    @SuppressLint("MissingPermission")
    public static boolean isOpenNet() {
        ConnectivityManager connManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    /**
     * 判断是否开启wifi
     */
    @SuppressLint("MissingPermission")
    public static boolean isOpenWifi() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWiFiNetworkInfo != null) {
            return mWiFiNetworkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 判断程序是否运行在后台
     */
    public static boolean isRunBackground() {
        if (UCApplication.startActivityCount > 0) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否挂载内存卡
     */
    public static boolean isExistSD() {
        String sdStatus = Environment.getExternalStorageState();
        /* 检测sd是否可用 */
        return sdStatus.equals(Environment.MEDIA_MOUNTED);
    }

    public static void reStartApp() {
        Intent intent = new Intent(getContext(), ((Activity)UCApplication.activityList.get(0)).getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 获取版本号
     */
    public static String getVersionInfo() {
        try {
            PackageManager pm = getContext().getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(getContext().getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    /*
     * 获取版本级别
     * */
    public static int getVersionCode() {
        try {
            PackageManager pm = getContext().getPackageManager();
            PackageInfo packageInfo = pm.getPackageInfo(getContext().getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }


    /**
     * 获取通知栏是否可用
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean isNotificationEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                AppOpsManager mAppOps = (AppOpsManager) getContext().getSystemService(Context.APP_OPS_SERVICE);
                ApplicationInfo appInfo = getContext().getApplicationInfo();
                String pkg = getContext().getPackageName();
                int uid = appInfo.uid;
                Class appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = appOpsClass.getMethod("checkOpNoThrow", Integer.TYPE, Integer.TYPE, String.class);
                Field opPostNotificationValue = appOpsClass.getDeclaredField("OP_POST_NOTIFICATION");
                int value = (int) opPostNotificationValue.get(Integer.class);
                return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        } else {
            return true;//在低于4.4版本中部判断是否具有通知栏权限
        }
    }

    /**
     * 设置textview 的颜色渐变
     *
     * @param text
     */
    public void setTextViewStyles(TextView text) {
        LinearGradient mLinearGradient = new LinearGradient(0, 0, 0, text.getPaint().getTextSize(), Color.parseColor("#00ffffff"),
                Color.parseColor("#ffffffff"), Shader.TileMode.CLAMP);
        text.getPaint().setShader(mLinearGradient);
        text.invalidate();
    }


    /**
     * 对TextView的文字进行特殊处理
     *
     * @param type          类型 0-文字颜色,1-背景颜色,2-字体大小,3-字体斜体，4-字体粗体,5-删除线，6-下划线,7-图片置换
     * @param flag          影响范围设置类型：即在设置后,重新在该范围前后插入字符,是否会受影响。比如设置3，5位置为红色,如果设置前不后扩,及在原来的5位置插入字符,该字符也会受到影响
     *                      0-Spannable.SPAN_EXCLUSIVE_EXCLUSIVE(前后都不包括)。
     *                      1-Spannable.SPAN_EXCLUSIVE_INCLUSIVE(前面不包括，后面包括)。即仅在范围字符的后面插入新字符时会应用新样式
     *                      2-Spannable.SPAN_INCLUSIVE_EXCLUSIVE(前面包括，后面不包括)。
     *                      3- Spannable.SPAN_INCLUSIVE_INCLUSIVE(前后都包括)。
     * @param content       设置的文字
     * @param rid           0,1-对应的颜色资源,2-对应的文字大小,7-图片资源
     * @param startPosition 开始位置(0位置开始)
     * @param endPosition   结束位置(不包含)
     */
    public static SpannableStringBuilder getSpannableString(int type, int flag, String content, int rid, int startPosition, int endPosition) {
        SpannableStringBuilder style = new SpannableStringBuilder(content);

        int spannableFlag = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
        if (flag == 1) {
            spannableFlag = Spannable.SPAN_EXCLUSIVE_INCLUSIVE;
        } else if (flag == 2) {
            spannableFlag = Spannable.SPAN_INCLUSIVE_EXCLUSIVE;
        } else if (flag == 3) {
            spannableFlag = Spannable.SPAN_INCLUSIVE_INCLUSIVE;
        }

        if (type == 0) {
            style.setSpan(new ForegroundColorSpan(rid), startPosition, endPosition, spannableFlag);
        } else if (type == 1) {
            style.setSpan(new BackgroundColorSpan(rid), startPosition, endPosition, spannableFlag);
        } else if (type == 2) {
            style.setSpan(new AbsoluteSizeSpan(rid), startPosition, endPosition, spannableFlag);
        } else if (type == 3) {
            style.setSpan(new StyleSpan(Typeface.ITALIC), startPosition, endPosition, spannableFlag);
        } else if (type == 4) {
            style.setSpan(new StyleSpan(Typeface.BOLD), startPosition, endPosition, spannableFlag);
        } else if (type == 5) {
            style.setSpan(new StrikethroughSpan(), startPosition, endPosition, spannableFlag);
        } else if (type == 6) {
            style.setSpan(new UnderlineSpan(), startPosition, endPosition, spannableFlag);
        } else if (type == 7) {
            Drawable d = getDrawable(rid);
            d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            style.setSpan(new ImageSpan(d), startPosition, endPosition, spannableFlag);
        }
        return style;
    }

    /**
     * 对字符串多处位置进行设置
     *
     * @param content  文本内容
     * @param settings 设置集合
     */
    public static SpannableStringBuilder getSpannableString(String content, List<Map<String, Integer>> settings) {
        SpannableStringBuilder style = new SpannableStringBuilder(content);
        for (Map<String, Integer> map : settings) {
            int flag = map.get("flag");
            int type = map.get("type");
            int rid = map.get("rid");
            int startPosition = map.get("startPosition");
            int endPosition = map.get("endPosition");
            int spannableFlag = Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
            if (flag == 1) {
                spannableFlag = Spannable.SPAN_EXCLUSIVE_INCLUSIVE;
            } else if (flag == 2) {
                spannableFlag = Spannable.SPAN_INCLUSIVE_EXCLUSIVE;
            } else if (flag == 3) {
                spannableFlag = Spannable.SPAN_INCLUSIVE_INCLUSIVE;
            }

            if (type == 0) {
                style.setSpan(new ForegroundColorSpan(rid), startPosition, endPosition, spannableFlag);
            } else if (type == 1) {
                style.setSpan(new BackgroundColorSpan(rid), startPosition, endPosition, spannableFlag);
            } else if (type == 2) {
                style.setSpan(new AbsoluteSizeSpan(rid), startPosition, endPosition, spannableFlag);
            } else if (type == 3) {
                style.setSpan(new StyleSpan(Typeface.ITALIC), startPosition, endPosition, spannableFlag);
            } else if (type == 4) {
                style.setSpan(new StyleSpan(Typeface.BOLD), startPosition, endPosition, spannableFlag);
            } else if (type == 5) {
                style.setSpan(new StrikethroughSpan(), startPosition, endPosition, spannableFlag);
            } else if (type == 6) {
                style.setSpan(new UnderlineSpan(), startPosition, endPosition, spannableFlag);
            } else if (type == 7) {
                Drawable d = getDrawable(rid);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                style.setSpan(new ImageSpan(d), startPosition, endPosition, spannableFlag);
            }
        }
        return style;
    }


    /**
     * 将Uri转换为对应的路径
     */
    public static String translateUri(Context context, final Uri uri) {
        if (null == uri) {
            return "";
        }
        String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


//    /**
//     * 将Json集合类型的字符串转化为List集合
//     */
//    public static ArrayList<String> changeJSONStringToList(String str) {
//        ArrayList<String> temp = new ArrayList<>();
//        if (TextUtils.isEmpty(str)) {
//            return temp;
//        }
//        temp.addAll(JSONArray.parseArray(str, String.class));
//        return temp;
//    }

    /**
     * 将List集合转化为" "**","**" "类型字符串
     */
    public static String getJsonStringForList(List<String> ids) {
        if (ids == null || ids.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String id : ids) {
            sb.append("\"" + id + "\",");
        }
        return sb.deleteCharAt(sb.toString().length() - 1).toString();
    }

    /**
     * 将list集合转化为"**,**"类型字符串
     */
    public static String getStringForList(List<String> ids) {
        if (ids == null || ids.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String string : ids) {
            sb.append(string + ",");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }


    /**
     * 获取Android设备ID
     *
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static String getAndroidId() {
        return Settings.Secure.getString(UCApplication.getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机IMEI
     */
    @SuppressLint("MissingPermission")
    public static String getIMEI(Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Activity.TELEPHONY_SERVICE);
        if (tm != null) {
            return tm.getDeviceId();
        }
        return null;
    }

    /**
     * 获取当前的运营商
     *
     * @return 运营商名字
     */
    public static String getOperator() {
        String ProvidersName = "";
        TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        try {


            @SuppressLint("MissingPermission")
            String IMSI = telephonyManager.getSubscriberId();
            Log.i("qweqwes", "运营商代码" + IMSI);
            if (IMSI != null) {
                if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
                    ProvidersName = "中国移动";
                } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
                    ProvidersName = "中国联通";
                } else if (IMSI.startsWith("46003")) {
                    ProvidersName = "中国电信";
                }
                return ProvidersName;
            } else {
                return "没有获取到sim卡信息";
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static String getResolution() {
        return getScreenWidth() + "*" + getScreenHeight();
    }

    /**
     * 获取版本名称
     *
     * @param context 上下文
     * @return 版本名称
     */
    public static String getVersionName(Context context) {

        //获取包管理器
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //返回版本号
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;

    }

    /**
     * 获取版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    public static int getVersionCode(Context context) {

        //获取包管理器
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //返回版本号
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;

    }

    /**
     * 获取App的名称
     *
     * @param context 上下文
     * @return 名称
     */
    public static String getAppName(Context context) {
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //获取应用 信息
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            //获取albelRes
            int labelRes = applicationInfo.labelRes;
            //返回App的名称
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}

