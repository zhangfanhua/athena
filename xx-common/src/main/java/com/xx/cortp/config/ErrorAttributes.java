package com.xx.cortp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 实现自定义统一异常处理
 * @author wn
 *
 */
@Component
public class ErrorAttributes extends DefaultErrorAttributes {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String ERROR_ATTRIBUTE = DefaultErrorAttributes.class.getName() + ".ERROR";

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
		Map<String, Object> errorAttributes = new LinkedHashMap();
		errorAttributes.put("timestamp", new Date());
		this.addStatus(errorAttributes, webRequest);
		this.addErrorDetails(errorAttributes, webRequest, includeStackTrace);
		this.addPath(errorAttributes, webRequest);
//		return errorAttributes;
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		resultMap.put("code", "500");
		resultMap.put("msg", "系统繁忙，请稍后再试。");
		resultMap.put("data", errorAttributes);
		return resultMap;
	}
	private void addStatus(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
		Integer status = (Integer)this.getAttribute(requestAttributes, "javax.servlet.error.status_code");
		if (status == null) {
			errorAttributes.put("status", 999);
			errorAttributes.put("error", "None");
		} else {
			errorAttributes.put("status", status);

			try {
				errorAttributes.put("error", HttpStatus.valueOf(status).getReasonPhrase());
			} catch (Exception var5) {
				errorAttributes.put("error", "Http Status " + status);
			}

		}
	}

	private void addErrorDetails(Map<String, Object> errorAttributes, WebRequest webRequest, boolean includeStackTrace) {
		Throwable error = this.getError(webRequest);
		if (error != null) {
			while(true) {
				if (!(error instanceof ServletException) || error.getCause() == null) {
					if (true) {
						errorAttributes.put("exception", error.getClass().getName());
					}

					this.addErrorMessage(errorAttributes, error);
					if (includeStackTrace) {
						this.addStackTrace(errorAttributes, error);
					}
					break;
				}

				error = ((ServletException)error).getCause();
			}
		}

		Object message = this.getAttribute(webRequest, "javax.servlet.error.message");
		if ((!StringUtils.isEmpty(message) || errorAttributes.get("message") == null) && !(error instanceof BindingResult)) {
			errorAttributes.put("message", StringUtils.isEmpty(message) ? "No message available" : message);
		}

	}

	private void addErrorMessage(Map<String, Object> errorAttributes, Throwable error) {
		BindingResult result = this.extractBindingResult(error);
		if (result == null) {
			errorAttributes.put("message", error.getMessage());
		} else {
			if (result.hasErrors()) {
				errorAttributes.put("errors", result.getAllErrors());
				errorAttributes.put("message", "Validation failed for object='" + result.getObjectName() + "'. Error count: " + result.getErrorCount());
			} else {
				errorAttributes.put("message", "No errors");
			}

		}
	}

	private BindingResult extractBindingResult(Throwable error) {
		if (error instanceof BindingResult) {
			return (BindingResult)error;
		} else {
			return error instanceof MethodArgumentNotValidException ? ((MethodArgumentNotValidException)error).getBindingResult() : null;
		}
	}

	private void addStackTrace(Map<String, Object> errorAttributes, Throwable error) {
		StringWriter stackTrace = new StringWriter();
		error.printStackTrace(new PrintWriter(stackTrace));
		stackTrace.flush();
		errorAttributes.put("trace", stackTrace.toString());
	}

	private void addPath(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
		String path = (String)this.getAttribute(requestAttributes, "javax.servlet.error.request_uri");
		if (path != null) {
			errorAttributes.put("path", path);
		}

	}

	public Throwable getError(WebRequest webRequest) {
		Throwable exception = (Throwable)this.getAttribute(webRequest, ERROR_ATTRIBUTE);
		if (exception == null) {
			exception = (Throwable)this.getAttribute(webRequest, "javax.servlet.error.exception");
		}

		return exception;
	}

	private Object getAttribute(RequestAttributes requestAttributes, String name) {
		return requestAttributes.getAttribute(name, 0);
	}


}
