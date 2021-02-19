package com.xx.cortp.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class JsonResultHelper<T> {
    /** 信息帮助类 */
    @Autowired
    private MessageHelper  messageHelper;

    /**
     * 根据结果码创建失败的Json结果对象
     * <p>不会校验结果码是否为<code>NULL</code>，应用需保证</p>
     *
     * @param resultCode 结果码
     * @return Json结果对象
     * @throws org.springframework.context.NoSuchMessageException 文案配置不存在抛出
     */
    public <T>JsonResult<T> buildFailJsonResult(ResultCode resultCode) {
        return buildFailJsonResult(resultCode.getStatusCode(), resultCode.getErrorCode());
    }

    public <T>JsonResult<T> buildFailJsonResult(ResultCode resultCode,Object[] args) {
        return buildFailJsonResult(resultCode.getStatusCode(), resultCode.getErrorCode(),args);
    }
    
    public <T>JsonResult<T> buildFailJsonResult(ResultCode resultCode,T data) {
        return buildFailJsonResult(resultCode.getStatusCode(), resultCode.getErrorCode(),data);
    }

    /**
     * 根据状态码和错误码创建失败的Json结果对象
     * <p>错误码对应的文案需要应用确保存在</p>
     *
     * @param statusCode 状态码
     * @param errorCode 错误码
     * @return Json结果对象
     * @throws org.springframework.context.NoSuchMessageException 文案配置不存在抛出
     */
    public <T>JsonResult<T> buildFailJsonResult(String statusCode, String errorCode) {
        String message = messageHelper.getMessage(errorCode);
        return JsonResult.failWithCodeAndMsg(statusCode,message);
    }

    public <T>JsonResult<T> buildFailJsonResult(String statusCode, String errorCode,Object[] args) {
        String message = messageHelper.getMessage(errorCode,args);
        return JsonResult.failWithCodeAndMsg(statusCode,message);
    }
    
    
    public <T>JsonResult<T> buildFailJsonResult(String statusCode, String errorCode,T data) {
        String message = messageHelper.getMessage(errorCode);
        return JsonResult.failWithCodeAndMsg(statusCode,message,data);
    }

    /**
     * 创建成功的Json结果对象
     *
     * @param data 结果数据，可以为<code>NULL</code>
     * @return Json结果对象
     */
    public <T>JsonResult<T> buildSuccessJsonResult(T data) {
        return JsonResult.successWithData(data);
    }

    /**
     * 创建成功的Json结果对象
     *
     * @param data 结果数据，可以为<code>NULL</code>
     * @return Json结果对象
     */
    public <T>JsonResult<T> buildSuccessJsonResult(T data,String message) {
        return JsonResult.successWithData(message,data);
    }
}
