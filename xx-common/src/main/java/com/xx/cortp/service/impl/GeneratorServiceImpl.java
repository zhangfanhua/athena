package com.xx.cortp.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xx.cortp.config.DataSourceProvider;
import com.xx.cortp.config.MyHikariDataSource;
import com.xx.cortp.entity.DataSourceInfo;
import com.xx.cortp.entity.ParamInfo;
import com.xx.cortp.repository.platform.GeneratorMapper;
import com.xx.cortp.service.GeneratorService;
import com.xx.cortp.utils.FreemarkerTool;
import com.zaxxer.hikari.HikariDataSource;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Frank.Zhang
 * @date: 2021/2/19 11:24
 * @description:
 */
@Slf4j
@Service
public class GeneratorServiceImpl implements GeneratorService {

    @Autowired
    private FreemarkerTool freemarkerTool;
    @Autowired
    private GeneratorMapper gm;
    @Value("${db.type}")
    private String dbType;

    @Override
    public void reload(DataSourceInfo info) {
        HikariDataSource dataSource = DataSourceProvider.create(info);
        MyHikariDataSource hikariCPDataSource = new MyHikariDataSource();
        hikariCPDataSource.updateDataSourceMap("1", dataSource);
    }

    @Override
    public List<String> getTables(String tableName) {
        if(dbType.equals("mysql")){
            return gm.findMysqlTables(tableName);
        }else{
            return gm.findTables(tableName);
        }

    }


    @Override
    public List<String> gainTable(ParamInfo paramInfo) {
        RestTemplate restTemplate = new RestTemplate();
        List<String> list = new ArrayList<>();
        ResponseEntity<String> ret = null;
        if(paramInfo.getDataType().equals("post")){
            String sql = paramInfo.getTableSql();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<String> request = new HttpEntity<>(sql, headers);
            ret = restTemplate.postForEntity(paramInfo.getCustomUrl(),request,String.class);
        }else{
            ret = restTemplate.getForEntity(paramInfo.getCustomUrl(),String.class);
        }
        if(null != ret){
            HashMap body = JSON.parseObject(ret.getBody(), HashMap.class);
            if(null != body.get("data")){
                HashMap data = null;
                try {
                    data = JSON.parseObject(body.get("data").toString(), HashMap.class);
                    if(null != data.get("list")){
                        data = JSON.parseArray(data.get("list").toString(),HashMap.class).get(0);
                    }
                }catch (Exception e){
                    data = JSON.parseArray(body.get("data").toString(),HashMap.class).get(0);
                }
                data.forEach((k,v)->{
                    list.add(k.toString());
                });
            }
        }
        return list;
    }

    @Override
    public String getTableInfo(String tableName) {
        //表名称 例如：cortp.product 得到 product
        String tName = null;
        if(!dbType.equals("mysql")){
            tName = tableName.split("\\.")[1];
        }
        //表描述
        StringBuilder tDesc = new StringBuilder();
        //表列
        StringBuilder tColumn = new StringBuilder();
        //表列描述
        StringBuilder tColumnDesc = new StringBuilder();
        //得到所有列
        List<Map<String,String>> columns = null;
        if(dbType.equals("mysql")){
            columns = gm.findMysqlTableColumn(tableName);
        } else {
            columns = gm.findTableColumn(tableName);
        }
        for (Map<String,String> column : columns) {
            String tableDesc = column.get("tableDesc") == null?"":column.get("tableDesc");
            if(tDesc.toString().isEmpty() && !tableDesc.isEmpty()){
                tDesc.append("COMMENT ON TABLE ").append(tableName).append(" IS '").append(tableDesc).append("' ;").append("\n");
            }
            tColumn.append(column.get("columnName")).append(" ").append(column.get("type")).append(" ").append(",").append("\n");
            String desc= column.get("desc") == null?"":column.get("desc");
            if(!desc.isEmpty()){
                tColumnDesc.append("COMMENT ON COLUMN ").append(tableName).append(".").append(column.get("columnName")).append(" IS '").append(desc).append("';").append("\n");
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(tableName).append(" (").append("\n")
        .append(tColumn.toString().substring(0,tColumn.toString().length()-3))
        .append(");").append("\n")
        .append(tDesc.toString())
        .append(tColumnDesc.toString());
        return sb.toString();
    }

    @Override
    public Map<String, String> getResultByParams(Map<String, Object> params) throws IOException, TemplateException {
        // result
        Map<String, String> result = new HashMap<String, String>(32);
        result.put("tableName",params.get("tableName")+"");
        //UI
        result.put("swagger-ui", freemarkerTool.processString("code-generator/ui/swagger-ui.ftl", params));
        result.put("element-ui", freemarkerTool.processString("code-generator/ui/element-ui.ftl", params));
        result.put("bootstrap-ui", freemarkerTool.processString("code-generator/ui/bootstrap-ui.ftl", params));
        //mybatis old
        result.put("controller", freemarkerTool.processString("code-generator/mybatis/controller.ftl", params));
        result.put("service", freemarkerTool.processString("code-generator/mybatis/service.ftl", params));
        result.put("service_impl", freemarkerTool.processString("code-generator/mybatis/service_impl.ftl", params));
        result.put("mapper", freemarkerTool.processString("code-generator/mybatis/mapper.ftl", params));
        result.put("mybatis", freemarkerTool.processString("code-generator/mybatis/mybatis.ftl", params));
        result.put("model", freemarkerTool.processString("code-generator/mybatis/model.ftl", params));
        //jpa

        result.put("entity", freemarkerTool.processString("code-generator/jpa/entity.ftl", params));
        result.put("repository", freemarkerTool.processString("code-generator/jpa/repository.ftl", params));
        result.put("jpacontroller", freemarkerTool.processString("code-generator/jpa/jpacontroller.ftl", params));
        //jdbc template
        result.put("jtdao", freemarkerTool.processString("code-generator/jdbc-template/jtdao.ftl", params));
        result.put("jtdaoimpl", freemarkerTool.processString("code-generator/jdbc-template/jtdaoimpl.ftl", params));
        //beetsql
        result.put("beetlmd", freemarkerTool.processString("code-generator/beetlsql/beetlmd.ftl", params));
        result.put("beetlentity", freemarkerTool.processString("code-generator/beetlsql/beetlentity.ftl", params));
        result.put("beetlentitydto", freemarkerTool.processString("code-generator/beetlsql/beetlentitydto.ftl", params));
        result.put("beetlcontroller", freemarkerTool.processString("code-generator/beetlsql/beetlcontroller.ftl", params));
        //mybatis plus
        result.put("pluscontroller", freemarkerTool.processString("code-generator/mybatis-plus/pluscontroller.ftl", params));
        result.put("plusmapper", freemarkerTool.processString("code-generator/mybatis-plus/plusmapper.ftl", params));
        //util
        result.put("util", freemarkerTool.processString("code-generator/util/util.ftl", params));
        result.put("json", freemarkerTool.processString("code-generator/util/json.ftl", params));
        result.put("xml", freemarkerTool.processString("code-generator/util/xml.ftl", params));
        //sql generate
        result.put("select", freemarkerTool.processString("code-generator/sql/select.ftl", params));
        result.put("insert", freemarkerTool.processString("code-generator/sql/insert.ftl", params));
        result.put("update", freemarkerTool.processString("code-generator/sql/update.ftl", params));
        result.put("delete", freemarkerTool.processString("code-generator/sql/delete.ftl", params));
        result.put("date-swagger", freemarkerTool.processString("code-generator/mybatis/date-swagger.ftl", params));

        return result;
    }


}
