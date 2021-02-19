package com.horen.gateway.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;


/**
 * @author horen
 */
@Slf4j
public class JsonUtil {

	private static final ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.registerModule(new ParameterNamesModule());
		mapper.registerModule(new Jdk8Module());
		mapper.registerModule(new JavaTimeModule());
	}

	public static ObjectMapper getMapper() {
		return mapper;
	}

	public static JavaType getJavaType(Type type) {
		return mapper.getTypeFactory().constructType(type);
	}

	/**
	 * Java对象转JSON字符串
	 *
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			log.error("Object to json stirng error", e);
			return null;
		}
	}

	public static String toJSONString(Object object, boolean pretty) {
		if (!pretty) {
			return toJSONString(object);
		}
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (Exception e) {
			log.error("Object to json stirng error", e);
			return null;
		}
	}

	/**
	 * JSON字符串转Java对象
	 *
	 * @param json
	 * @param type
	 * @param <T>
	 * @return
	 */
	public static <T> T parseObject(String json, Class<T> type) {
		try {
			return mapper.readValue(json, type);
		} catch (Exception e) {
			log.error("Json parse to object error", e);
		}
		return null;
	}
	
	public static <T> T parseObject(byte[] data, Class<T> type) {
		try {
			return mapper.readValue(data, type);
		} catch (Exception e) {
			log.error("Json parse to object error", e);
		}
		return null;
	}

	/**
	 * JSON转Object
	 */
	public static <T> T parseObject(String json, Type type) {
		try {
			return mapper.readValue(json, getJavaType(type));
		} catch (Exception e) {
			log.error("Json parse to object error", e);
		}
		return null;
	}

	public static <T> T parseObject(String json, TypeReference<T> typeReference) {
		try {
			return mapper.readValue(json, typeReference);
		} catch (Exception e) {
			log.error("Json parse to object error", e);
			return null;
		}
	}
}