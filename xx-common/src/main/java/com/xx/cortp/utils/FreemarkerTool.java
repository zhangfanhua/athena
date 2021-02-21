package com.xx.cortp.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author Frank.Zhang
 * @date: 2021/2/19 10:42
 * @description:
 */
@Component
public class FreemarkerTool {

    @Autowired
    private Configuration configuration;

    /**
     * process Template Into String
     *
     * @param template
     * @param model
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public String processTemplateIntoString(Template template, Object model)
            throws IOException, TemplateException {

        StringWriter result = new StringWriter();
        template.process(model, result);
        return result.toString();
    }

    /**
     * process String
     *
     * @param templateName
     * @param params
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public String processString(String templateName, Map<String, Object> params)
            throws IOException, TemplateException {

        Template template = configuration.getTemplate(templateName);
        String htmlText = processTemplateIntoString(template, params);
        return htmlText;
    }
}
