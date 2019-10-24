package com.tmall.error;

public interface CommentError {
    public  int getErrCode();
    public String getErrMsg();
    public CommentError setErrMsg(String errMsg);
}
