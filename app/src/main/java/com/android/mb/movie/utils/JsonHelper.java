package com.android.mb.movie.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * <p>JSON帮助类(基于Gson)</p>
 * <p>可使用@SerializedName为POJO类中的成员变量起别名</p>
 * <p>可使用@Expose排除POJO类中的某一成员变量,但现在还未解决...但不怎么影响使用...只是不怎么</p>
 * <p>在使用列表结构时,请统一定义为List,实例化时使用LinkedList,要不会报类型不对</p>
 */
public class JsonHelper {

	private static Gson sGson = null;
	private static Gson sExposeGson = null;
	/**
	 * 取得gson对象
	 * @return gson对象
	 */
	public static Gson getGson(){
		if (sGson == null){
			sGson = new Gson();
		}
		return sGson;
	}
	/**
	 * 取得具有Expose属性的gson对象
	 * @return 具有Expose属性的gson对象
	 */
	public static Gson getExposeGson(){
		if (sExposeGson == null){
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.excludeFieldsWithoutExposeAnnotation();
            sExposeGson = gsonBuilder.create();
		}
		return sExposeGson;
	}
	/**
	 * 将对象序列化为JSON字符串
	 * @param src json对象
	 * @return JSON字符串
	 */
	public static String toJson(Object src){
		return getGson().toJson(src);
	}
	/**
	 * 根据指定类型将对象序列化为JSON字符串
	 * @param src json对象
	 * @param typeOfSrc 对象类型
	 * @return JSON字符串
	 */
	public static String toJson(Object src, Type typeOfSrc){
		return getGson().toJson(src, typeOfSrc);
	}
	/**
	 * 将对象序列化为JSON字符串，过滤Expose
	 * @param src json对象
	 * @return JSON字符串
	 */
	public static String toExposeJson(Object src){
		return getExposeGson().toJson(src);
	}
	/**
	 * 根据指定类型将对象序列化为JSON字符串，过滤Expose
     * @param src json对象
     * @param typeOfSrc 对象类型
     * @return JSON字符串
     */
	public static String toExposeJson(Object src, Type typeOfSrc){
		return getExposeGson().toJson(src, typeOfSrc);
	}
	/**
	 * 从JSON字符串反序列化为指定类型的对象
     * @param json JSON字符串
     * @param classOfT 对象类型
     * @return 实体
     */
	public static <T> T fromJson(String json, Class<T> classOfT){
		try{
			return getGson().fromJson(json, classOfT);
		}catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从JSON对象反序列化为指定类型的对象
     * @param json JSON字符串
     * @param classOfT 对象类型
     * @return 实体
     */
	public static <T> T fromJson(JSONObject json, Class<T> classOfT){
		if (Helper.isNotNull(json)){
			return fromJson(json.toString(), classOfT);
		}
		return null;
	}
	/**
	 * 从JSON字符串反序列化为指定类型的对象
     * @param json JSON字符串
     * @param typeOfT 对象类型
     * @return 实体
     */
	public static <T> T fromJson(String json, Type typeOfT){
		try{
			return getGson().fromJson(json, typeOfT);
		}catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从JSON对象反序列化为指定类型的对象
     * @param json JSON字符串
     * @param typeOfT 对象类型
     * @return 实体
     */
	public static <T> T fromJson(JSONObject json, Type typeOfT){
		if (Helper.isNotNull(json)){
			return fromJson(json.toString(), typeOfT);
		}
		return null;
	}
	/**
	 * 从JSON字符串反序列化为指定类型的对象，过滤Expose
     * @param json JSON字符串
     * @param classOfT 对象类型
     * @return 实体
     */
	public static <T> T fromExposeJson(String json, Class<T> classOfT){
		try{
			return getExposeGson().fromJson(json, classOfT);
		}catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从JSON对象反序列化为指定类型的对象，过滤Expose
     * @param json JSON字符串
     * @param classOfT 对象类型
     * @return 实体
     */
	public static <T> T fromExposeJson(JSONObject json, Class<T> classOfT){
		if (Helper.isNotNull(json)){
			return fromExposeJson(json.toString(), classOfT);
		}
		return null;
	}
	/**
	 * 从JSON字符串反序列化为指定类型的对象，过滤Expose
     * @param json JSON字符串
     * @param typeOfT 对象类型
     * @return 实体
     */
	public static <T> T fromExposeJson(String json, Type typeOfT){
		try{
			return getExposeGson().fromJson(json, typeOfT);
		}catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从JSON对象反序列化为指定类型的对象，过滤Expose
     * @param json JSON字符串
     * @param typeOfT 对象类型
     * @return 实体
     */
	public static <T> T fromExposeJson(JSONObject json, Type typeOfT){
		if (Helper.isNotNull(json)){
			return fromExposeJson(json.toString(), typeOfT);
		}
		return null;
	}

    // region 废弃方法
    /**
     * JSON的编码前缀
     * @deprecated 请使用RequestHelper中的对应常量
     */
    @Deprecated
    public static final String PREFIX_ENCODE_JSON_STRING = "0";
    /**
     * 随机字符串长度
     * @deprecated 请使用RequestHelper中的对应常量
     */
    @Deprecated
    public static final int LENGTH_ENCODE_RANDOM_STRING = 0;
    /**
     * JSON的编码前缀
     * @deprecated 请使用RequestHelper中的对应常量
     */
    @Deprecated
    public static final String PREFIX_DECODE_JSON_STRING = "0";
    /**
     * 随机字符串长度
     * @deprecated 请使用RequestHelper中的对应常量
     */
    @Deprecated
    public static final int LENGTH_DECODE_RANDOM_STRING = 0;

    /**
	 * 简单编码
	 * @param json 待编码的json字符串(即原文)
     * @deprecated 请使用RequestHelper中的对应方法
	 * @return 编码后的json字符串(即密文)
	 */
    @Deprecated
	public static String simpleEncode(JSONObject json){
		return simpleEncode(json.toString());
	}
	/**
	 * 简单编码
	 * @param encodeStr 待编码的字符串(即原文)
     * @deprecated 请使用RequestHelper中的对应方法
	 * @return 编码后的字符串(即密文)
	 */
    @Deprecated
	public static String simpleEncode(String encodeStr){
		return simpleEncode(PREFIX_ENCODE_JSON_STRING, LENGTH_ENCODE_RANDOM_STRING, encodeStr);
	}
	/**
	 * 简单编码
	 * @param prefix 前缀
	 * @param randomStringLength 随机字符串长度
	 * @param json 待编码的json字符串(即原文)
     * @deprecated 请使用RequestHelper中的对应方法
	 * @return 编码后的json字符串(即密文)
	 */
    @Deprecated
	public static String simpleEncode(String prefix, int randomStringLength, JSONObject json){
		return simpleEncode(prefix, randomStringLength, json.toString());
	}
	/**
	 * 简单编码
	 * @param prefix 前缀
	 * @param randomStringLength 随机字符串长度
	 * @param encodeStr 待编码的字符串(即原文)
     * @deprecated 请使用RequestHelper中的对应方法
	 * @return 编码后的字符串(即密文)
	 */
    @Deprecated
	public static String simpleEncode(String prefix, int randomStringLength, String encodeStr){
		String result = null;
		if (Helper.isNotEmpty(encodeStr)){
			result = String.format(
					"%s%s%s"
					, prefix
					, (randomStringLength > 0) ? Helper.createRandomString(randomStringLength) : ""
					, Helper.encodeByBase64(encodeStr)
					);
		}
		return result;
	}
	/**
	 * 简单解码
	 * @param decodeStr 待解码的字符串(即密文)
     * @deprecated 请使用RequestHelper中的对应方法
	 * @return 解码后字符串(即原文)
	 */
    @Deprecated
	public static String simpleDecode(String decodeStr){
		return simpleDecode(PREFIX_ENCODE_JSON_STRING, LENGTH_ENCODE_RANDOM_STRING, decodeStr);
	}
	/**
	 * 简单解码
	 * @param decodeStr 待解码的json字符串(即密文)
     * @deprecated 请使用RequestHelper中的对应方法
	 * @return json
	 */
    @Deprecated
	public static JSONObject simpleJsonDecode(String decodeStr){
		return simpleJsonDecode(PREFIX_ENCODE_JSON_STRING, LENGTH_ENCODE_RANDOM_STRING, decodeStr);
	}
	/**
	 * 简单解码
	 * @param prefix 前缀
	 * @param randomStringLength 随机字符串长度
	 * @param decodeStr 待解码的字符串(即密文)
     * @deprecated 请使用RequestHelper中的对应方法
	 * @return 解码后字符串(即原文)
	 */
    @Deprecated
	public static String simpleDecode(String prefix, int randomStringLength, String decodeStr){
		String result = null;
		if (Helper.isNotEmpty(decodeStr) && randomStringLength >= 0 && decodeStr.length() > randomStringLength){
			if (Helper.isNotEmpty(prefix)){
				if (decodeStr.startsWith(prefix)){
					result = Helper.decodeToStringByBase64(
							decodeStr.substring(prefix.length() + randomStringLength));
				}
			}else{
				result = Helper.decodeToStringByBase64(
						decodeStr.substring(randomStringLength));
			}
		}
		return result;
	}
	/**
	 * 简单解码
	 * @param prefix 前缀
	 * @param randomStringLength 随机字符串长度
	 * @param jsonStr 待解码的json字符串(即密文)
     * @deprecated 请使用RequestHelper中的对应方法
	 * @return json(即原文)
	 */
    @Deprecated
	public static JSONObject simpleJsonDecode(String prefix, int randomStringLength, String jsonStr){
		JSONObject result = null;
		String decodeStr = simpleDecode(prefix, randomStringLength, jsonStr);
		if (Helper.isNotEmpty(decodeStr)){
			try{
				result = new JSONObject(decodeStr);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
    // endregion 废弃方法
}
