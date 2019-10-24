package com.tmall.error;

public enum EmBusinessError implements CommentError{
    //通用错误类型10000
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKOWN_ERROR(10002,"未知错误"),
    //20000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001,"用户不存在"),
    USER_LOGIN_FAIL(20002,"用户手机号或密码不正确"),
    USER_NOT_LOGIN(20003,"用户还未登陆"),
    //30000开头为交易信息错误定义
    STOCK_NOT_ENOUGH(30000,"库存不足")
    ;
    private EmBusinessError(int errCode,String errMsg){
        this.errCode=errCode;
        this.errMsg=errMsg;
    }
    private  int errCode;
    private String errMsg;

    @Override
    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getErrMsg() {
        return errMsg;
    }

    @Override
    public CommentError setErrMsg(String errMsg) {
        this.errMsg=errMsg;
        return this;
    }
}
