package com.hframework.datacenter.controllers;

import com.google.common.base.Joiner;
import com.hframework.beans.controller.ResultData;
import com.hframework.common.util.JavaUtil;
import com.hframework.common.util.UrlHelper;
import com.hframework.smartsql.client.DBClient;
import com.hframework.datacenter.GraphDBRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class AutoPageInnerController {
    private static Logger logger = LoggerFactory.getLogger(AutoPageInnerController.class);

    /**
     * 动态数据保存
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/runtime_submits.json")
    @ResponseBody
    public ResultData apidoc(HttpServletRequest request) throws Exception {
        String tableCode = request.getParameter("tableCode");
        String dbKey = tableCode.substring(0, tableCode.indexOf("_"));
        String tableName = tableCode.substring(tableCode.indexOf("_") + 1);
        DBClient.setCurrentDatabaseKey(dbKey);

        boolean isEdit = request.getHeader("Referer").contains("_edit.html");
        Map<String, String> queryParameters = UrlHelper.getUrlParameters(request.getHeader("Referer"), false);

        Set<String> tableFields = GraphDBRegistry.getInstance().getTableFields(Long.valueOf(dbKey), tableName);

        Map<String, String> values = new LinkedHashMap<>();
        for (String tableField : tableFields) {
            String value = request.getParameter(JavaUtil.getJavaVarName(tableField));
            if(value != null && !"".equals(value)) {
                values.put(tableField, "'" + value + "'");
            }
        }

        Map<String, String> conditions = new LinkedHashMap<>();
        for (String tableField : tableFields) {
            String value = queryParameters.get(JavaUtil.getJavaVarName(tableField));
            if(value != null && !"".equals(value)) {
                conditions.put(tableField , "'" + value + "'");
//                conditions.add(tableField + "='" + value + "'");
            }
        }

        StringBuffer sql = new StringBuffer();
        if(isEdit) {
            List<String> valueKVs = new ArrayList<>();
            for (Map.Entry<String, String> entry : values.entrySet()) {
                valueKVs.add(entry.getKey() + "=" + entry.getValue());
            }

            List<String> condKVs = new ArrayList<>();
            for (Map.Entry<String, String> entry : conditions.entrySet()) {
                condKVs.add(entry.getKey() + "=" + entry.getValue() + "");
            }

            sql.append("update ")
                    .append(tableName)
                    .append(" set ")
                    .append(Joiner.on(", ").join(valueKVs))
                    .append(" where ")
                    .append(Joiner.on(" and ").join(condKVs));
        }else {

            values.putAll(conditions);

            sql.append("insert into ")
                    .append(tableCode)
                    .append("(").append(Joiner.on(",").join(values.keySet())).append(") ")
                    .append(" values ")
                    .append("('").append(Joiner.on("','").join(values.values())).append("')");
        }

        DBClient.setCurrentDatabaseKey(dbKey);
        DBClient.executeUpdate(sql.toString(), DBClient.emptyObjectArray);

        //TODO 返回ID

        return ResultData.success();
    }
}
