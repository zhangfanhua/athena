package com.xx.cortp.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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
		factory.setReadTimeout(5000);//单位为ms
		factory.setConnectTimeout(5000);//单位为ms
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
				.required(false).build(); //header中的ticket参数非必填，传空也可以
		pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数


		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.horen"))
                .paths(PathSelectors.any())
                .build()
				.globalOperationParameters(pars);
				//.genericModelSubstitutes(JsonResult.class) //4
	}


	//静态资源配置
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

}
