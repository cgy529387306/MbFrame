package com.android.mb.movie.retrofit.http;

import com.android.mb.movie.retrofit.http.util.DotNetDateDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @Description
 * @created by cgy on 2017/6/19
 * @version v1.0
 *
 */
public class ResponseConvertFactory extends Converter.Factory{

    /**
     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static ResponseConvertFactory create() {
        return create(new Gson());
    }

    public static ResponseConvertFactory createDotNet() {
        Gson gson = new GsonBuilder()
                //由于gson没有反序列化Date的功能，此处自己构造一个DateTime的反序列化类，将其注册到GsonBuilder中
                .registerTypeAdapter(Date.class,new DotNetDateDeserializer())
                .create();
        return create(gson);
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static ResponseConvertFactory create(Gson gson) {
        return new ResponseConvertFactory(gson);
    }

    private final Gson gson;

    private ResponseConvertFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        return new GsonResponseBodyConverter<>(gson,type);
    }

}
