package com.hframework.datacenter.interceptors;

import com.hframework.common.util.UrlHelper;
import com.hframework.web.context.WebContextHelper;
import com.hframework.datacenter.GraphDBRegistry;
import com.hframework.datacenter.WebContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zqh on 2016/4/11.
 */
public class GraphDBRegistryInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         GraphDBRegistry.getInstance();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }


}
