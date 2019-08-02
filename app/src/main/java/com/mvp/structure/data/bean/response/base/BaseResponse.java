package com.mvp.structure.data.bean.response.base;

/**
 * Created by thai.cao on 2019/07/30
 */
public class BaseResponse {
    public static String STATUS_OK = "200";
    private String status;
    private String error;

    public BaseResponse(){

    }

    public Boolean isApiSuccess(){
        if(status!=null && STATUS_OK.equals(status)){
            return true;
        }
        return false;
    }

    public String getStatus(){return  status;}
    public String getError(){return error;}
}
