package com.tmall.error;

public class BusinessException extends Exception implements CommentError{
    private CommentError commentError;
    //直接接收EmBuainessError的传参用于构造的业务异常
    public BusinessException(CommentError commentError){
        super();
        this.commentError=commentError;
    }
    //接受自定义errMsg的方法构造业务异常
    public BusinessException(CommentError commentError,String errMsg){
        super();
        this.commentError=commentError;
        this.commentError.setErrMsg(errMsg);
    }
    @Override
    public int getErrCode() {
        return this.commentError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commentError.getErrMsg();
    }

    @Override
    public CommentError setErrMsg(String errMsg) {
        this.commentError.setErrMsg(errMsg);
        return this;
    }
}
