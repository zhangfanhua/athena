package com.xx.cortp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 启动类
 * @author wn
 *
 */
@EnableScheduling
@SpringBootApplication
@EnableSwagger2
public class CortpApplication {

	public static void main(String[] args) {
		SpringApplication.run(CortpApplication.class, args);
	}
}
