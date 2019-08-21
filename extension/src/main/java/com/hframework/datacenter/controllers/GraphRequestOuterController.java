package com.hframework.datacenter.controllers;

import com.alibaba.fastjson.JSONObject;
import com.hframework.beans.controller.ResultData;
import com.hframework.common.util.StringUtils;
import com.hframework.datacenter.GraphDBRegistry;
import com.hframework.graphdb.cfg.domain.model.CfgRelat;
import com.hframework.graphdb.cfg.domain.model.CfgRelat_Example;
import com.hframework.graphdb.cfg.service.interfaces.ICfgRelatSV;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/graph/api/")
public class GraphRequestOuterController {

    @Resource
    private ICfgRelatSV relatSV;

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "table/init.json")
    @ResponseBody
    public ResultData tableInit(HttpServletRequest request) throws Exception {
        String dbId = request.getParameter("dbId");
        JSONObject netJson = GraphDBRegistry.getInstance().getNetJson(Long.valueOf(dbId.trim()));
        return ResultData.success(netJson);
    }

    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "table/edge/add.json")
    @ResponseBody
    public ResultData tableAddEdge(HttpServletRequest request) throws Exception {
        Long dbId = Long.valueOf(request.getParameter("dbId"));
        String sourceTable = request.getParameter("sourceTable");
        String sourceField = request.getParameter("sourceField");
        String targetTable = request.getParameter("targetTable");
        String targetField = request.getParameter("targetField");

        CfgRelat_Example example = new CfgRelat_Example();
        example.createCriteria()
                .andDbIdEqualTo(dbId)
                .andSourceTableEqualTo(sourceTable)
                .andSourceFieldEqualTo(sourceField)
                .andTargetTableEqualTo(targetTable)
                .andTargetFieldEqualTo(targetField);

        int count = relatSV.getCfgRelatCountByExample(example);
        if(count == 0) {
            CfgRelat relat = new CfgRelat();
            relat.setDbId(dbId);
            relat.setSourceTable(sourceTable);
            relat.setSourceField(sourceField);
            relat.setTargetTable(targetTable);
            relat.setTargetField(targetField);
            relat.setStatus((byte) 1);
            int update = relatSV.create(relat);
            if(update > 0) {
                GraphDBRegistry.getInstance().addNodeEdge(dbId, sourceTable, sourceField, targetTable, targetField);
            }
        }

        CfgRelat cfgRelat = relatSV.getCfgRelatListByExample(example).get(0);

        JSONObject netJson = GraphDBRegistry.getInstance().getNetJson(dbId);
        JSONObject data = new JSONObject();
        data.put("add_ok", count == 0);
        data.put("net", netJson);
        data.put("edge", cfgRelat);
        return ResultData.success(data);
    }


    /**
     *
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "data/init.json")
    @ResponseBody
    public ResultData dataInit(HttpServletRequest request) throws Exception {

        String dbIdString = request.getParameter("dbId");

        String nodeId = request.getParameter("nodeId");
        String dataId = request.getParameter("dataId");
        if(StringUtils.isBlank(dataId)) {
            dataId = nodeId.substring(nodeId.lastIndexOf("_") + 1);
            nodeId = nodeId.substring(0, nodeId.lastIndexOf("_"));
        }
        Long dbId;
        if(StringUtils.isNotBlank(dbIdString)) {
            dbId = Long.valueOf(dbIdString);
        }else {
            dbId = Long.valueOf(nodeId.substring(0, nodeId.indexOf("_")));
            nodeId = nodeId.substring(nodeId.indexOf("_") + 1);
        }


        GraphDBRegistry.GraphNet net = RuntimeDataUtils.getRuntimeDataGraphNet(dbId, nodeId, dataId);
        ;
        JSONObject data = new JSONObject();
        data.put("hot_node_id", net.getHotNode().getId());
        data.put("net", net.getJson());

        return ResultData.success(data);
    }

}
