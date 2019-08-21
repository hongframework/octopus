package com.hframework.datacenter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hframework.common.frame.ServiceFactory;
import com.hframework.datacenter.handlers.Field;
import com.hframework.graphdb.cfg.domain.model.CfgDataset;
import com.hframework.graphdb.cfg.domain.model.CfgDb;
import com.hframework.graphdb.cfg.domain.model.CfgRelat;
import com.hframework.graphdb.cfg.service.interfaces.ICfgDatasetSV;
import com.hframework.graphdb.cfg.service.interfaces.ICfgDbSV;
import com.hframework.graphdb.cfg.service.interfaces.ICfgRelatSV;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;

import java.util.*;

public class GraphDBRegistry {
    private GraphDBRegistry(){}

    private static GraphDBRegistry instance;

    private Set<Long> dbs = new HashSet<>();

    private  Map<Long, GraphNet> tableGraphs = new HashMap<>();

    //运行时热图数据缓存
    private Map<String, GraphNet> dataHotGraphs = new LinkedHashMap<>();

    private static final int DATA_HOT_GRAPHS_MAX_LENGTH = 50;

    public static GraphDBRegistry getInstance() throws Exception {
        if(instance == null) {
            synchronized (GraphDBRegistry.class) {
                if(instance == null) {
                    instance = new GraphDBRegistry();
                    instance.initialize();
                }
            }
        }
        return instance;
    }

    public static String getDataGraphKey(String dbId, String tableName, String dataId) {
        return dbId + "_" + tableName + "_" + dataId;
    }

    public  GraphNet getDataGraph(String dbId, String tableName, String dataId) {
        return dataHotGraphs.get(getDataGraphKey(dbId, tableName, dataId));
    }

    public  GraphNet putDataGraph(String dbId, String tableName, String dataId, GraphNet graphNet) {
        if(dataHotGraphs.size() >= DATA_HOT_GRAPHS_MAX_LENGTH) {
            Iterator<Map.Entry<String, GraphNet>> iterator = dataHotGraphs.entrySet().iterator();
            while (iterator.hasNext()) {
                iterator.remove();
                break;
            }
        }
        return dataHotGraphs.put(getDataGraphKey(dbId, tableName, dataId), graphNet);
    }

    public void addDataSet(CfgDataset dataset) throws DocumentException {

        Long dbId = dataset.getDbId();
        dbs.add(dbId);
        String tableName = dataset.getTableName();
        String tableDesc = dataset.getTableDesc();
        if(!tableGraphs.containsKey(dbId)) {
            tableGraphs.put(dbId, new GraphNet());
        }

        GraphNode graphNode = tableGraphs.get(dbId).addNode(tableName, tableDesc, tableName);

        addAttrNode(graphNode, WebContextUtils.getFields(dataset.geteCreateXml(), "edit"));
        addAttrNode(graphNode, WebContextUtils.getFields(dataset.geteCreateXml(), "rule"));
        addAttrNode(graphNode, WebContextUtils.getFields(dataset.geteCreateXml(), "hid"));

        addAttrNode(graphNode, WebContextUtils.getFields(dataset.geteUpdateXml(), "edit"));
        addAttrNode(graphNode, WebContextUtils.getFields(dataset.geteUpdateXml(), "rule"));
        addAttrNode(graphNode, WebContextUtils.getFields(dataset.geteUpdateXml(), "hid"));

        addAttrNode(graphNode, WebContextUtils.getFields(dataset.getqListXml(), "edit"));
        addAttrNode(graphNode, WebContextUtils.getFields(dataset.getqListXml(), "rule"));
        addAttrNode(graphNode, WebContextUtils.getFields(dataset.getqListXml(), "hid"));

        addAttrNode(graphNode, WebContextUtils.getFields(dataset.getqCondXml(), "edit"));
        addAttrNode(graphNode, WebContextUtils.getFields(dataset.getqCondXml(), "rule"));
        addAttrNode(graphNode, WebContextUtils.getFields(dataset.getqCondXml(), "hid"));

    }

    public Set<String> getTableFields(Long dbId, String tableName) {
        return tableGraphs.get(dbId).nodes.get(tableName).attrNodes.keySet();
    }


    public  GraphNet getNet(Long dbId){
        return  this.tableGraphs.get(dbId);
    }

    public JSONObject  getNetJson(Long dbId) {
        return getNet(dbId).getJson();
    }


    private void addAttrNode(GraphNode parentNode, List<Field> fields) {
        if(fields == null) return ;
        for (Field field : fields) {
            parentNode.addAttrNode(new GraphNode(field.getCode(), field.getName(), parentNode.getTableName()),
                    "true".equals(field.getIsKey()), "true".equals(field.getIsName()));
        }
    }

