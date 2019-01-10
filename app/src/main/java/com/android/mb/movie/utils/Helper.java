package com.android.mb.movie.utils;

import android.graphics.Point;
import android.os.SystemClock;
import android.util.Base64;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * 通用帮助类
 * @author chengfu.bao
 *
 */
public class Helper {
	/**
	 * 时间格式
	 */
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 随机种子
	 */
	private static final String RANDOMSEED = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; 
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	/**
	 * 转换字符串为日期类型
	 * @param dateStr 日期字符串
	 * @return 日期
	 */
	public static Date string2Date(String dateStr) {
		return string2Date(dateStr, DATE_FORMAT);
	}
	/**
	 * 转换字符串为日期类型
	 * @param dateStr 日期字符串
	 * @param dateFormat 日期字符串格式
	 * @return 日期
	 */
	public static Date string2Date(String dateStr, String dateFormat) {
		Date result = null;
		try {
			DateFormat df = new SimpleDateFormat(dateFormat);
			result = df.parse(dateStr);
		} catch (Exception e) {
			result = null;
		}
		return result;
	}
	/**
	 * 转换当前日期为字符串
	 * @return
	 */
	public static String date2String(){
		return date2String(new Date());
	}
	/**
	 * 转换当前日期为字符串
	 * @param dateFormat
	 * @return
	 */
	public static String date2String(String dateFormat){
		return date2String(new Date(), dateFormat);
	}
	/**
	 * 转换日期为字符串
	 * @param date 日期
	 * @return 日期字符串
	 */
	public static String date2String(Date date){
		return date2String(date, DATE_FORMAT);
	}
	/**
	 * 转换日期为字符串
	 * @param date 日期
	 * @param dateFormat 日期字符串格式
	 * @return 日期字符串
	 */
	public static String date2String(Date date, String dateFormat){
		String dateStr = "";
		try{
			DateFormat df = new SimpleDateFormat(dateFormat);
			dateStr = df.format(date);
		}catch (Exception e){
			dateStr = "";
		}
		return dateStr;
	}
	/**
	 * 日期转换成毫秒
	 * @param date
	 * @return
	 */
	public static long date2Long(Date date){
		return date.getTime();
	}
	/**
	 * 当前日期转换成毫秒
	 * @return
	 */
	public static long date2Long(){
		return new Date().getTime();
	}
	/**
	 * 当前日期转换成毫秒字符串
	 * @return
	 */
	public static String date2LongString(){
		return String.valueOf(date2Long());
	}
	/**
	 * 毫秒转换成日期
	 * @param ms
	 * @return
	 */
	public static Date long2Date(long ms){
		return new Date(ms);
	}
	/**
	 * 将时间ms数转换为日期字符串
	 * @param ms
	 * @return
	 */
	public static String long2DateString(long ms){
		return long2DateString(ms, DATE_FORMAT);
	}
	/**
	 * 将时间ms数转换为日期字符串
	 * @param ms
	 * @param dateFormat
	 * @return
	 */
	public static String long2DateString(long ms, String dateFormat){
		String result = null;
		Date date = long2Date(ms);
		if (isNotEmpty(date)){
			result = date2String(date, dateFormat);
		}
		if (isNull(result)){
			result = String.valueOf(ms);
		}
		return result;
	}
	/**
	 * 日期字符串转换为ms值
	 * @param dateString
	 * @return
	 */
	public static long dateString2Long(String dateString){
		return dateString2Long(dateString, DATE_FORMAT);
	}
	/**
	 * 日期字符串转换为ms值
	 * @param dateString
	 * @param dateFormat
	 * @return
	 */
	public static long dateString2Long(String dateString, String dateFormat){
		Date date = string2Date(dateString, dateFormat);
		if (Helper.isNotNull(date)){
			return date2Long(date);
		}
		return 0;
	}
	/**
	 * 转换字符串为布尔值
	 * @param booleanStr 布尔字符串
	 * @return 布尔值
	 */
	public static boolean string2Boolean(String booleanStr){
		if (booleanStr == null){
			return false;
		}
		return booleanStr.equals(TRUE);
	}
	/**
	 * 转换布尔值为字符串
	 * @param bool 布尔值
	 * @return 布尔字符串
	 */
	public static String boolean2String (boolean bool){
		return bool ? TRUE : FALSE;
	}
	/**
	 * 判断是否是真
	 * @param booleanStr　真假字符串
	 * @return
	 */
	public static boolean isTrue(String booleanStr){
		if (Helper.isEmpty(booleanStr)){
			return false;
		}
		return Helper.TRUE.equals(booleanStr.trim());
	}

