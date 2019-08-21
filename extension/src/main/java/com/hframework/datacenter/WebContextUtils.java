package com.hframework.datacenter;

import com.google.common.collect.Lists;
import com.hframework.common.frame.ServiceFactory;
import com.hframework.common.util.StringUtils;
import com.hframework.common.util.file.FileUtils;
import com.hframework.common.util.message.XmlUtils;
import com.hframework.smartsql.client.DBClient;
import com.hframework.web.config.bean.DataSet;
import com.hframework.web.config.bean.dataset.Entity;
import com.hframework.web.config.bean.dataset.Field;
import com.hframework.web.config.bean.dataset.Fields;
import com.hframework.web.config.bean.module.Page;
import com.hframework.web.context.WebContext;
import com.hframework.web.context.WebContextHelper;
import com.hframework.datacenter.interceptors.CommonInterceptor;
import com.hframework.graphdb.cfg.domain.model.CfgDataset;
import com.hframework.graphdb.cfg.domain.model.CfgDataset_Example;
import com.hframework.graphdb.cfg.domain.model.CfgDb;
import com.hframework.graphdb.cfg.service.interfaces.ICfgDatasetSV;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

import static com.hframework.common.util.BeanUtils.getFilds;
import static com.hframework.common.util.BeanUtils.getMethods;

public class WebContextUtils {
    private static WebContextHelper contextHelper;

    public static WebContextHelper getWebContextHelper() throws IOException {
        if(contextHelper == null) {
            synchronized (CommonInterceptor.class) {
                if(contextHelper == null) {
                    contextHelper = new WebContextHelper();
                }
            }
        }
        return contextHelper;
    }

    private static Logger logger = LoggerFactory.getLogger(WebContextUtils.class);

    private static Object[] globalEmptyObject = new Object[0];
    private static Element rootElement;

