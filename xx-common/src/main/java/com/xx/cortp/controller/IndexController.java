package com.xx.cortp.controller;

import com.alibaba.fastjson.JSONArray;
import com.xx.cortp.dto.ExportDto;
import com.xx.cortp.entity.*;
import com.xx.cortp.service.GeneratorService;
import com.xx.cortp.utils.CodeGenerateException;
import com.xx.cortp.utils.ExcelUtil;
import com.xx.cortp.utils.TableParseUtil;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页
 * @Author:Frank.zhang
 * @Date: 2021/2/19 11:02 上午
 */
@Controller
@Slf4j
@Api(tags={"首页"})
public class IndexController {
	@Autowired
	private GeneratorService gs;

	@GetMapping("/")
	@ApiOperation(value = "欢迎页")
	public String index() {
		return "index";
	}

	@GetMapping("/excel")
	@ApiOperation(value = "欢迎页")
	public String index2() {
		return "index2";
	}

	@ApiOperation("更新数据源")
	@PostMapping("/update")
	@ResponseBody
	public void update(@RequestBody DataSourceInfo info) {
		gs.reload(info);
	}

	@ApiOperation("表详情")
	@PostMapping("/getTableInfo")
	@ResponseBody
	public ReturnT<Map<String, String>> getTableInfo(@RequestBody ParamInfo paramInfo){
		Map<String, String> result = new HashMap<>(1);
		String info = gs.getTableInfo(paramInfo.getTableName());
		result.put("info",info);
		return new ReturnT<>(result);
	}

	@ApiOperation("所有表信息")
	@PostMapping("/getTables")
	@ResponseBody
	public ReturnT<Map<String, Object>> getTables(@RequestBody ParamInfo paramInfo){
		Map<String, Object> result = new HashMap<>(1);
		List<String> tables = gs.getTables(paramInfo.getTableName());
		result.put("tables", JSONArray.toJSONString(tables));
		return new ReturnT<>(result);
	}

	@ApiOperation("获取对象信息")
	@PostMapping("/gainTable")
	@ResponseBody
	public ReturnT<Map<String, Object>> gainTable(@RequestBody ParamInfo paramInfo){
		Map<String, Object> result = new HashMap<>(1);
		List<String> list = gs.gainTable(paramInfo);
		result.put("table", JSONArray.toJSONString(list));
		return new ReturnT<>(result);
	}

	@ApiOperation("导出")
	@PostMapping("/export")
	@ResponseBody
	public void gainTable(HttpServletResponse response, @RequestBody @ApiParam(value = " ",name=" ") ExportDto dto) throws IOException {
		ExcelUtil.export(response,dto.getDetailList(),dto.getTitles(),dto.getName());
	}

	@PostMapping("/genCode")
	@ResponseBody
	public ReturnT<Map<String, String>> codeGenerate(@RequestBody ParamInfo paramInfo ) {

		try {

			if (StringUtils.isBlank(paramInfo.getTableSql())) {
				return new ReturnT<>(ReturnT.FAIL_CODE, "表结构信息不可为空");
			}

			// parse table
			ClassInfo classInfo = null;
			switch (paramInfo.getDataType()){
				//parse json
				case "json":classInfo = TableParseUtil.processJsonToClassInfo(paramInfo);break;
				//parse sql  by regex
				case "sql-regex":classInfo = TableParseUtil.processTableToClassInfoByRegex(paramInfo);break;
				//default parse sql by java
				default : classInfo = TableParseUtil.processTableIntoClassInfo(paramInfo);break;
			}

			// process the param
			Map<String, Object> params = new HashMap<String, Object>(8);
			params.put("classInfo", classInfo);
			params.put("tableName", classInfo==null?System.currentTimeMillis():classInfo.getTableName());
			params.put("authorName", paramInfo.getAuthorName());
			params.put("packageName", paramInfo.getPackageName());
			params.put("returnUtil", paramInfo.getReturnUtil());
			params.put("swagger", paramInfo.isSwagger());

			log.info("generator table:"+(classInfo==null?"":classInfo.getTableName())
					+",field size:"+((classInfo==null||classInfo.getFieldList()==null)?"":classInfo.getFieldList().size()));

			// generate the code 需要加新的模板请在里面改
			Map<String, String> result = gs.getResultByParams(params);

			return new ReturnT<>(result);
		} catch (IOException | TemplateException e) {
			log.error(e.getMessage(), e);
			return new ReturnT<>(ReturnT.FAIL_CODE, e.getMessage());
		} catch (CodeGenerateException e) {
			log.error(e.getMessage(), e);
			return new ReturnT<>(ReturnT.FAIL_CODE, e.getMessage());
		}

	}

}
