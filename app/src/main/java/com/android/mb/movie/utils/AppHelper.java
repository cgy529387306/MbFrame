package com.android.mb.movie.utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


import com.android.mb.movie.app.MBApplication;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Application助手类
 * User: pcqpcq
 * Date: 13-9-4
 * Time: 上午9:37
 */
public class AppHelper {

    private static float sDisplayMetricsDensity = -1;

    /**
     * 计算DPI值对应的PX
     *
     * @param dpi DPI
     * @return px
     */
    public static int calDpi2px(int dpi) {
        if (dpi == 0) {
            return dpi;
        }
        if (sDisplayMetricsDensity < 0) {
            sDisplayMetricsDensity = MBApplication.getInstance().getResources().getDisplayMetrics().density;
        }
        return Helper.float2Int(dpi * sDisplayMetricsDensity);
    }

    private static int[] sScreenSize = null;

    /**
     * 取得屏幕尺寸(0:宽度; 1:高度)
     *
     * @return size of screen
     */
    public static int[] getScreenSize() {
        if (sScreenSize == null) {
            sScreenSize = new int[2];
            DisplayMetrics dm = MBApplication.getInstance().getResources().getDisplayMetrics();
            sScreenSize[0] = dm.widthPixels;
            sScreenSize[1] = dm.heightPixels;
        }
        return sScreenSize;
    }

    /**
     * 取得屏幕宽度
     *
     * @return width of screen
     */
    public static int getScreenWidth() {
        return getScreenSize()[0];
    }

    /**
     * 取得屏幕高度
     *
     * @return height of screen
     */
    public static int getScreenHeight() {
        return getScreenSize()[1];
    }

    /**
     * 获取正在使用的launcher的包名
     * <p>存在多个桌面时且未指定默认桌面时，该方法返回空字串，使用时需处理这个情况</p>
     */
    public static ArrayList<String> getLauncherPackageNames() {
        ArrayList<String> launcherPackageNames = new ArrayList<String>();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> infos = MBApplication.getInstance().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        if (Helper.isEmpty(infos)) {
            return launcherPackageNames;
        }

        for (ResolveInfo info : infos) {
            launcherPackageNames.add(info.activityInfo.packageName);
        }
        return launcherPackageNames;
    }

