package com.xx.cortp.utils;

/**
 * 通用结果枚举
 * @author wn
 *
 */
public enum CommonResultCode implements ResultCode {

    /** 成功 */
    SUCCESS("200", "SUCCESS"),

    /** rtp id 已存在错误 */
    RTPID_ERROR("-1", "RTPID_ERROR"),
    /** rfid 已存在错误 */
    BLUETOOTHID_ERROR("-2", "BLUETOOTHID_ERROR"),
    /** rfid 已存在错误 */
    MODULEID_ERROR("-3", "MODULEID_ERROR"),
    /** rfid 已存在错误 */
    RFID_ERROR("-4", "RFID_ERROR"),

    /** 系统错误 */
    SYSTEM_ERROR("1000", "SYSTEM_ERROR"),

    /** 请求参数不完整 */
    PARAM_MISS("1001", "PARAM_MISS"),
    
    /** 请求参数错误 */
    PARAM_ERROR("1002", "PARAM_ERROR"),

    /** 提货地址不能为多个 */
    PARAM_FROM_ADDR_LIMIT("1003", "PARAM_FROM_ADDR_LIMIT"),

    /** 到达地址不能为多个 */
    PARAM_TO_ADDR_LIMIT("1004", "PARAM_TO_ADDR_LIMIT"),

    /** 需求提货地址不能为多个 */
    PARAM_DEMAND_FROM_ADDR_LIMIT("1005", "PARAM_DEMAND_FROM_ADDR_LIMIT"),

    /** 需求到达地址不能为多个 */
    PARAM_DEMAND_TO_ADDR_LIMIT("1006", "PARAM_DEMAND_TO_ADDR_LIMIT"),

    /** 审批通过的合同不能删除 */
    CONTRACT_NOT_DEL("1007", "CONTRACT_NOT_DEL"),
    
    ;
	
	
    private String errorCode;

    private String statusCode;

    CommonResultCode(String statusCode, String errorCode) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getStatusCode() {
        return statusCode;
    }
}