package com.android.mb.movie.retrofit.http.interceptor;

import android.util.Log;

import com.android.mb.movie.retrofit.http.util.JsonUtil;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * <pre>
 *     author: cgy
 *     time  : 2017/12/26
 *     desc  :
 * </pre>
 */

public class LogInterceptor{

    public static HttpLoggingInterceptor getLogInterceptor(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

            private StringBuilder mMessage = new StringBuilder();

            @Override
            public void log(String message) {

                // 请求或者响应开始
                if (message.startsWith("--> POST")) {
                    mMessage.setLength(0);
                }

                // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
                if ((message.startsWith("{") && message.endsWith("}"))
                        || (message.startsWith("[") && message.endsWith("]"))) {
                    message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message));
                }

                mMessage.append(message.concat("\n"));
                // 响应结束，打印整条日志
                if (message.startsWith("<-- END HTTP")) {
                    Log.d("RetrofitHttpClient",mMessage.toString());
                }
            }
        });

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }


}
