package com.android.mb.movie.retrofit.cache.util;


import com.android.mb.movie.app.MBApplication;

import java.io.IOException;

/**
 * <pre>
 *     author: cgy
 *     time  : 2017/9/6
 *     desc  :
 * </pre>
 */

public class CacheManager {

    //max cache size 50mb
    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 50;

    private static final int DISK_CACHE_INDEX = 0;

    private static final String CACHE_DIR = "responses";

    private volatile static CacheManager mCacheManager;

    public static CacheManager getInstance() {
        if (mCacheManager == null) {
            synchronized (CacheManager.class) {
                if (mCacheManager == null) {
                    mCacheManager = new CacheManager();
                }
            }
        }
        return mCacheManager;
    }

    private CacheManager(){

    }


}
