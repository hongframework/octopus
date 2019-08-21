package com.hframework.datacenter.controllers;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.hframework.common.util.JavaUtil;
import com.hframework.common.util.StringUtils;
import com.hframework.smartsql.client.DBClient;
import com.hframework.datacenter.GraphDBRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RuntimeDataUtils {

    public static String getPrimaryKey(Long dbId, String tableName) throws Exception {
        return GraphDBRegistry.getInstance().getNet(dbId).getNode(tableName).getKeyAttrNode().getId();
    }

    public static List<String> getNameKeys(Long dbId, String tableName) throws Exception {
        return GraphDBRegistry.getInstance().getNet(dbId).getNode(tableName).getNameKeys();
    }

    public static List<Map<String, Object>> getList(Long dbId, String tableName, String dataField, String dataId) throws Exception {
        DBClient.setCurrentDatabaseKey(String.valueOf(dbId));
        String sql = "select * from " + tableName + " where " + dataField+ "='"+ dataId +  "'";
        List<Map<String, Object>> map = DBClient.executeQueryMaps(sql, DBClient.emptyObjectArray);
        return map;
    }

    public static Map<String, Object> getOne(Long dbId, String tableName, String dataField, String dataId) throws Exception {
        DBClient.setCurrentDatabaseKey(String.valueOf(dbId));
        String sql = "select * from " + tableName + " where " + dataField+ "='"+ dataId +  "'";
        Map<String, Object> map = DBClient.executeQueryMap(sql, DBClient.emptyObjectArray);
        return map;
    }



    public static List<GraphDBRegistry.GraphNode> getGraphNodes(Long dbId, String tableName,
                                                                List<Map<String, Object>> datas, String tableDesc) throws Exception {
        List<GraphDBRegistry.GraphNode> result = new ArrayList<>();
        List<String> nameKeys = getNameKeys(dbId, tableName);
        String primaryKey = getPrimaryKey(dbId, tableName);
        for (Map<String, Object> data : datas) {
            List<String> nameValues = new ArrayList<>();
            for (String nameField : nameKeys) {
                if(data.get(nameField) != null && !"".equals(data.get(nameField))) {
                    nameValues.add(String.valueOf(data.get(nameField)));
                }
            }
            String nodeId = tableName + "_" + String.valueOf(data.get(primaryKey));
            String nodeName = Joiner.on("|").join(nameValues);
            GraphDBRegistry.GraphNode graphNode = new GraphDBRegistry.GraphNode(nodeId, tableDesc + ":" +
                    (StringUtils.isNotBlank(nodeName) ? nodeName :  nodeId), tableName);

            graphNode.addAttrNode(new GraphDBRegistry.GraphNode("url_entity",  "/runtime/" + dbId + "_" + tableName + "_", null), false, false);
            graphNode.addAttrNode(new GraphDBRegistry.GraphNode("url_params", ".html?" +
                    JavaUtil.getJavaVarName(primaryKey) + "=" + data.get(primaryKey) + "&isPop=true", tableName), false, false);

            result.add(graphNode);
        }
        return result;
    }


    public static GraphDBRegistry.GraphNet getRuntimeDataGraphNet(Long dbId, String tableName, String dataId) throws Exception {
        GraphDBRegistry.GraphNet tableNet = GraphDBRegistry.getInstance().getNet(dbId);

        Map<String, Object> hostData = getOne(dbId, tableName, getPrimaryKey(dbId, tableName), dataId);

        GraphDBRegistry.GraphNet network = GraphDBRegistry.getInstance().getDataGraph(String.valueOf(dbId), tableName, dataId);
//        if(network != null) {
//            return network;
//        }
        network = new GraphDBRegistry.GraphNet();
        GraphDBRegistry.getInstance().putDataGraph(String.valueOf(dbId), tableName, dataId, network);
        GraphDBRegistry.GraphNode curNode = getGraphNodes(dbId, tableName, Lists.newArrayList(hostData), tableNet.getNode(tableName).getName()).get(0);
        network.addHotNode(curNode);

        addFromEdge(tableNet, tableName, hostData, dbId, network, curNode ,dataId);
        addToEdge(tableNet, tableName, hostData, dbId, network, curNode ,dataId, true);

        return network;
    }

    private static void addFromEdge(GraphDBRegistry.GraphNet tableNet, String tableName, Map<String, Object> hostData,
                                    Long dbId, GraphDBRegistry.GraphNet network, GraphDBRegistry.GraphNode curNode, String dataId) throws Exception {
        List<GraphDBRegistry.GraphEdge> fromEdges = tableNet.getNode(tableName).getFromEdges();
        for (GraphDBRegistry.GraphEdge edge : fromEdges) {
            String relTableName = edge.getSourceId();
            String relTableField = edge.getSourceRemark();
            String curTableField = edge.getTargetRemark();
            Object curVal = hostData.get(curTableField);
            addRelatTableData(dbId, network, curNode, edge, relTableName, relTableField, curTableField, curVal, tableNet, true, true);

            if(StringUtils.isNotBlank(dataId)) {
                GraphDBRegistry.GraphNode virtualNode = getVirtualNode(tableNet, dbId, tableName, dataId, relTableName, relTableField);
                network.addNode(virtualNode);
                network.addEdge(virtualNode.getId(), curNode.getId(), virtualNode.getName(), relTableField, curTableField);
            }
        }
    }



    private static void addToEdge(GraphDBRegistry.GraphNet tableNet, String tableName, Map<String, Object> hostData,
                                  Long dbId, GraphDBRegistry.GraphNet network, GraphDBRegistry.GraphNode curNode, String dataId, boolean cycleFetch) throws Exception {
        List<GraphDBRegistry.GraphEdge> toEdges = tableNet.getNode(tableName).getToEdges();
        for (GraphDBRegistry.GraphEdge edge : toEdges) {
            String relTableName = edge.getTargetId();
            String relTableField = edge.getTargetRemark();
            String curTableField = edge.getSourceRemark();
            Object curVal = hostData.get(curTableField);
            addRelatTableData(dbId, network, curNode, edge, relTableName, relTableField, curTableField, curVal, tableNet, cycleFetch, false);
        }
    }


    private static List<GraphDBRegistry.GraphNode>  addRelatTableData(Long dbId, GraphDBRegistry.GraphNet network, GraphDBRegistry.GraphNode curNode,
                                          GraphDBRegistry.GraphEdge edge, String relTableName, String relTableField, String curTableField, Object
                                                                              curVal, GraphDBRegistry.GraphNet tableNet, boolean cycleFetch, boolean isFrom) throws Exception {
        if(curVal != null && !"".equals(curVal)) {
            List<Map<String, Object>> list = getList(dbId, relTableName, relTableField, String.valueOf(curVal));
            List<GraphDBRegistry.GraphNode> relNodes = getGraphNodes(dbId, relTableName, list, tableNet.getNode(relTableName).getName());
            if(relNodes.size() > 1) relNodes = relNodes.subList(0, 1);
            network.addNodes(relNodes);
            for (GraphDBRegistry.GraphNode relNode : relNodes) {
                if(isFrom) {
                    network.addEdge(relNode.getId(), curNode.getId(), edge.getName(), relTableField, curTableField);
                }else {
                    network.addEdge(curNode.getId(), relNode.getId(), edge.getName(), curTableField, relTableField);
                }

            }

            if(cycleFetch) {
                for (int i = 0; i < relNodes.size(); i++) {
                    addToEdge(tableNet, relTableName, list.get(i), dbId, network, relNodes.get(i), null, false);
                }
            }

            return relNodes;
        }

        return new ArrayList<>();
    }

    private static GraphDBRegistry.GraphNode getVirtualNode(GraphDBRegistry.GraphNet tableNet, Long dbId, String tableName,
                                                            String dataId, String relTableName, String relTableField) {
        GraphDBRegistry.GraphNode graphNode = new GraphDBRegistry.GraphNode("_v_" + tableName + "_" + relTableName,
                "添加" + tableNet.getNode(relTableName).getName(), relTableName);
        graphNode.addAttrNode(new GraphDBRegistry.GraphNode("url_entity",  "/runtime/" + dbId +
                "_" + relTableName + "_", null), false, false);
        graphNode.addAttrNode(new GraphDBRegistry.GraphNode("url_params", ".html?" +
                JavaUtil.getJavaVarName(relTableField) + "=" + dataId + "&isPop=true", tableName), false, false);
        graphNode.setIsVirtual(true);
        return graphNode;
    }
}
