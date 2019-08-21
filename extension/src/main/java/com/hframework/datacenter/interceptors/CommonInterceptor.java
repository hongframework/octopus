package com.hframework.datacenter.interceptors;

import com.hframework.common.frame.ServiceFactory;
import com.hframework.common.util.UrlHelper;
import com.hframework.datacenter.GraphDBRegistry;
import com.hframework.datacenter.WebContextUtils;
import com.hframework.graphdb.cfg.domain.model.CfgDataset;
import com.hframework.graphdb.cfg.service.interfaces.ICfgDatasetSV;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zqh on 2016/4/11.
 */
public class CommonInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String refer = request.getHeader("Referer");
        String url = refer.substring(0, refer.indexOf("?"));
        // http://localhost:15469/cfg/list_meta_xeditor.html?id=35&path=35
        if(url.endsWith("meta_xeditor.html")) {
            Long dataSetId = Long.valueOf(UrlHelper.getUrlParameters(refer, false).get("id"));
            ICfgDatasetSV service = ServiceFactory.getService(ICfgDatasetSV.class);
            CfgDataset cfgDataset = service.getCfgDatasetByPK(dataSetId);
            if(url.endsWith("list_meta_xeditor.html")) {
                WebContextUtils.refreshDataSet(dataSetId, "list");
            }else if(url.endsWith("cond_meta_xeditor.html")) {
                WebContextUtils.refreshDataSet(dataSetId, "cond");
            }else if(url.endsWith("create_meta_xeditor.html")) {
                WebContextUtils.refreshDataSet(dataSetId, "create");
            }else if(url.endsWith("update_meta_xeditor.html")) {
                WebContextUtils.refreshDataSet(dataSetId, "update");
            }
            GraphDBRegistry.getInstance().addDataSet(cfgDataset);

        }
    }


}