	/**
	 * 转换为int
	 * @param data 字符串
	 * @return
	 */
	public static int toInt(String data){
		int result = 0;
		try{
			result = Integer.valueOf(data);
		}catch(Exception ex){
			result = Integer.MIN_VALUE;
		}
		return result;
	}
	/**
	 * 字符串转换为整数
	 * @param data 字符串
	 * @param defaultValue 默认值
	 * @return
	 */
	public static int toInt(String data, int defaultValue){
		int result = 0;
		try{
			result = Integer.valueOf(data);
		}catch(Exception ex){
			result = defaultValue;
		}
		return result;
	}
	/**
	 * 转换为long
	 * @param data 字符串
	 * @return
	 */
	public static long toLong(String data){
		long result = 0;
		try{
			result = Long.valueOf(data);
		}catch(Exception ex){
			result = Long.MIN_VALUE;
		}
		return result;
	}
	/**
	 * 转换为float
	 * @param data 字符串
	 * @return
	 */
	public static float toFloat(String data){
		float result = 0;
		try{
			result = Float.valueOf(data);
		}catch(Exception ex){
			result = Float.MIN_VALUE;
		}
		return result;
	}
	/**
	 * 转换为double
	 * @param data 字符串
	 * @return
	 */
	public static double toDouble(String data){
		double result = 0;
		try{
			result = Double.valueOf(data);
		}catch(Exception ex){
			result = Double.MIN_VALUE;
		}
		return result;
	}
	/**
	 * 判断对象是否为NULL或空
	 * @param object 对象
	 * @return 是否为NULL或空
	 */
	public static boolean isEmpty (Object object){
		boolean result = false;
		if (object == null){
			result = true;
		} else {
			if (object instanceof String){
				result = ((String)object).equals("");
			}else if (object instanceof Date) {
				result = ((Date) object).getTime() == 0;
			}else if (object instanceof Long){
				result = ((Long)object).longValue() == Long.MIN_VALUE;
			}else if (object instanceof Integer){
				result = ((Integer)object).intValue() == Integer.MIN_VALUE;
			}else if (object instanceof Collection){
				result = ((Collection<?>)object).size() == 0;
			}else if (object instanceof Map){
				result = ((Map<?, ?>)object).size() == 0;
			}else if (object instanceof JSONObject){
				result = !((JSONObject)object).keys().hasNext();
			}else{
				result = object.toString().equals("");
			}
		}
		return result;
	}
	/**
	 * 判断对象是否不为NULL或空
	 * @param object 对象
	 * @return 是否不为NULL或空
	 */
	public static boolean isNotEmpty(Object object){
		return !Helper.isEmpty(object);
	}
	/**
	 * 判断对象是否为NULL
	 * @param obj 对象
	 * @return 是否为NULL
	 */
	public static boolean isNull(Object obj){
		return obj == null;
	}
	/**
	 * 判断对象是否不为NULL
	 * @param obj 对象
	 * @return 是否不为NULL
	 */
	public static boolean isNotNull(Object obj){
		return !Helper.isNull(obj);
	}
	/**
	 * 若判断对象为null输出空值
	 * @param obj 输出对象
	 * @return 
	 */
	public static String ifNull(Object obj){
		return Helper.ifNull(obj, "");
	}
	/**
	 * 若判断对象为null输出默认值
	 * @param obj 输出对象
	 * @param defaultValue 默认值
	 * @return 
	 */
	public static String ifNull(Object obj, String defaultValue){
		if (Helper.isNull(obj)){
			return defaultValue;
		}else{
			return obj.toString();
		}
	}

	/**
	 * 浮点数转换整数
	 * @param f
	 * @return
	 */
	public static int float2Int(float f){
		return Math.round(f);
	}
	/**
	 * 双字节数转换整数
	 * @param d
	 * @return
	 */
	public static int double2Int(double d){
		return Long.valueOf(Math.round(d)).intValue();
	}
	/**
	 * 集合转字符串
	 * @param obj
	 * @return
	 */
	public static String set2String(Object obj){
		String result = "";
		if (Helper.isNotEmpty(obj)){
			if (obj instanceof Collection){
				Object[] objArray = ((Collection<?>)obj).toArray();
				for(Object tempObj : objArray){
					if (!Helper.isEmpty(tempObj)){
						result += "," + tempObj.toString();
					}
				}
				if (result.length() > 0){
					result = result.substring(1);
				}
			}else{
				result = obj.toString();
			}
		}
		return result;
	}
	/**
	 * 泛型数组转换成列表
	 * @param <T>
	 * @param array
	 * @return
	 */
	public static <T extends Object> List<T> array2List(T[] array){
		List<T> result = new ArrayList<T>();
		if (!Helper.isEmpty(array)){
			for(T entity : array){
				result.add(entity);
			}
		}
		return result;
	}
	
