package com.xx.cortp.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Frank.Zhang
 * @date: 2021/2/19 10:34
 * @description:
 */
@Data
public class ReturnT<T> implements Serializable {

    private static final long serialVersionUID = -2124677911603406428L;

    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;
    public static final ReturnT<String> SUCCESS = new ReturnT<>(null);
    public static final ReturnT<String> FAIL = new ReturnT<>(FAIL_CODE, null);


    private int code;
    private String msg;
    private T data;

    public ReturnT(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    public ReturnT(T data) {
        this.code = SUCCESS_CODE;
        this.data = data;
    }
}
