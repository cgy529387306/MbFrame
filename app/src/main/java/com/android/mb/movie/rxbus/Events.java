package com.android.mb.movie.rxbus;

/**
 * @version v1.0
 * @Description
 * @Created by cgy on 2017/6/26
 *
 */

public class Events<T> {


    public int code;
    public T content;

    public static <O> Events<O> setContent(O t) {
        Events<O> events = new Events<>();
        events.content = t;
        return events;
    }

    public <T> T getContent() {
        return (T) content;
    }
}
