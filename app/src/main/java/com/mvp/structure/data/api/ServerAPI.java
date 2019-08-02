package com.mvp.structure.data.api;

import com.mvp.structure.data.bean.response.UserResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by thai.cao on 2019/07/30
 */

public interface ServerAPI {

    public static final String GET_CATEGORIES      = "categories";
    public static final String GET_USER      = "/search/users";

//    @GET(GET_CATEGORIES)
//    Call<List<Category>> getCategoriesData();

      @GET(GET_USER)
      Observable<UserResponse> getUsers(@Query("q") String query);
}
