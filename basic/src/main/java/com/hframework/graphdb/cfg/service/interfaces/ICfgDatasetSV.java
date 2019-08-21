package com.hframework.graphdb.cfg.service.interfaces;

import java.util.*;

import com.hframework.graphdb.cfg.domain.model.CfgDataset;
import com.hframework.graphdb.cfg.domain.model.CfgDataset_Example;


public interface ICfgDatasetSV   {

  
    /**
    * 创建数据集
    * @param cfgDataset
    * @return
    * @throws Exception
    */
    public int create(CfgDataset cfgDataset) throws  Exception;

    /**
    * 批量维护数据集
    * @param cfgDatasets
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgDataset[] cfgDatasets) throws  Exception;
    /**
    * 更新数据集
    * @param cfgDataset
    * @return
    * @throws Exception
    */
    public int update(CfgDataset cfgDataset) throws  Exception;

    /**
    * 通过查询对象更新数据集
    * @param cfgDataset
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgDataset cfgDataset, CfgDataset_Example example) throws  Exception;

    /**
    * 删除数据集
    * @param cfgDataset
    * @return
    * @throws Exception
    */
    public int delete(CfgDataset cfgDataset) throws  Exception;


    /**
    * 删除数据集
    * @param cfgDatasetId
    * @return
    * @throws Exception
    */
    public int delete(long cfgDatasetId) throws  Exception;


    /**
    * 查找所有数据集
    * @return
    */
    public List<CfgDataset> getCfgDatasetAll()  throws  Exception;

    /**
    * 通过数据集ID查询数据集
    * @param cfgDatasetId
    * @return
    * @throws Exception
    */
    public CfgDataset getCfgDatasetByPK(long cfgDatasetId)  throws  Exception;

    /**
    * 通过MAP参数查询数据集
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgDataset> getCfgDatasetListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询数据集
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgDataset> getCfgDatasetListByExample(CfgDataset_Example example) throws  Exception;


    /**
    * 通过MAP参数查询数据集数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgDatasetCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询数据集数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgDatasetCountByExample(CfgDataset_Example example) throws  Exception;


 }
