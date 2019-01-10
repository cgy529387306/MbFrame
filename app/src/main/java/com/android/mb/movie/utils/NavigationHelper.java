package com.android.mb.movie.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;



/**
 * Activiy 跳转
 * @ author:cgy
 */
public class NavigationHelper {
	
	public static final void startActivity(Activity act, Class<?> toActivity, Bundle bundle, boolean finish) {
		Intent intent = new Intent(act, toActivity);
		if(bundle!=null) {
			intent.putExtras(bundle);
		}
		act.startActivity(intent);
		if (finish){
			act.finish();
		}
	}
	
	
	public static final void startActivityForResult(Activity act, Class<?> toActivity, Bundle bundle, int requestCode) {
		Intent intent = new Intent(act, toActivity);
		if(bundle!=null) {
			intent.putExtras(bundle);
		}
		
		act.startActivityForResult(intent, requestCode);
	}
	
	
	public static final void finish(Activity act, int resultCode, Intent intent){
		if(intent==null) {
			act.setResult(resultCode);
		}
		else {
			act.setResult(resultCode, intent);
		}
		
		act.finish();
	}

}
