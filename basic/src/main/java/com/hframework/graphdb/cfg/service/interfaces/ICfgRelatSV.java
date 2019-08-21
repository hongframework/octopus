package com.hframework.graphdb.cfg.service.interfaces;

import java.util.*;

import com.hframework.graphdb.cfg.domain.model.CfgRelat;
import com.hframework.graphdb.cfg.domain.model.CfgRelat_Example;


public interface ICfgRelatSV   {

  
    /**
    * 创建数据关系
    * @param cfgRelat
    * @return
    * @throws Exception
    */
    public int create(CfgRelat cfgRelat) throws  Exception;

    /**
    * 批量维护数据关系
    * @param cfgRelats
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgRelat[] cfgRelats) throws  Exception;
    /**
    * 更新数据关系
    * @param cfgRelat
    * @return
    * @throws Exception
    */
    public int update(CfgRelat cfgRelat) throws  Exception;

    /**
    * 通过查询对象更新数据关系
    * @param cfgRelat
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgRelat cfgRelat, CfgRelat_Example example) throws  Exception;

    /**
    * 删除数据关系
    * @param cfgRelat
    * @return
    * @throws Exception
    */
    public int delete(CfgRelat cfgRelat) throws  Exception;


    /**
    * 删除数据关系
    * @param cfgRelatId
    * @return
    * @throws Exception
    */
    public int delete(long cfgRelatId) throws  Exception;


    /**
    * 查找所有数据关系
    * @return
    */
    public List<CfgRelat> getCfgRelatAll()  throws  Exception;

    /**
    * 通过数据关系ID查询数据关系
    * @param cfgRelatId
    * @return
    * @throws Exception
    */
    public CfgRelat getCfgRelatByPK(long cfgRelatId)  throws  Exception;

    /**
    * 通过MAP参数查询数据关系
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgRelat> getCfgRelatListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询数据关系
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgRelat> getCfgRelatListByExample(CfgRelat_Example example) throws  Exception;


    /**
    * 通过MAP参数查询数据关系数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgRelatCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询数据关系数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgRelatCountByExample(CfgRelat_Example example) throws  Exception;


 }
