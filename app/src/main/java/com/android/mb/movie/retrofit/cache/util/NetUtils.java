package com.android.mb.movie.retrofit.cache.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 *
 * <pre>
 *     author: cgy
 *     time  : 2017/9/5
 *     desc  : 判断是否有网络.
 * </pre>
 *
 */

public class NetUtils {

    public static boolean isConnectNet(Context context){
        if (context!=null){
            ConnectivityManager conManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
            return networkInfo == null ? false : networkInfo.isAvailable();
        }
        return false;
    }
}
