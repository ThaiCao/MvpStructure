package com.mvp.structure.data.bean.response.base;

public class ResponseError {
    public Throwable errorKind;
    public String errorMessage;
    public String status = "";

    public ResponseError(Throwable errorKind) {
        this.errorKind = errorKind;
    }

    public ResponseError(Throwable error, String msg){
        errorKind = error;
        errorMessage = msg;
    }

    public ResponseError(String _status, String msg){
        status = _status;
        errorMessage = msg;
    }

    public String getErrorMessage(){return  errorMessage;}
    public String getStatus(){return  status;}
    public Throwable getErrorKind(){return errorKind;}
}
