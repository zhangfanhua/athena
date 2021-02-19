package com.horen.gateway.filter;

import com.horen.gateway.util.Constant;
import com.horen.gateway.util.JsonUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Component
@Slf4j
public class PreAuthorizationFilter extends ZuulFilter {

    @Value("${secretKey : 698d8515-9910-4f42-a58e-94c920902a4a}")
    private String signingKey;

    @Value("${enableAuthFilter: true}")
    private boolean enable;

    @SuppressWarnings("unused")
    @Autowired
    private AntPathMatcher antPathMatcher;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return enable;
    }

    @Override
    public Object run() {
        log.info("zuul过滤器。。。");
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //antPathMatcher.match(rejectPath, uri)比较uri对应的权限规则
        String uri = request.getRequestURI();
        String token = request.getHeader(Constant.AUTHORIZATION);
        try {
            //1、获取请求body
            Map<String, Object> requestBody = new HashMap<>();
            getRequestBody(ctx, requestBody);

            //2、封装请求BODY
            byte[] requestEntityBytes = JsonUtil.toJSONString(requestBody.getOrDefault("data", new HashMap<>())).getBytes("UTF-8");
            ctx.setRequest(new HttpServletRequestWrapper(ctx.getRequest()) {
                @Override
                public ServletInputStream getInputStream() throws IOException {
                    return new ServletInputStreamWrapper(requestEntityBytes);
                }

                @Override
                public int getContentLength() {
                    return requestEntityBytes.length;
                }

                @Override
                public long getContentLengthLong() {
                    return requestEntityBytes.length;
                }
            });
        } catch (Exception e) {
            reject(500, "网关处理请求异常");
        }
        return null;
    }

    public void getRequestBody(RequestContext ctx, Map<String, Object> requestBody) {
        try {
            String charset = ctx.getRequest().getCharacterEncoding();
            InputStream in = (InputStream) ctx.get("requestEntity");
            if (null == in) {
                in = ctx.getRequest().getInputStream();
            }
            String requestEntityStr = StreamUtils.copyToString(in, Charset.forName(charset));
            requestEntityStr = URLDecoder.decode(requestEntityStr, charset);
            HashMap<String, Object> resoult = JsonUtil.parseObject(requestEntityStr, HashMap.class);
            requestBody.putAll(resoult);
            log.info("get request body :{}", requestBody);
        } catch (IOException io) {
            log.error("get request body error! message:{}", io.getMessage());
            reject(500, "获取请求boby异常");
        }
    }

    private void reject(Integer code, String msg) {
        PostAuthorizationFilter.ERROR_MSG = msg;
        PostAuthorizationFilter.ERROR_CODE = code;
        PostAuthorizationFilter.ERROR_STATUS = 0;
    }

}
