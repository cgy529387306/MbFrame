package com.android.mb.movie.retrofit.http.exception;

/**
 * Created by liukun on 16/3/10.
 */
public class ApiException extends RuntimeException {
    /**
     * 链接超时编码
     */
    public final static int CONNECT_TIMEOUT = 40001;

    /**
     * 读取超时编码
     */
    public final static int SOCKET_TIMEOUT = 40002;

    /**
     * 请求失败编码
     */
    public final static int REQUEST_FAIL = 40003;




    public ApiException(int resultCode,String message) {
        this(getApiExceptionMessage(resultCode,message));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code, String msg){
        String message = "";
        switch (code) {
            case CONNECT_TIMEOUT:
                message = "请求链接超时";
                break;
            case SOCKET_TIMEOUT:
                message = "数据读取超时";
                break;
            case REQUEST_FAIL:
                message = msg;
                break;
            default:
                message = "请求失败";

        }
        return message;
    }
}

