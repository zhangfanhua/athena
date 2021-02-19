package com.xx.cortp.utils;

/**
 * 结果码接口
 * <p>
 * 结果码接口定义了错误码以及状态码
 * 错误码定义了java系统内可识别的符号，用于内部java应用系统之间传递信息，错误码一般需要在配置文件中配置文案</br>
 * 状态码主要用于java系统和php系统(或者java系统和页面json请求之间的交互)，或者java系统和外部系统之间的信息交互
 * </p>
 * @version $Id: ResultCode.java
 */
public interface ResultCode {

    /**
     * 获取错误码
     * <p>不能返回<code>NULL</code></p>
     *
     * @return 错误码
     */
    public String getErrorCode();

    /**
     * 获取状态码
     * <p>状态码应该是经过约定的</p>
     *
     * @return 状态码
     */
    public String getStatusCode();
}
