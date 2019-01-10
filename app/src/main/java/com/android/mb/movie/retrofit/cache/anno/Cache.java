package com.android.mb.movie.retrofit.cache.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;


/**
 *
 * <pre>
 *     author: cgy
 *     time  : 2017/9/5
 *     desc  : 缓存Annotation
 * </pre>
 *
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    TimeUnit timeUnit()  default TimeUnit.NANOSECONDS;
    long time() default -1;
}
