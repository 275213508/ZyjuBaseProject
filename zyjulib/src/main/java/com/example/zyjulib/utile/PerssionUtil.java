package com.example.zyjulib.utile;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.zyjulib.resource.Contect;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.example.zyjulib.utile.CommonUtils.getContext;

/**
 * Created by 180713 on 2018/9/25.
 */

public class PerssionUtil {

    public static boolean checkReadStoragePermission(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return true;
        }
        int readStoragePermissionState = -1;
        for(int i = 0; i< Contect.PermissionsConstant.PERMISSIONS.length; i++) {
            readStoragePermissionState = ContextCompat.checkSelfPermission(activity, Contect.PermissionsConstant.PERMISSIONS[i]);
        }
        boolean readStoragePermissionGranted = readStoragePermissionState == PackageManager.PERMISSION_GRANTED;

        if (!readStoragePermissionGranted) {
            ActivityCompat.requestPermissions(activity,
                    Contect.PermissionsConstant.PERMISSIONS,
                    Contect.PermissionsConstant.REQUEST_EXTERNAL_READ);
        }
        return readStoragePermissionGranted;
    }
    public static boolean checkReadWriteStoragePermission(Context activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        int readStoragePermissionState = -1;
        for(int i=0;i<Contect.PermissionsConstant.PERMISSIONS_EXTERNAL_READ_WRITE.length;i++) {
            readStoragePermissionState = ContextCompat.checkSelfPermission(activity, Contect.PermissionsConstant.PERMISSIONS_EXTERNAL_READ_WRITE[i]);
        }
        boolean readStoragePermissionGranted = readStoragePermissionState == PackageManager.PERMISSION_GRANTED;

        if (!readStoragePermissionGranted) {
            ActivityCompat.requestPermissions((Activity) activity,
                    Contect.PermissionsConstant.PERMISSIONS_EXTERNAL_READ_WRITE,
                    Contect.PermissionsConstant.REQUEST_EXTERNAL_READ_WRITE);
        }
        return readStoragePermissionGranted;
    }
    public static boolean checkWriteStoragePermission(Fragment fragment) {
        int writeStoragePermissionState =
                ContextCompat.checkSelfPermission(fragment.getContext(), WRITE_EXTERNAL_STORAGE);

        boolean writeStoragePermissionGranted = writeStoragePermissionState == PackageManager.PERMISSION_GRANTED;

        if (!writeStoragePermissionGranted) {
            fragment.requestPermissions(Contect.PermissionsConstant.PERMISSIONS_EXTERNAL_WRITE,
                    Contect.PermissionsConstant.REQUEST_EXTERNAL_WRITE);
        }
        return writeStoragePermissionGranted;
    }

    public static boolean checkCameraPermission(Fragment fragment) {
        int cameraPermissionState = ContextCompat.checkSelfPermission(fragment.getContext(), CAMERA);

        boolean cameraPermissionGranted = cameraPermissionState == PackageManager.PERMISSION_GRANTED;

        if (!cameraPermissionGranted) {
            fragment.requestPermissions(Contect.PermissionsConstant.PERMISSIONS_CAMERA,
                    Contect.PermissionsConstant.REQUEST_CAMERA);
        }
        return cameraPermissionGranted;
    }
    public static boolean checkCameraPermission(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            return true;
        }
        int cameraPermissionState = ContextCompat.checkSelfPermission(activity, CAMERA);

        boolean cameraPermissionGranted = cameraPermissionState == PackageManager.PERMISSION_GRANTED;

        if (!cameraPermissionGranted) {
            activity.requestPermissions(Contect.PermissionsConstant.PERMISSIONS_CAMERA,
                    Contect.PermissionsConstant.REQUEST_CAMERA);
        }
        return cameraPermissionGranted;
    }

    public static boolean checkBlueToothPermission(Activity activity) {
        int cameraPermissionState = ContextCompat.checkSelfPermission(activity, ACCESS_FINE_LOCATION);

        boolean cameraPermissionGranted = cameraPermissionState == PackageManager.PERMISSION_GRANTED;

        if (!cameraPermissionGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.requestPermissions(Contect.PermissionsConstant.PERMISSIONS_BLUETOOTH,
                        Contect.PermissionsConstant.ACCESS_FINE_LOCATION);
            }
        }
        return cameraPermissionGranted;
    }

    /**
     * 是否在后台白名单
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isIgnoringBatteryOptimizations() {
        boolean isIgnoring = false;
        PowerManager powerManager = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(getContext().getPackageName());
        }
        return isIgnoring;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestIgnoreBatteryOptimizations(Activity activity) {
        try {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getContext().getPackageName()));
            activity.startActivityForResult(intent, Contect.RequestCode.BACKGROUD_RUN_PERSSION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 跳转到指定应用的首页
     */
    private void showActivity(Activity activity,@NonNull String packageName) {
        Intent intent = activity.getPackageManager().getLaunchIntentForPackage(packageName);
        activity.startActivity(intent);
    }

    /**
     * 跳转到指定应用的指定页面
     */
    private void showActivity(Activity activity,@NonNull String packageName, @NonNull String activityDir) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(packageName, activityDir));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void goSetting(Activity activity){
        if(isHuawei()){
            goHuaweiSetting(activity);
        }else
        if(isLeTV()){
            goLetvSetting(activity);
        }else
        if(isMeizu()){
            goMeizuSetting(activity);
        }else
        if(isOPPO()){
            goOPPOSetting(activity);
        }else
        if(isSamsung()){
            goSamsungSetting(activity);
        }else
        if(isSmartisan()){
            goSmartisanSetting(activity);
        }else
        if(isVIVO()){
            goVIVOSetting(activity);
        }else
        if (isXiaomi()){
            goXiaomiSetting(activity);
        }
    }
    public boolean isHuawei() {
        if (Build.BRAND == null) {
            return false;
        } else {
            return Build.BRAND.toLowerCase().equals("huawei") || Build.BRAND.toLowerCase().equals("honor");
        }
    }
    private void goHuaweiSetting(Activity activity) {
        try {
            showActivity(activity,"com.huawei.systemmanager",
                    "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
        } catch (Exception e) {
            showActivity(activity,"com.huawei.systemmanager",
                    "com.huawei.systemmanager.optimize.bootstart.BootStartActivity");
        }
    }
    public static boolean isXiaomi() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("xiaomi");
    }
    private void goXiaomiSetting(Activity activity) {
        showActivity(activity,"com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity");
    }
    public static boolean isOPPO() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("oppo");
    }
    private void goOPPOSetting(Activity activity) {
        try {
            showActivity(activity,"com.coloros.phonemanager");
        } catch (Exception e1) {
            try {
                showActivity(activity,"com.oppo.safe");
            } catch (Exception e2) {
                try {
                    showActivity(activity,"com.coloros.oppoguardelf");
                } catch (Exception e3) {
                    showActivity(activity,"com.coloros.safecenter");
                }
            }
        }
    }
    public static boolean isVIVO() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("vivo");
    }
    private void goVIVOSetting(Activity activity) {
        showActivity(activity,"com.iqoo.secure");
    }
    public static boolean isMeizu() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("meizu");
    }
    private void goMeizuSetting(Activity activity) {
        showActivity(activity,"com.meizu.safe");
    }
    public static boolean isSamsung() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("samsung");
    }
    private void goSamsungSetting(Activity activity) {
        try {
            showActivity(activity,"com.samsung.android.sm_cn");
        } catch (Exception e) {
            showActivity(activity,"com.samsung.android.sm");
        }
    }
    public static boolean isLeTV() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("letv");
    }
    private void goLetvSetting(Activity activity) {
        showActivity(activity,"com.letv.android.letvsafe",
                "com.letv.android.letvsafe.AutobootManageActivity");
    }
    public static boolean isSmartisan() {
        return Build.BRAND != null && Build.BRAND.toLowerCase().equals("smartisan");
    }

    private void goSmartisanSetting(Activity activity) {
        showActivity(activity,"com.smartisanos.security");
    }

}