    public void addDataEdge(CfgRelat cfgRelat) {
        Long dbId = cfgRelat.getDbId();
        String sourceTable = cfgRelat.getSourceTable();
        String targetTable = cfgRelat.getTargetTable();
        String sourceField = cfgRelat.getSourceField();
        String targetField = cfgRelat.getTargetField();
        if(!tableGraphs.containsKey(dbId)) {
            tableGraphs.put(dbId, new GraphNet());
        }
        tableGraphs.get(dbId).addEdge(sourceTable, targetTable, "", sourceField, targetField);
    }

    private void initialize() throws Exception {
        ICfgDatasetSV service = ServiceFactory.getService(ICfgDatasetSV.class);
        List<CfgDataset> cfgDatasetAll = service.getCfgDatasetAll();

        for (CfgDataset dataset : cfgDatasetAll) {
            addDataSet(dataset);
        }

        ICfgRelatSV relatSV = ServiceFactory.getService(ICfgRelatSV.class);
        List<CfgRelat> cfgRelatAll = relatSV.getCfgRelatAll();
        for (CfgRelat cfgRelat : cfgRelatAll) {
            addDataEdge(cfgRelat);
        }

        ICfgDbSV dbSV = ServiceFactory.getService(ICfgDbSV.class);
        for (Long db : dbs) {
            CfgDb cfgDb = dbSV.getCfgDbByPK(db);
            WebContextUtils.registerDatabase(cfgDb);
            try{
                WebContextUtils.refreshAllDataSets(cfgDb);//依照数据集配置生成hframework数据集，并加载进入WebContext
            }catch (Exception e) {
                e.printStackTrace();
            }
            try{
                WebContextUtils.refreshAllDataSets(cfgDb);//依照数据集配置生成hframework数据集，并加载进入WebContext
            }catch (Exception e) {
                e.printStackTrace();
            }
            try{
                WebContextUtils.refreshAllDataSets(cfgDb);//依照数据集配置生成hframework数据集，并加载进入WebContext
            }catch (Exception e) {
                e.printStackTrace();
            }
            WebContextUtils.refreshAllPageInfo(cfgDb);//依照数据集配置生成hframework页面信息，并加载进入WebContext

        }
    }

    public void addNodeEdge(Long dbId, String sourceTable, String sourceField, String targetTable, String targetField) {
        GraphNet net = getNet(dbId);
        net.addEdge(sourceTable, targetTable, "", sourceField, targetField);
    }

    public static class GraphNet{
        private GraphNode hotNode;
        private Map<String, GraphEdge> edges = new HashMap<>();
        private Map<String, GraphNode> nodes = new HashMap<>();

        public GraphNode addHotNode(GraphNode graphNode) {
            addNode(graphNode);
            hotNode = graphNode;
            return graphNode;
        }

        public GraphNode addNode(GraphNode node) {
            if(nodes.containsKey(node.getId())){
                GraphNode graphNode = nodes.get(node.getId());
                graphNode.name = node.getName();
                graphNode.tableName = node.tableName;
            }else{
                nodes.put(node.getId(), node);
            }
            return node;
        }


        public GraphNode addHotNode(String nodeId, String nodeName,  String tableName) {
            GraphNode graphNode = addNode(nodeId, nodeName, tableName);
            hotNode = graphNode;
            return graphNode;
        }

        public GraphNode addNode(String nodeId, String nodeName,  String tableName) {
            if(nodes.containsKey(nodeId)){
                GraphNode graphNode = nodes.get(nodeId);
                graphNode.name = nodeName;
                graphNode.tableName = tableName;
            }else{
                nodes.put(nodeId, new GraphNode(nodeId, nodeName, tableName));
            }
            return nodes.get(nodeId);
        }

        public void addEdge(String sourceId, String targetId, String name, String sourceRemark, String targetRemark){
            GraphEdge tempEdge = new GraphEdge(sourceId, targetId, name, sourceRemark, targetRemark);
            if(edges.containsKey(tempEdge.getId())){
                GraphEdge graphEdge = edges.get(tempEdge.getId());
                graphEdge.name = tempEdge.getName();
                graphEdge.sourceRemark = sourceRemark;
                graphEdge.targetRemark = targetRemark;
                edgeBuild(graphEdge);
            }else{
                edges.put(tempEdge.getId(), tempEdge);
                edgeBuild(tempEdge);
            }

        }

        public void edgeBuild(GraphEdge edge){
            if(edge.sourceNode == null && nodes.containsKey(edge.getSourceId())) {
                GraphNode graphNode = nodes.get(edge.getSourceId());
                edge.sourceNode = graphNode;
                graphNode.addToEdge(edge);
            }
            if(edge.targetNode == null && nodes.containsKey(edge.getTargetId())) {
                GraphNode graphNode = nodes.get(edge.getTargetId());
                edge.targetNode = graphNode;
                graphNode.addFromEdge(edge);
            }
            edge.isActivate = edge.sourceNode != null && edge.targetNode != null;
        }

        public GraphNode getHotNode() {
            return hotNode;
        }

