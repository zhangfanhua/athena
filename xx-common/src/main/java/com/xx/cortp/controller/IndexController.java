package com.xx.cortp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页
 * @author wn
 *
 */
@RestController
@Api(tags={"首页"})
public class IndexController {

	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/")
	@ApiOperation(value = "欢迎页")
	public String index() {
		return "鸿润基础定时任务管理";
	}


}