	/**
	 * 计算圆上点旋转的真实坐标
	 * 已知圆心和圆上终点点,并知道经过的旋转度数,求起始点坐标
	 * @param srcPoint 圆上已知点的坐标
	 * @param centerPoint 圆点
	 * @param degree 旋转的角度,以二维坐标系方式
	 * @return
	 */
	public static Point calcRealPoint(Point srcPoint, Point centerPoint, int degree, boolean isZeroPointAtTop){
		Point result = new Point();
		double r = Math.hypot(srcPoint.x - centerPoint.x, srcPoint.y - centerPoint.y);
		double beta = calcAngle(srcPoint, centerPoint, isZeroPointAtTop);
		
		beta += degree;
		double desY = Math.abs(Math.sin(beta * Math.PI / 180) * r);
		double desX = Math.abs(Math.sqrt(r * r - desY * desY));
		beta = beta % 360;
		if (90 < beta && beta < 270) {
			// X为负
			desX = -1 * desX;
		} else {
			// X为正
		}
		if (0 <= beta && beta <= 180) {
			// 项点在上角时Y为负,在下角时Y为正
			desY = isZeroPointAtTop ? -desY : desY;
		} else {
			// 项点在上角时Y为正,在下角时Y为负
			desY = isZeroPointAtTop ? desY : -desY;
		}
		result.set(double2Int(desX + centerPoint.x), double2Int(desY + centerPoint.y));
		return result;
	}
	public static double calcAngle(Point srcPoint, Point centerPoint, boolean isZeroPointAtTop){
		Point srcTempPoint = new Point(srcPoint.x - centerPoint.x, isZeroPointAtTop ? centerPoint.y - srcPoint.y : srcPoint.y - centerPoint.y);
		double r = Math.hypot(srcTempPoint.x, srcTempPoint.y);
		double beta = 0;
		if (srcTempPoint.x >= 0 && srcTempPoint.y >= 0){
			//第一象限
			beta = Math.round(Math.acos(srcTempPoint.x / r) * 180 / Math.PI);
		}else if (srcTempPoint.x < 0 && srcTempPoint.y >= 0){
			//第二象限
			beta = 180 - Math.round(Math.asin(srcTempPoint.y / r ) * 180 / Math.PI);
		}else if (srcTempPoint.x < 0 && srcTempPoint.y < 0){
			//第三象限
			beta = 180 - Math.round(Math.asin(srcTempPoint.y / r ) * 180 / Math.PI);
		}else if (srcTempPoint.x >= 0 && srcTempPoint.y < 0){
			//第四象限
			beta = Math.round(Math.asin(srcTempPoint.y / r ) * 180 / Math.PI) + 360;
		}
		return beta;
	}
	/**
	 * 根据时间生成int类型的tag值
	 * @return
	 */
	public static int createIntTag(){
		int result = -1;
		try{
			result = Long.valueOf(SystemClock.currentThreadTimeMillis() % Integer.MAX_VALUE).intValue();
		}catch(Exception e){
			result = -1;
		}
		return result;
	}
	/**
	 * 取得文件名(从url或本地文件路径)
	 * @param path
	 * @param ignorExtention 是否忽略后缀
	 * @return
	 */
	public static String getFileName(String path, boolean ignorExtention){
		String result = path;
		if (Helper.isNotEmpty(path)){
			int startIndex = 0;
			if (result.contains("\\")){
				startIndex = result.lastIndexOf("\\") + 1;
			}else if (result.contains("/")){
				startIndex = result.lastIndexOf("/") + 1;
			}
			
			int endIndex = ignorExtention ? result.lastIndexOf(".") : result.length();
			result = result.substring(startIndex, endIndex);
		}
		return result;
	}
	/**
	 * 取得文件名,无后缀
	 * @param file
	 * @return
	 */
	public static String getFileName(File file){
		String result = "";
		try {
			result = file.getName().substring(0, file.getName().lastIndexOf("."));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 取得文件名,无后缀
	 * @param path
	 * @return
	 */
	public static String getFileName(String path){
		return getFileName(path, true);
	}
	/**
	 * 取得文件后缀名
	 * @param file
	 * @return
	 */
	public static String getFileExtension(File file){
		String result = "";
		try {
			result = file.getName().substring(file.getName().lastIndexOf(".") + 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 取得文件后缀名
	 * @param path
	 * @return
	 */
	public static String getFileExtension(String path){	
		if (Helper.isNotEmpty(path)){
			if (path.contains(".")){
				return path.substring(path.lastIndexOf(".") + 1);
			}else{
				return path;
			}
		}else{
			return "";
		}
	}
	/**
	 * 使用Base64方式对数据进行编码
	 * @param str
	 * @return
	 */
	public static String encodeByBase64(String str){
		if (Helper.isNotEmpty(str)){
			return encodeByBase64(str.getBytes());
		}
		return str;
	}
	/**
	 * 使用Base64方式对数据进行编码
	 * @param data
	 * @return
	 */
	public static String encodeByBase64(byte[] data){
		return new String(Base64.encode(data, Base64.NO_WRAP));
	}
	/**
	 * 使用Base64方式对字符串进行解码
	 * @param str
	 * @return
	 */
	public static byte[] decodeByBase64(String str){
		return Base64.decode(str, Base64.NO_WRAP);
	}
	/**
	 * 使用Base64方式对字符串进行解码
	 * @param str
	 * @return
	 */
	public static String decodeToStringByBase64(String str){
		return new String(Base64.decode(str, Base64.NO_WRAP));
	}
	/**
	 * 从file转为byte[]
	 * @param file
	 * @return
	 */
	public static byte[] getBytesFromFile(File file) {
		if (file == null) {
			return null;
		}
		FileInputStream stream = null;
		ByteArrayOutputStream out = null;
		try {
			stream = new FileInputStream(file);
			out = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1) {
				out.write(b, 0, n);
			}
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (Helper.isNotNull(stream)){
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (Helper.isNotNull(out)){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	/**
	 * 判断两字符串是否相等(区分大小写)
	 * @param str1
	 * @param str2
	 * @param ignorSpace 是否忽略空格
	 * @return
	 */
	public static boolean equalString(String str1, String str2, boolean ignorSpace){
		return Helper.equalString(str1, str2, ignorSpace, false);
	}
	/**
	 * 判断两字符串是否相等(包含对null的判断)
	 * @param str1
	 * @param str2
	 * @param ignorSpace 是否忽略空格
	 * @param ignorCase  是否忽略大小写
	 * @return
	 */
	public static boolean equalString(String str1, String str2, boolean ignorSpace, boolean ignorCase){
		if (Helper.isNull(str1) && Helper.isNull(str2)){
			return true;
		}
		if ((Helper.isNull(str1) && Helper.isNotNull(str2)) || (Helper.isNull(str2) && Helper.isNotNull(str1))){
			return false;
		}
		if (ignorSpace){
			if (ignorCase){
				return str1.trim().toLowerCase().equals(str2.trim().toLowerCase());
			}else{
				return str1.trim().equals(str2.trim());
			}
		}else{
			if (ignorCase){
				return str1.toLowerCase().equals(str2.toLowerCase());
			}else{
				return str1.equals(str2);
			}
		}
	}
	/**
	 * 创建随机字符串
	 * @param length 随机字符串长度
	 * @return
	 */
	public static String createRandomString(int length){
		StringBuffer result = new StringBuffer();
		if (length > 0){
			Random random = new Random();
			int seedLength = RANDOMSEED.length();
			for (int i = 0; i < length; i++) {
				result.append(RANDOMSEED.charAt(random.nextInt(seedLength)));
			}
		}
		return result.toString();
	}

    /**
     * 获取小于最大随机数的任一随机数
     *
     * @param maxNum 最大随机数
     * @return 随机数
     */
    public static int getRandomNum(int maxNum) {
        if (maxNum < 0) {
            return 0;
        }
        return new Random().nextInt(maxNum);
    }
    
    public static String textFilter(String str) throws PatternSyntaxException {
		// 只允许字母、数字和汉字
		String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return replaceBlank(m.replaceAll("").trim());
	}
    /**
	 * 去除空格换行
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

}