    static {
        try {
            rootElement = DocumentHelper.parseText(FileUtils.readFile(WebContextUtils.class.getClassLoader().
                        getResource("").getPath() +"runtimePageTempaltes.xml")).getRootElement();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String registerDatabase(CfgDb cfgDb) {
        String key = String.valueOf(cfgDb.getId());
        String host = cfgDb.getHost();
        Integer port = cfgDb.getPort();
        String dbName = cfgDb.getDatabase();
        String username = cfgDb.getUsername();
        String password = cfgDb.getPassword();
        String url = "jdbc:mysql://" + host + ":" + port + "/" +dbName + "?useUnicode=true&tinyInt1isBit=false";
        DBClient.registerDatabase(key, url, username, password);
        return key;
    }

    /**
     * 加载数据库表结构，生成默认数据集配置
     * @param cfgDb
     * @throws Exception
     */
    public static List<CfgDataset> generateDefaultDataSet(CfgDb cfgDb) throws Exception {

        DBClient.setCurrentDatabaseKey(registerDatabase(cfgDb));

        List<CfgDataset> cfgDatasets = new ArrayList<>();
        ICfgDatasetSV datasetSV = ServiceFactory.getService(ICfgDatasetSV.class);

        List<CfgDataset> cfgDatasetAll = datasetSV.getCfgDatasetAll();
        Map<String, CfgDataset> cfgDatasetMap = new HashMap<>();
        for (CfgDataset dataset : cfgDatasetAll) {
            if(dataset.getDbId() == cfgDb.getId()) {
                cfgDatasetMap.put(dataset.getTableName(), dataset);
            }
        }

        List<Map<String, Object>> comments = DBClient.executeQueryMaps("SELECT table_name, table_comment FROM " +
                "information_schema.TABLES WHERE table_schema = '" + cfgDb.getDatabase() + "'", globalEmptyObject);
        Map<String, String> commentMap = new HashMap<>();
        for (Map<String, Object> comment : comments) {
            commentMap.put(String.valueOf(comment.get("table_name")),
                    String.valueOf(comment.get("table_comment") == null || "".equals(comment.get("table_comment"))
                            ? comment.get("table_name") : comment.get("table_comment")));
        }
        List<Map<String, Object>> maps = DBClient.executeQueryMaps("SHOW TABLES;", globalEmptyObject);
        for (Map<String, Object> map : maps) {
            String tableName = (String) map.values().iterator().next();
            if(cfgDatasetMap.containsKey(tableName)) {
                continue;
            }
            List<Map<String, Object>> columns = DBClient.executeQueryMaps("SHOW FULL COLUMNS FROM " + tableName, globalEmptyObject);


            Document document = DocumentHelper.createDocument();
            Element root = document.addElement("dataset");
            Element edit = root.addElement("edit");
            Element rule = root.addElement("rule");
            Element hid = root.addElement("hid");

            root.addAttribute("name", commentMap.get(tableName));
            for (Map<String, Object> column : columns) {
                String code = String.valueOf(column.get("Field"));
                String key = String.valueOf(column.get("Key"));
                Boolean notNull = "No".equals(String.valueOf(column.get("Null")));
                String type = String.valueOf(column.get("Type"));
                String extra = String.valueOf(column.get("Extra"));
                String comment = String.valueOf(column.get("Comment"));

                String name = org.apache.commons.lang3.StringUtils.isBlank(comment.split(",| ")[0]) ? code : comment.split(",| ")[0];

                boolean isPk = "PRI".equals(key);
                boolean isAutoPk = isPk && "auto_increment".equals(extra);
                boolean isName = code.toLowerCase().endsWith("name");
                boolean isInfo = code.toLowerCase().endsWith("code")
                        || code.toLowerCase().endsWith("value")
                        || code.toLowerCase().endsWith("score");


                com.hframework.datacenter.handlers.Field field = new com.hframework.datacenter.handlers.Field();
                field.setCode(code);
                field.setName(name);
//                field.setTipinfo(comment);
                field.setNotNull(String.valueOf(notNull));
                field.setIsKey(String.valueOf(isPk));
                field.setIsName(String.valueOf(isName));



                if(isAutoPk) {
                    field.setDefaultValue("incr");
                }

                if("datetime".equals(type)) {
                    field.setEditType("datetime");
                }else {
                    field.setEditType("input");
                }

                //带有的规则默认都是不能编辑的
                if(org.apache.commons.lang3.StringUtils.isNotBlank(field.getDefaultValue())) {
                    field.setEditType("hidden");
                }else if(isName || isInfo || notNull || !code.endsWith("id")) {//名称、编码、ID、不为空属性都可以编辑

                }else {//否则都不能编辑
                    field.setEditType("hidden");
                }

                String tempXml = XmlUtils.writeValueAsString(field);
                Element fieldNode = DocumentHelper.parseText(tempXml).getRootElement();

                edit.add(fieldNode);
//                if(!"hidden".equals(field.getEditType())) {
//                    edit.add(fieldNode);
//                }else if(StringUtils.isNotBlank(field.getDefaultValue())){
//                    rule.add(fieldNode);
//                }else {
//                    hid.add(fieldNode);
//                }

            }

            String xml = root.asXML();

            CfgDataset dataset = new CfgDataset();
            dataset.setDbId(cfgDb.getId());
            dataset.setTableName(tableName);
            dataset.setTableDesc(commentMap.get(tableName));
            dataset.setStatus((byte) 1);
            dataset.seteCreateXml(xml);
            dataset.seteUpdateXml(xml);
            dataset.setqListXml(xml);
            dataset.setqCondXml(xml);

            datasetSV.create(dataset);
            cfgDatasets.add(dataset);
        }

        return cfgDatasets;
    }

    /**
     * 依照数据集配置生成hframework数据集，并加载进入WebContext
     * @param cfgDb
     * @throws Exception
     */
    public static void refreshAllDataSets(CfgDb cfgDb) throws Exception {
        ICfgDatasetSV datasetSV = ServiceFactory.getService(ICfgDatasetSV.class);
        CfgDataset_Example example = new CfgDataset_Example();
        example.createCriteria().andDbIdEqualTo(cfgDb.getId());
        List<CfgDataset> cfgDatasetAll = datasetSV.getCfgDatasetListByExample(example);
        for (CfgDataset dataset : cfgDatasetAll) {
            rewriteDataSet(dataset.getDbId(), dataset.getTableName(), dataset.getTableDesc(), "list", dataset.getqListXml());
            rewriteDataSet(dataset.getDbId(), dataset.getTableName(), dataset.getTableDesc(), "cond", dataset.getqCondXml());
            rewriteDataSet(dataset.getDbId(), dataset.getTableName(), dataset.getTableDesc(), "create", dataset.geteCreateXml());
            rewriteDataSet(dataset.getDbId(), dataset.getTableName(), dataset.getTableDesc(), "update", dataset.geteUpdateXml());
        }
    }

    /**
     * 依照数据集配置生成hframework页面信息，并加载进入WebContext
     * @param cfgDb
     * @throws Exception
     */
    public static void refreshAllPageInfo(CfgDb cfgDb) throws Exception {
        ICfgDatasetSV datasetSV = ServiceFactory.getService(ICfgDatasetSV.class);
        CfgDataset_Example example = new CfgDataset_Example();
        example.createCriteria().andDbIdEqualTo(cfgDb.getId());
        List<CfgDataset> cfgDatasetAll = datasetSV.getCfgDatasetListByExample(example);
        for (CfgDataset dataset : cfgDatasetAll) {
            rewritePage(dataset.getDbId() + "_" + dataset.getTableName(), dataset.getTableName(), null);
        }
    }

    public static void refreshDataSet(Long dataSetId, String dateSetType) throws Exception {
        ICfgDatasetSV service = ServiceFactory.getService(ICfgDatasetSV.class);
        CfgDataset cfgDataset = service.getCfgDatasetByPK(dataSetId);


        if("list".equals(dateSetType)) {
            rewriteDataSet(cfgDataset.getDbId(), cfgDataset.getTableName(), cfgDataset.getTableDesc(), dateSetType, cfgDataset.getqListXml());
        }else if("cond".equals(dateSetType)) {
            rewriteDataSet(cfgDataset.getDbId(), cfgDataset.getTableName(), cfgDataset.getTableDesc(), dateSetType, cfgDataset.getqCondXml());
        }else if("create".equals(dateSetType)) {
            rewriteDataSet(cfgDataset.getDbId(), cfgDataset.getTableName(), cfgDataset.getTableDesc(), dateSetType, cfgDataset.geteCreateXml());
        }else if("update".equals(dateSetType)) {
            rewriteDataSet(cfgDataset.getDbId(), cfgDataset.getTableName(), cfgDataset.getTableDesc(), dateSetType, cfgDataset.geteUpdateXml());
        }
        rewritePage(cfgDataset.getDbId() + "_" + cfgDataset.getTableName(), cfgDataset.getTableName(), dateSetType);
    }

    public static void rewritePage(String tableCode, String tableName, String dataSetType) throws Exception {
        List<Page> pageList = new ArrayList<>();
        if("list".equals(dataSetType)) {
            addPage(pageList, rootElement.element("list").getTextTrim(), tableCode, tableName);
            addPage(pageList, rootElement.element("query").getTextTrim(), tableCode, tableName);
            addPage(pageList, rootElement.element("detail").getTextTrim(), tableCode, tableName);
        }else if("cond".equals(dataSetType)) {
            addPage(pageList, rootElement.element("query").getTextTrim(), tableCode, tableName);
        }else if("create".equals(dataSetType)) {
            addPage(pageList, rootElement.element("create").getTextTrim(), tableCode, tableName);
        }else if("update".equals(dataSetType)) {
            addPage(pageList, rootElement.element("update").getTextTrim(), tableCode, tableName);
            addPage(pageList, rootElement.element("eList").getTextTrim(), tableCode, tableName);
        }else {
            addPage(pageList, rootElement.element("query").getTextTrim(), tableCode, tableName);
            addPage(pageList, rootElement.element("create").getTextTrim(), tableCode, tableName);
            addPage(pageList, rootElement.element("update").getTextTrim(), tableCode, tableName);
            addPage(pageList, rootElement.element("detail").getTextTrim(), tableCode, tableName);
            addPage(pageList, rootElement.element("list").getTextTrim(), tableCode, tableName);
            addPage(pageList, rootElement.element("eList").getTextTrim(), tableCode, tableName);
        }

        WebContext.get().reloadPage("runtime", pageList);

    }

    public static List<com.hframework.datacenter.handlers.Field> getFields(String configXml, String nodeName) throws DocumentException {
        if(StringUtils.isBlank(configXml)) return null;
        logger.info("config xml => " + configXml);
        Element fieldNode = DocumentHelper.parseText(configXml).getRootElement();
        Element edit = fieldNode.element(nodeName);
        if(edit == null) return null;

        List<com.hframework.datacenter.handlers.Field> results = new ArrayList<>();
        List fields = edit.elements("field");
        for (Object fieldObj : fields) {
            Element fieldEle = (Element) fieldObj;
            String fieldXml = fieldEle.asXML();
            com.hframework.datacenter.handlers.Field field = XmlUtils.readValue(fieldXml, com.hframework.datacenter.handlers.Field.class);
            results.add(field);
        }
        return results;
    }


    public static DataSet rewriteDataSet(Long dbId, String tableName, String tableDesc, String type, String configXml) throws Exception {

        DataSet dataSet = new DataSet();
        dataSet.setModule("runtime");
        dataSet.setCode(dbId + "_" + tableName + "_" + type);
        dataSet.setName(tableDesc);
        dataSet.setEntityList(Lists.newArrayList(new Entity()));
        dataSet.getEntityList().get(0).setText(dbId + "_" + tableName);

        dataSet.setFields(new Fields());
        dataSet.getFields().setFieldList(new ArrayList<Field>());

        addFields(dataSet.getFields().getFieldList(), getFields(configXml, "rule"), true);
        addFields(dataSet.getFields().getFieldList(), getFields(configXml, "edit"), false);
        addFields(dataSet.getFields().getFieldList(), getFields(configXml, "hid"), true);


        //这里暂不写文件，但是为了定位问题，这里先默认都放开 TODO
        String dataSetString = XmlUtils.writeValueAsString(dataSet);
        URL fileResource = XmlUtils.class.getResource("/" + getWebContextHelper().programConfigDataSetDir);
        String filePath = fileResource.getPath() + File.separator + dataSet.getCode() + ".xml";
        FileUtils.writeFile(filePath, dataSetString);

        WebContext.get().overrideDataSet(dataSet);




        return dataSet;
    }

    private static void addPage(List<Page> pageList, String template, String tableCode, String tableName) {
        String pageXml = template
                .replace("\\\\$", "^^^")
                .replaceAll("\\$\\{tableCode\\}", tableCode)
                .replaceAll("\\$\\{tableName\\}", tableName)
                .replace("^^^", "$");
        pageList.add(XmlUtils.readValue(pageXml, Page.class));
    }

    private static void addFields(List<com.hframework.web.config.bean.dataset.Field> fieldList,
                                  List<com.hframework.datacenter.handlers.Field> editFields, boolean isHidden) {
        if(editFields == null) return;
        for (com.hframework.datacenter.handlers.Field field : editFields) {
            com.hframework.web.config.bean.dataset.Field newFiled = new com.hframework.web.config.bean.dataset.Field();
            convertObject(newFiled, field);
            if(isHidden) {
                field.setEditType("hidden");
            }
            if(StringUtils.isBlank(field.getEditType())) {
                field.setEditType("text");
            }
            fieldList.add(newFiled);
        }

    }

    public static void convertObject(Object obj, Object obj1) {
        // 属性的名称及类型
        Map<String, Class> fileds = getFilds(obj1.getClass());
        // 方法名称及方法
        Map<String, Method> methods = getMethods(obj.getClass());
        Map<String, Method> method1s = getMethods(obj1.getClass());

        try {
            for (Iterator it = fileds.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry entry = (Map.Entry) it.next();
                // 属性名称
                String filed = (String) entry.getKey();
                // 转换成SET方法(首字母大写)
                StringBuffer sub = new StringBuffer("set");
                sub.append(StringUtils.upperCaseFirstChar(filed));
                // 获取SET方法
                Method setMethod = (Method) methods.get(sub.toString());
                // 转换成GET方法(首字母大写)
                StringBuffer gub = new StringBuffer("get");
                gub.append(StringUtils.upperCaseFirstChar(filed));
                // 获取GET方法
                Method getMethod = (Method) method1s.get(gub.toString());

                if (setMethod != null && getMethod != null) {
                    // 从baseForm中取出对应的值
                    Object temp = getMethod.invoke(obj1);
                    // 注入到对象中相应的属性
                    if (temp != null) {
                        setMethod.invoke(obj, new Object[]{temp});
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
