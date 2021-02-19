package com.xx.cortp.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 通用响应对象
 *
 * @author wn
 * @date 2018-04-04
 */
@ApiModel(description = "响应对象")
public class JsonResult<T> {
	private static final String SUCCESS_CODE = "200";
	private static final String SUCCESS_MESSAGE = "操作成功";

	@ApiModelProperty(value = "响应码", name = "code", required = true, example = "" + SUCCESS_CODE)
	private String code;

	@ApiModelProperty(value = "响应消息", name = "msg", required = true, example = SUCCESS_MESSAGE)
	private String msg;

	@ApiModelProperty(value = "响应数据", name = "data")
	private T data;

	public JsonResult() {
		this(SUCCESS_CODE, SUCCESS_MESSAGE);
	}

    public JsonResult(String code, String msg) {
		this(code, msg, null);
	}

    public JsonResult(T data) {
		this(SUCCESS_CODE, SUCCESS_MESSAGE, data);
	}

    public JsonResult(String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

    public JsonResult(String msg, T data) {
		this.msg = msg;
		this.data = data;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public static <T> JsonResult<T> success() {
		return new JsonResult<>();
	}

	public static <T> JsonResult<T> successWithData(T data) {
		return new JsonResult<>(data);
	}

	public static <T> JsonResult<T> successWithData(String message,T data) {
		return new JsonResult<>(message,data);
	}

	public static <T> JsonResult<T> failWithCodeAndMsg(String code, String msg) {
		return new JsonResult<>(code, msg, null);
	}

	public static <T> JsonResult<T> failWithCodeAndMsg(String code, String msg, T data) {
		return new JsonResult<>(code, msg, data);
	}

}
