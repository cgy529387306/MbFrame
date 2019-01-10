package com.android.mb.movie.retrofit.http.entity;

/**
 * Created by liukun on 16/3/5.
 */
public class HttpResult<T> {

    /**
     * 返回成功与否标识（1：成功；0：失败）
     */
    private int code;
    /**
     * 当前data里面的数据数量
     */
    private int totalRecord;
    /**
     * 总的页码数
     */
    private int totalPage;
    /**
     * 当前页码
     */
    private int pageIndex;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 失败返回错误信息
     */
    private String errorMessage;
    private String systemError;

    //用来模仿Data
    private T data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getSystemError() {
        return systemError;
    }

    public void setSystemError(String systemError) {
        this.systemError = systemError;
    }

    public T getData() {
        return data;
    }
    public void setData(T subjects) {
        this.data = data;
    }



}