        public JSONObject getJson(){
            JSONObject result = new JSONObject();

            JSONArray nodeArray = new JSONArray();
            for (GraphNode graphNode : nodes.values()) {
                JSONObject nodeObject =new JSONObject();
                nodeObject.put("id", graphNode.getId());
                nodeObject.put("label", graphNode.getName());
                nodeObject.put("schema", graphNode.getTableName());
                JSONArray attrs = new JSONArray();
                nodeObject.put("attrs", attrs);
                if(graphNode.isVirtual) {
                    nodeObject.put("isVirtual", true);
                }
                for (GraphNode attrNode : graphNode.attrNodes.values()) {
                  JSONObject attrObject = new JSONObject();
                  attrObject.put("id", attrNode.getId());
                  attrObject.put("name", attrNode.getName());
                  attrs.add(attrObject);
                }
                nodeArray.add(nodeObject);
            }
            result.put("nodes", nodeArray);


            JSONArray edgeArray = new JSONArray();
            for (GraphEdge graphEdge : edges.values()) {
                JSONObject edgeObject =new JSONObject();
                edgeObject.put("source", graphEdge.getSourceId());
                edgeObject.put("target", graphEdge.getTargetId());
                edgeObject.put("value", 1);
                edgeObject.put("shape", "quadratic");
                edgeObject.put("label", graphEdge.getName());
                edgeArray.add(edgeObject);
            }
            result.put("edges", edgeArray);

            return result;
        }


        public GraphNode getNode(String nodeId) {
            return nodes.get(nodeId);
        }

        public void addNodes(List<GraphNode> nodes) {
            for (GraphNode node : nodes) {
                addNode(node);
            }
        }
    }

    public static class GraphEdge{
        private String id;
        private String sourceId;
        private String targetId;
        private String name;

        private boolean isActivate = false;

        private String sourceRemark;
        private String targetRemark;

        private GraphNode sourceNode;
        private GraphNode targetNode;

        public GraphEdge(String sourceId, String targetId, String name, String sourceRemark, String targetRemark) {
            this.sourceId = sourceId;
            this.targetId = targetId;
            this.name = name;
            this.sourceRemark = sourceRemark;
            this.targetRemark = targetRemark;
            this.id = sourceId + "_" + targetId;
        }

        public GraphEdge(String sourceId, String targetId, String name) {
            this.sourceId = sourceId;
            this.targetId = targetId;
            this.name = name;
            this.id = sourceId + "_" + targetId;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSourceId() {
            return sourceId;
        }

        public void setSourceId(String sourceId) {
            this.sourceId = sourceId;
        }

        public String getTargetId() {
            return targetId;
        }

        public void setTargetId(String targetId) {
            this.targetId = targetId;
        }

        public GraphNode getSourceNode() {
            return sourceNode;
        }

        public void setSourceNode(GraphNode sourceNode) {
            this.sourceNode = sourceNode;
        }

        public GraphNode getTargetNode() {
            return targetNode;
        }

        public void setTargetNode(GraphNode targetNode) {
            this.targetNode = targetNode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSourceRemark() {
            return sourceRemark;
        }

        public String getTargetRemark() {
            return targetRemark;
        }
    }

    public static class GraphNode{
        private String tableName;
        private String id;
        private String name;
        private int type = 1;

        private Map<String, GraphNode> attrNodes = new HashMap<>();
        private GraphNode keyAttrNode;
        private List<String> nameKeys = new ArrayList<>();
        private boolean isVirtual;


        public GraphNode(String id, String name, String tableName) {
            this.tableName = tableName;
            this.id = id;
            this.name = name;
        }

        private List<GraphEdge> toEdges = new ArrayList<>();
        private List<GraphEdge> fromEdges = new ArrayList<>();

        public void addAttrNode(GraphNode attrNode, boolean isKeyAttr, boolean isNameAttr){
            if(attrNodes.containsKey(attrNode.getId())) {
                GraphNode graphNode = attrNodes.get(attrNode.getId());
                if(StringUtils.isBlank(graphNode.getName())) {
                    graphNode.name = attrNode.name;
                }
                if(isNameAttr && !nameKeys.contains(attrNode.getId())) nameKeys.add(attrNode.getId());
            }else {
                if(isKeyAttr) keyAttrNode = attrNode;
                if(isNameAttr) nameKeys.add(attrNode.getId());


                attrNodes.put(attrNode.getId(), attrNode);
            }
        }

        public GraphNode getKeyAttrNode() {
            return keyAttrNode;
        }

        public void addToEdge(GraphEdge edge){
            toEdges.add(edge);
        }

        public void addFromEdge(GraphEdge edge){
            fromEdges.add(edge);
        }



        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }


        public List<String> getNameKeys() {
            return nameKeys;
        }

        public List<GraphEdge> getToEdges() {
            return toEdges;
        }

        public List<GraphEdge> getFromEdges() {
            return fromEdges;
        }

        public void setIsVirtual(boolean isVirtual) {
            this.isVirtual = isVirtual;
        }
    }

}
