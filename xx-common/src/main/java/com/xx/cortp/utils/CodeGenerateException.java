package com.xx.cortp.utils;

/**
 * @author Frank.Zhang
 * @date: 2021/2/19 10:41
 * @description:
 */
public class CodeGenerateException extends RuntimeException{
    public CodeGenerateException() {
        super();
    }

    public CodeGenerateException(String msg) {
        super(msg);
    }

    public CodeGenerateException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CodeGenerateException(Throwable cause) {
        super(cause);
    }

    public CodeGenerateException(String message, Throwable cause,
                                 boolean enableSuppression,
                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
