package com.mvp.structure.data.api.converter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by thai.cao on 2019/07/30
 * @param <T>
 */
class NullOnEmptyResponseBodyConverter<T> implements Converter<ResponseBody, T> {

   private Factory factory;
   private Type type;
   private Annotation[] annotations;
   private Retrofit retrofit;

   NullOnEmptyResponseBodyConverter(@Nullable Converter.Factory factory, Type type, Annotation[] annotations, Retrofit retrofit) {
       this.factory = factory;
       this.type = type;
       this.annotations = annotations;
       this.retrofit = retrofit;
   }

   @Override
   public T convert(@NonNull ResponseBody value) throws IOException {
       if (value.contentLength() == 0) {
           return null;
       }
       return retrofit
               .<T>nextResponseBodyConverter(factory, type, annotations)
               .convert(value);
   }
}
