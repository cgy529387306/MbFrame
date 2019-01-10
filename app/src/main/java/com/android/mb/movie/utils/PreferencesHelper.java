package com.android.mb.movie.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.mb.movie.app.MBApplication;


/**
 * <p>SharedPreferences辅助类</p>
 * <p>使用前必须保证ActivityHelper类的init方法被正确调用过</p>
 * <p>可操作多个SharedPreferences,取得时通过getInstance方法进行选择</p>
 *
 */
public class PreferencesHelper {
	private static PreferencesHelper sInstance = new PreferencesHelper();
	//private static Context sApplicationContext;
	private static String sLastSharedPreferencesName = null;
	
	private SharedPreferences mSharePreferences = null;
	
	/**
	 * 取得实例
	 * @return
	 */
	public static PreferencesHelper getInstance() {
		return getInstance(null);
	}
	/**
	 * 通过名称取得实例
	 * @param sharedPreferencesName
	 * @return
	 */
	public static PreferencesHelper getInstance(String sharedPreferencesName){
		if (sInstance == null){
			sInstance = new PreferencesHelper();
		}
		boolean useDefault = true;

		if (Helper.isNull(sInstance.mSharePreferences) || (Helper.isEmpty(sharedPreferencesName) && Helper.isEmpty(sLastSharedPreferencesName))){
			useDefault = Helper.isEmpty(sharedPreferencesName);
		}else if ( !Helper.equalString(sharedPreferencesName, sLastSharedPreferencesName, true) ){
			useDefault = Helper.isEmpty(sharedPreferencesName);
		}else{
			return sInstance;
		}
		
		if (useDefault){
			sInstance.mSharePreferences = PreferenceManager.getDefaultSharedPreferences(MBApplication.getInstance());
			sLastSharedPreferencesName = null;
		}else{
			sInstance.mSharePreferences = MBApplication.getInstance().getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
			sLastSharedPreferencesName = sharedPreferencesName;
		}
		return sInstance;
	}
	
	/**
	 * 获取String类型
	 */
	public String getString(String key) {
		return mSharePreferences.getString(key, "");
	}

	/**
	 * 获取String类型
	 */
	public String getString(String key, String def) {
		return mSharePreferences.getString(key, def);
	}

	/**
	 * 获取float类型
	 */
	public float getFloat(String key) {
		return mSharePreferences.getFloat(key, 0f);
	}
	
	/**
	 * 获取float类型
	 */
	public float getFloat(String key, float def) {
		return mSharePreferences.getFloat(key, def);
	}
	
	/**
	 * 获取int类型
	 */
	public int getInt(String key) {
		return mSharePreferences.getInt(key, 0);
	}

	/**
	 * 获取int类型
	 */
	public int getInt(String key, int def) {
		return mSharePreferences.getInt(key, def);
	}

	/**
	 * 获取long类型
	 */
	public long getLong(String key) {
		return mSharePreferences.getLong(key, 0);
	}

	/**
	 * 获取long类型
	 */
	public long getLong(String key, long def) {
		return mSharePreferences.getLong(key, def);
	}

	/**
	 * 获取boolean类型
	 */
	public boolean getBoolean(String key) {
		return mSharePreferences.getBoolean(key, false);
	}

	/**
	 * 获取boolean类型
	 */
	public boolean getBoolean(String key, boolean def) {
		return mSharePreferences.getBoolean(key, def);
	}

	/**
	 * 设置String类型
	 */
	public void putString(String key, String value) {
		SharedPreferences.Editor ed = mSharePreferences.edit();
		ed.putString(key, value);
		ed.commit();
	}

	/**
	 * 设置Int类型
	 */
	public void putInt(String key, int value) {
		SharedPreferences.Editor ed = mSharePreferences.edit();
		ed.putInt(key, value);
		ed.commit();
	}

	/**
	 * 设置Long类型
	 */
	public void putFloat(String key, float value) {
		SharedPreferences.Editor ed = mSharePreferences.edit();
		ed.putFloat(key, value);
		ed.commit();
	}
	
	/**
	 * 设置Long类型
	 */
	public void putLong(String key, long value) {
		SharedPreferences.Editor ed = mSharePreferences.edit();
		ed.putLong(key, value);
		ed.commit();
	}

	/**
	 *  设置Boolean类型
	 */
	public void putBoolean(String key, boolean value) {
		SharedPreferences.Editor ed = mSharePreferences.edit();
		ed.putBoolean(key, value);
		ed.commit();
	}
	
	/**
	 *  清除
	 */
	public void clear() {
		SharedPreferences.Editor ed = mSharePreferences.edit();
		ed.clear();
		ed.commit();
	}
}
