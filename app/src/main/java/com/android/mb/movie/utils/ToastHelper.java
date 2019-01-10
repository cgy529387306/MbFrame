package com.android.mb.movie.utils;

import android.widget.Toast;

import com.android.mb.movie.app.MBApplication;


/**
 * Toast相关方法
 * User: pcqpcq
 * Date: 13-9-3
 * Time: 下午5:55
 */
public class ToastHelper {

    private static Toast mToast;

    private static Toast getToast() {
        if (mToast == null) {
            mToast = Toast.makeText(MBApplication.getInstance(), "", Toast.LENGTH_SHORT);
        }
        return mToast;
    }

    /**
     * 显示Toast信息(短)
     *
     * @param resId      显示文本的资源ID
     * @param formatArgs 字符串格式化参数
     */
    public static void showToast(int resId, Object... formatArgs) {
        showToast(Toast.LENGTH_SHORT, resId, formatArgs);
    }

    /**
     * 显示Toast信息(短)
     *
     * @param text 显示文本
     */
    public static void showToast(CharSequence text) {
        showToast(Toast.LENGTH_SHORT, text);
    }

    /**
     * 显示Toast信息(长)
     *
     * @param resId      显示文本的资源ID
     * @param formatArgs 字符串格式化参数
     */
    public static void showLongToast(int resId, Object... formatArgs) {
        showToast(Toast.LENGTH_LONG, resId, formatArgs);
    }

    /**
     * 显示Toast信息(长)
     *
     * @param text 显示文本
     */
    public static void showLongToast(CharSequence text) {
        showToast(Toast.LENGTH_LONG, text);
    }

    /**
     * 显示Toast信息
     *
     * @param duration   时长
     * @param resId      显示文本的资源ID
     * @param formatArgs 字符串格式化参数
     */
    private static void showToast(int duration, int resId, Object... formatArgs) {
        try {
            showToast(duration, MBApplication.getInstance().getString(resId, formatArgs));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示Toast信息
     *
     * @param duration 时长
     * @param text     显示文本
     */
    private static void showToast(int duration, CharSequence text) {
        try {
            Toast toast = getToast();
            if (Helper.isNotNull(toast)) {
                //4.0伤不起啊..
//				toast.cancel();
                toast.setText(text);
                toast.setDuration(duration);
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
