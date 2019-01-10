package com.android.mb.movie.utils;

import android.app.Activity;

import java.util.HashMap;
import java.util.Set;

/**
 * @author cgy
 * @content 内部Activity管理实例
 * @time 2016/5/3
 */
public class ActivityManager {

    private static ActivityManager activityTaskManager = null;

    private HashMap<String, Activity> activityMap = null;

    private ActivityManager() {
        activityMap = new HashMap<String, Activity>();
    }

    /**
     * 返回activity管理器的唯一实例对象。
     *
     * @return ActivityTaskManager
     */
    public static synchronized ActivityManager getInstance() {
        if (activityTaskManager == null) {
            activityTaskManager = new ActivityManager();
        }
        return activityTaskManager;
    }

    /**
     * 将一个activity添加到管理器。
     *
     * @param activity
     */

    public String putActivity(String name, Activity activity) {
        String currentName = name;
        activityMap.put(currentName, activity);
        return currentName;
    }


    /**
     * 得到保存在管理器中的Activity对象。
     *
     * @param name
     * @return Activity
     */

    public Activity getActivity(String name) {
        return activityMap.get(name);
    }

    /**
     * 返回管理器的Activity是否为空。
     *
     * @return 当且当管理器中的Activity对象为空时返回true，否则返回false。
     */

    public boolean isEmpty() {
        return activityMap.isEmpty();
    }

    /**
     * 返回管理器中Activity对象的个数。
     *
     * @return 管理器中Activity对象的个数。
     */
    public int size() {
        Set<String> activityNames = activityMap.keySet();
        for (String name : activityNames) {
            Activity activity = activityMap.get(name);
            if (activity == null || activity.isFinishing()) {
                activityNames.remove(name);
            }
        }
        return activityMap.size();
    }

    /**
     * 返回管理器中是否包含指定的名字。
     *
     * @param name 要查找的名字。
     * @return 当且仅当包含指定的名字时返回true, 否则返回false。
     */

    public boolean containsName(String name) {
        return activityMap.containsKey(name);
    }

    /**
     * 返回管理器中是否包含指定的Activity。
     *
     * @param activity 要查找的Activity。
     * @return 当且仅当包含指定的Activity对象时返回true, 否则返回false。
     */

    public boolean containsActivity(Activity activity) {
        return activityMap.containsValue(activity);
    }

    /**
     * 关闭所有活动的Activity。
     */
    public void closeAllActivity() {
        Set<String> activityNames = activityMap.keySet();
        for (String string : activityNames) {
            finisActivity(activityMap.get(string));
        }
        activityMap.clear();
    }

    /**
     * 关闭所有活动的Activity除了指定的一个之外。
     *
     * @param nameSpecified 指定的不关闭的Activity对象的名字。
     */

    public void closeAllActivityExceptOne(String nameSpecified) {
        Set<String> activityNames = activityMap.keySet();
        Activity activitySpecified = activityMap.get(nameSpecified);
        for (String name : activityNames) {
            if (!name.equals(nameSpecified)) {
                finisActivity(activityMap.get(name));
            }
        }
        activityMap.clear();
        activityMap.put(nameSpecified, activitySpecified
        );
    }

    /**
     * 移除Activity对象,如果它未结束则结束它。
     *
     * @param name Activity对象的名字。
     */

    public void removeActivity(String name) {
        if (containsName(name)){
            Activity activity = activityMap.remove(name);
            finisActivity(activity);
        }
    }

    public void removeActivityName(String name) {
        activityMap.remove(name);
    }

    /**
     * 结束指定的Activity
     *
     * @param activity 指定的Activity。
     */
    private final void finisActivity(Activity activity) {
        if (activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }
}
