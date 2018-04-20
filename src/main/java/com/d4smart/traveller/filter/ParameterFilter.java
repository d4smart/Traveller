package com.d4smart.traveller.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求参数过滤器
 * Created by d4smart on 2018/4/19 16:54
 */
public class ParameterFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ParameterRequestWrapper parameterRequestWrapper = new ParameterRequestWrapper(request);
        filterChain.doFilter(parameterRequestWrapper, response);
    }
}
