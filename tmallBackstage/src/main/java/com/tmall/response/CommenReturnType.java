package com.tmall.response;

public class CommenReturnType {
    //表明对应请求的返回处理结果"succes"或"fail"
    private String status;
    //若status=success,则data内返回前端需要的son数据
    //若status=success，则data内使用通用的错误码格式
    private Object data;
    //定义一个通用的创建方法
    public static CommenReturnType creat( Object result){
        return CommenReturnType.creat(result,"success");
    }
    public static CommenReturnType creat(Object result,String status){
        CommenReturnType type=new CommenReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
