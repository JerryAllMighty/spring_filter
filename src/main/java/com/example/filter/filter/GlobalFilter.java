package com.example.filter.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;


@Slf4j
@Component
@WebFilter(urlPatterns = "/api/user/*")
public class GlobalFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //전처리
        ContentCachingRequestWrapper httpServletRequest = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        ContentCachingResponseWrapper httpServletResponse = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        String url = httpServletRequest.getRequestURI();

        BufferedReader br = httpServletRequest.getReader();

        br.lines().forEach(line -> {

            log.info("url : {}, line : {}", url, line);
        });

        filterChain.doFilter(servletRequest, servletResponse);

        //후처리

        // req

        String reqContent = new String(httpServletRequest.getContentAsByteArray());
        log.info("response url : {}, response body : {}", url, reqContent);


        String resContent = new String(httpServletResponse.getContentAsByteArray());
        int httpStatus = httpServletResponse.getStatus();

        log.info("response status : {}, response body : {}", httpStatus, resContent);



    }

}