    /**
     * 从URI取得bitmap
     *
     * @param context context
     * @param uri     Uri
     * @return bitmap
     */
    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        Bitmap result = null;
        InputStream is = null;
        try {
            is = context.getContentResolver().openInputStream(uri);
            if (Helper.isNotNull(is)) {
                result = BitmapFactory.decodeStream(is, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (Helper.isNotNull(is)) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

//	/**
//	 * Bitmap反失真用Paint
//	 */
//	private static Paint mBitmapPaint = null;
//	/**
//	 * 取得Bitmap用反失真Paint
//	 */
//	public static Paint getDrawBitmapPaint(){
//		if (Helper.isNull(mBitmapPaint)){
//			mBitmapPaint = new Paint();
//			mBitmapPaint.setAntiAlias(true);
//			mBitmapPaint.setFilterBitmap(true);
//			mBitmapPaint.setDither(true);
//		}
//		return mBitmapPaint;
//	}

    /**
     * 取得当前软件版本
     *
     * @return version code
     */
    public static int getCurrentVersion() {
        int versionCode = 0;
        try {
            PackageInfo info = MBApplication.getInstance().getPackageManager()
                    .getPackageInfo(MBApplication.getInstance().getPackageName(), 0);
            versionCode = info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 取得当前软件版本名称
     *
     * @return version name
     */
    public static String getCurrentVersionName() {
        String versionName = "";
        try {
            PackageInfo info = MBApplication.getInstance().getPackageManager()
                    .getPackageInfo(MBApplication.getInstance().getPackageName(), 0);
            versionName = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }


    /**
     * 判断指定的服务是否已启动
     *
     * @param serviceFullName 服务全名(包括包名)
     * @return true if api is running
     */
    public static boolean isServiceRunning(String serviceFullName) {
        return isServiceRunning(MBApplication.getInstance(), serviceFullName);
    }

    /**
     * 判断指定的服务是否已启动
     *
     * @param context context
     * @param serviceFullName 服务全名(包括包名)
     * @return true if api is running
     */
    public static boolean isServiceRunning(Context context, String serviceFullName) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = manager.getRunningServices(40);
        if (Helper.isNotEmpty(runningServices)) {
            for (ActivityManager.RunningServiceInfo runningService : runningServices) {
                if (runningService.service.getClassName().equals(serviceFullName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断某个intent是否可用
     * <p> e.g. 判断系统是否可以处理mailto数据：
     * <p>intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:gao7@gao7.com"));
     *
     * @param context 上下文
     * @param intent  intent
     * @return 是否可用
     */
    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    /**
     * 判断是否安装过应用
     * @param context 上下文
     * @param packageName 包名
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        if (Helper.isEmpty(packageName)) {
            return false;
        }
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }

    public static boolean isPermissionGranted(Context context, String permName, String pkgName) {
        int result = context.getPackageManager().checkPermission(permName, pkgName);
        return PackageManager.PERMISSION_GRANTED == result;
    }
    /**
     * 打开指定包名的应用
     * @param context 上下文
     * @param packageName 包名
     * @return 启动成功与否
     */
    public static boolean launchApp(Context context, String packageName) {
        if (Helper.isNull(context) || Helper.isEmpty(packageName)) {
            return false;
        }
        // 获取指定包名的启动Intent
        PackageManager pm = context.getPackageManager();
        Intent intent = pm.getLaunchIntentForPackage(packageName);
        // 判断是否安装
        if (Helper.isNull(intent)) {
            return false;
        } else {
            context.startActivity(intent);
            return true;
        }
    }

    // region 复制剪贴板
    /**
     * 复制到剪贴板
     * @param content 要复制的内容
     */
    public static void copyToClipboard(String content) {
        copyToClipboard(MBApplication.getInstance(), content);
    }

    /**
     * 复制到剪贴板
     * @param context 上下文
     * @param content 要复制的内容
     */
    @SuppressLint("NewApi")
    public static void copyToClipboard(Context context, String content) {
        if (Helper.isNull(context)) {
            context = MBApplication.getInstance();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            android.content.ClipboardManager newClipboardManager =
                    (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            newClipboardManager.setPrimaryClip(android.content.ClipData.newPlainText(content, content));
        } else {
            android.text.ClipboardManager oldClipboardManager =
                    (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            oldClipboardManager.setText(content);
        }
    }

    // endregion 复制剪贴板

    // region ExternalStorage
    /**
     * <p> 取得SD卡路径(无SD卡则使用RAM)
     *
     * @return 类似这样的路径 /mnt/sdcard
     */
    public static String getExternalStoragePath() {
        String result = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && Environment.getExternalStorageDirectory().canWrite()) {
            result = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        if (Helper.isEmpty(result)) {
            result = MBApplication.getInstance().getCacheDir().getPath();
        }
        return result;
    }

    /**
     * <p> 取得基本的缓存路径(无SD卡则使用RAM)
     *
     * @return 类似这样的路径 /mnt/sdcard/Android/data/demo.android/cache/ 或者 /data/data/demo.android/cache/
     */
    public static String getBaseCachePath() {
        String result = null;
        // 有些机型外部存储不按套路出牌的
        try {
        	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
        			&& Environment.getExternalStorageDirectory().canWrite()) {
        		result = MBApplication.getInstance().getExternalCacheDir().getAbsolutePath().concat(File.separator);
        	}
        }catch (Exception e) {
        	e.printStackTrace();
        }
        if (Helper.isEmpty(result)) {
            result = MBApplication.getInstance().getCacheDir().getPath().concat(File.separator);
        }
        return result;
    }

    /**
     * <p> 取得默认类型的基本的文件路径(无SD卡则使用RAM)
     * <p> 默认为下载目录
     *
     * @return 类似这样的路径 /mnt/sdcard/Android/data/demo.android/files/Download/ 或者 /data/data/demo.android/files/
     */
    public static String getBaseFilePath() {
        String result = null;
        try {
        	if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && Environment.getExternalStorageDirectory().canWrite()) {
                result = MBApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                        .getAbsolutePath().concat(File.separator);
            }
        }catch (Exception e) {
        	e.printStackTrace();
        }
        if (Helper.isEmpty(result)) {
            result = MBApplication.getInstance().getFilesDir().getPath().concat(File.separator);
        }
        return result;
    }

    /**
     * 取得指定类型的基本的文件路径(无SD卡则使用RAM)
     *
     * @param type 参见 {@link Context} getExternalFilesDir(String type)
     *             <p> Environment常量：
     *             <p> DIRECTORY_MUSIC, DIRECTORY_PODCASTS, DIRECTORY_RINGTONES, DIRECTORY_ALARMS,
     *             DIRECTORY_NOTIFICATIONS, DIRECTORY_PICTURES, or DIRECTORY_MOVIES.
     * @return 类似这样的路径 /mnt/sdcard/Android/data/demo.android/files/Download/ 或者 /data/data/demo.android/files/
     */
    public static String getBaseFilePath(String type) {
        if (Helper.isEmpty(type)) {
            return getBaseFilePath();
        } else {
            String result = null;
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                    && Environment.getExternalStorageDirectory().canWrite()) {
                result = MBApplication.getInstance().getExternalFilesDir(type).getAbsolutePath().concat(File.separator);
            }
            if (Helper.isEmpty(result)) {
                result = MBApplication.getInstance().getFilesDir().getPath().concat(File.separator);
            }
            return result;
        }
    }
    // endregion ExternalStorage
    /**
     * 判断软键盘是否弹出
     */
    public static boolean isShowKeyboard( View v) {
        InputMethodManager imm = (InputMethodManager) MBApplication.getInstance()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm!=null && imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
            return true;
            //软键盘已弹出
        } else {
            return false;
            //软键盘未弹出
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param view the view you want to hide keyboard
     */
    public static void hideSoftInputFromWindow(View view) {
        if (Helper.isNotNull(view)) {
            InputMethodManager imm = (InputMethodManager) MBApplication.getInstance()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }


}
