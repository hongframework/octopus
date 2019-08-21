package com.hframework.datacenter.handlers;

import com.hframework.web.extension.AbstractBusinessHandler;
import com.hframework.web.extension.annotation.AfterCreateHandler;
import com.hframework.web.extension.annotation.AfterUpdateHandler;
import com.hframework.datacenter.GraphDBRegistry;
import com.hframework.datacenter.WebContextUtils;
import com.hframework.graphdb.cfg.domain.model.CfgDataset;
import com.hframework.graphdb.cfg.domain.model.CfgDb;
import com.hframework.graphdb.cfg.service.interfaces.ICfgDatasetSV;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CfgDBHandler extends AbstractBusinessHandler<CfgDb> {

    @Resource
    private ICfgDatasetSV datasetSV;

    private static Object[] globalEmptyObject = new Object[0];

    @AfterCreateHandler
    public void onCreate(CfgDb cfgDb) throws Exception {
        sync(cfgDb);
    }

    @AfterUpdateHandler
    public void onUpdate(CfgDb cfgDb) throws Exception {
        sync(cfgDb);
    }



    private void sync(CfgDb cfgDb) throws Exception {
        List<CfgDataset> cfgDatasets = WebContextUtils.generateDefaultDataSet(cfgDb);//加载数据库表结构，生成默认数据集配置
        for (CfgDataset cfgDataset : cfgDatasets) {
            GraphDBRegistry.getInstance().addDataSet(cfgDataset);
        }

        WebContextUtils.refreshAllDataSets(cfgDb);//依照数据集配置生成hframework数据集，并加载进入WebContext
        WebContextUtils.refreshAllPageInfo(cfgDb);//依照数据集配置生成hframework页面信息，并加载进入WebContext


    }

}
