package com.mvp.structure.data.api.converter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by thai.cao on 2019/07/30
 * <p> {@linkplain Converter.Factory converter} </p> dedicated to handling empty JSON strings or null returns
   * <b>Note the first {@linkplain Converter.Factory converter} that needs to be added to Retrofit when it is used.</b>
   * <br/>
   * If an API request does not need to return data,
   * It is likely that our server will not return data (returning an empty response body).
   * The empty string is not a valid JSON, so the Square implementation of <a href="https://github.com/square/retrofit/tree/master/retrofit-converters/gson">GsonResponseBodyConverter</a> will not Recognize,
   * Directly throw a JSON parsing error. For more discussion on this issue, take a look at Retrofit's <a href="https://github.com/square/retrofit/issues/1554">issue:#1554 Handle Empty Body<a/>
 */

public class NullOnEmptyConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(final Type type, final Annotation[] annotations, final Retrofit retrofit) {
        return new NullOnEmptyResponseBodyConverter<>(this, type, annotations, retrofit);
    }
}