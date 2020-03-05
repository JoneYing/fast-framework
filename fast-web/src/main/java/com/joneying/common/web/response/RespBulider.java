package com.joneying.common.web.response;

/**
 *@Title
 *@Description Resp响应对象Bulider类
 *@author Yingjianghua
 *@date 8:16 PM 2019/4/18
 *@param
 *@return
 */
public class RespBulider {

    public static<T> Resp<T> success(T data) {
        return new Resp<T>().success(data);
    }

    public static<T> Resp<T> success() {

        return new Resp<T>().success();
    }
    /**
     *参数不正确
     */
    public static<T> Resp<T> badParameter() {
        return new Resp<T>().failure(MessageConstant.FAILURE);
    }

    /**
     *会话过期
     */
    public static<T> Resp<T> sessionExpire() {
        return new Resp<T>().failure(MessageConstant.FAILURE);
    }



    public static<T> Resp<T> failure() {
        return new Resp<T>().failure();
    }

    public static<T> Resp<T> failure(String code) {
        return new Resp<T>().failure(code);
    }

    public static<T> Resp<T> failure(String code, T data) {
        return new Resp<T>().failure(code, data);
    }

    public static<T> Resp<T> failure(String code, String message) {
        return new Resp<T>().failure(code, message);
    }

    public static<T> Resp<T> error() {
        return new Resp<T>().error();
    }

    public static<T> Resp<T> error(String code) {
        return new Resp<T>().error(code);
    }

    public static<T> Resp<T> error(String code, T data) {
        return new Resp<T>().error(code, data);
    }

}
