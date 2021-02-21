package com.xx.cortp.config;


import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * 配置
 * @author wn
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory factory){
		return new RestTemplate(factory);
	}

	@Bean
	public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		//单位为ms
		factory.setReadTimeout(5000);
		//单位为ms
		factory.setConnectTimeout(5000);
		return factory;
	}


	@Override
	public void addCorsMappings(CorsRegistry registry) {
 
		registry.addMapping("/**")
				.allowCredentials(true)
				.allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH")
				.allowedHeaders("*")
				.allowedOrigins("*")
				.allowedMethods("*");
 
	}

	@Bean
    public Docket api(){
		ParameterBuilder ticketPar = new ParameterBuilder();
		List<Parameter> pars = new ArrayList<Parameter>();
		ticketPar.name("Authorization").description("认证token")
				.modelRef(new ModelRef("string")).parameterType("header")
				//header中的ticket参数非必填，传空也可以
				.required(false).build();
		//根据每个方法名也知道当前方法在设置什么参数
		pars.add(ticketPar.build());


		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xx"))
                .paths(PathSelectors.any())
                .build()
				.globalOperationParameters(pars);
				//.genericModelSubstitutes(JsonResult.class)
	}


	/**
	 * 功能描述: 静态资源配置
	 * @Author:Frank.zhang
	 * @Date: 2021/2/19 10:08 上午
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//映射上传文件路径
		registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/","classpath:/static/js","classpath:/static/img");
		registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}


	/**
	 * 项目信息
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("业务系统接口文档")
				.version("1.0")
				.build();
	}

	/**
	 * 功能描述: SpringMvc MessageConverter
	 * @Author:Frank.zhang
	 * @Date: 2021/2/19 10:12 上午
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		//FastJsonHttpMessageConverter
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();

		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		fastConverter.setSupportedMediaTypes(fastMediaTypes);

		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setCharset(StandardCharsets.UTF_8);
		fastConverter.setFastJsonConfig(fastJsonConfig);

		//StringHttpMessageConverter
		StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
		stringConverter.setDefaultCharset(StandardCharsets.UTF_8);
		stringConverter.setSupportedMediaTypes(fastMediaTypes);
		converters.add(stringConverter);
		converters.add(fastConverter);
	}

}
