package com.horen.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

/**
 * @Desc 在请求被处理之后，会进入该过滤器
 * @Author AFZ
 */
@Component
@Slf4j
public class PostAuthorizationFilter extends ZuulFilter {

    public static String ERROR_MSG = "系统异常，请联系管理员";

    public static Integer ERROR_CODE = 500;

    public static Integer ERROR_STATUS = 1;

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String result = "";
        BufferedReader in = null;
        InputStream stream = ctx.getResponseDataStream();
        try {
            in = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            log.info("result:{}", result);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private void reject(RequestContext ctx, Map<String, Object> data) {
        PostAuthorizationFilter.ERROR_STATUS = 1;
        ctx.set("logic-is-success", true);
        ctx.setResponseStatusCode(Response.SC_OK);
        // 输出最终结果
        ctx.setResponseBody(data.toString());
        ctx.getResponse().setContentType("application/json;charset=UTF-8");
        ctx.setSendZuulResponse(false);
    }


    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public String filterType() {
        return POST_TYPE;
    }

}