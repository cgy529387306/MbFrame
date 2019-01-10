package com.android.mb.movie.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetworkHelper {
	
	private static final String TAG = NetworkHelper.class.getSimpleName();
	/**
	 * KEY:网络传输用,user-agent1
	 */
	public static final String NETWORK_KEY_USER_AGENT1 = "User-Agent1";

	public static final short TYPE_IP_V4 = 4;
	public static final short TYPE_IP_V6 = 6;
	
	/**
	 * 检查网络连接是否可用
	 * 
	 * @param context
	 *            上下文
	 * 
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null) {
			return false;
		}
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		if (netInfo == null) {
			return false;
		}
		for (int i = 0; i < netInfo.length; i++) {
			if (netInfo[i].isConnected()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否是wifi连接
	 */
	public static boolean isWifi(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm == null)
			return false;
		return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

	}

	/**
	 * 打开网络设置界面
	 */
	public static void openSetting(Activity activity)
	{
		Intent intent = null;
		if (Build.VERSION.SDK_INT>10){
			intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
		}else{
			intent = new Intent();
			ComponentName cm = new ComponentName("com.android.settings",
					"com.android.settings.WirelessSettings");
			intent.setComponent(cm);
			intent.setAction("android.intent.action.VIEW");
		}
		activity.startActivity(intent);
	}


	/**
	 * 获取当前ip
	 * @param ipType ipv4或者ipv6，请使用{@link #TYPE_IP_V4}或者{@link #TYPE_IP_V6}
	 * @return 当前ip
	 */
	public static String getLocalIpAddress(short ipType) {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
					en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
						enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						switch (ipType) {
						case TYPE_IP_V4:
							if (inetAddress instanceof Inet4Address) {
								return inetAddress.getHostAddress().toString();
							}
							break;
						case TYPE_IP_V6:
							if (inetAddress instanceof Inet6Address) {
								return inetAddress.getHostAddress().toString();
							}
							break;

						default:
							break;
						}
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
}
