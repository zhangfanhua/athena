package com.xx.cortp.config;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证过滤器
 */
@WebFilter(filterName = "AuthenticationFilter", urlPatterns = "/*")
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String requestURI = request.getRequestURI();
        if(requestURI.contains("/doc.html")||requestURI.contains("swagger")
                ||requestURI.contains("/v2/")||requestURI.contains("/webjars/")){
            filterChain.doFilter(request, servletResponse);
        }else{
            String authorization = request.getHeader("Authorization");
            if(StringUtils.isBlank(authorization)){
                response.setStatus(498);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().print("{\"code\":\"498\",\"data\":\"\",\"msg\":\"认证失败\"}");
            }else{
//                RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//                ra.setAttribute("user",new SysUser(authorization), WebRequest.SCOPE_REQUEST);
//                filterChain.doFilter(request, servletResponse);
            }

        }
    }

}
