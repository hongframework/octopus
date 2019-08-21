package com.hframework.graphdb.cfg.service.interfaces;

import java.util.*;

import com.hframework.graphdb.cfg.domain.model.CfgDb;
import com.hframework.graphdb.cfg.domain.model.CfgDb_Example;


public interface ICfgDbSV   {

  
    /**
    * 创建数据库
    * @param cfgDb
    * @return
    * @throws Exception
    */
    public int create(CfgDb cfgDb) throws  Exception;

    /**
    * 批量维护数据库
    * @param cfgDbs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgDb[] cfgDbs) throws  Exception;
    /**
    * 更新数据库
    * @param cfgDb
    * @return
    * @throws Exception
    */
    public int update(CfgDb cfgDb) throws  Exception;

    /**
    * 通过查询对象更新数据库
    * @param cfgDb
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgDb cfgDb, CfgDb_Example example) throws  Exception;

    /**
    * 删除数据库
    * @param cfgDb
    * @return
    * @throws Exception
    */
    public int delete(CfgDb cfgDb) throws  Exception;


    /**
    * 删除数据库
    * @param cfgDbId
    * @return
    * @throws Exception
    */
    public int delete(long cfgDbId) throws  Exception;


    /**
    * 查找所有数据库
    * @return
    */
    public List<CfgDb> getCfgDbAll()  throws  Exception;

    /**
    * 通过数据库ID查询数据库
    * @param cfgDbId
    * @return
    * @throws Exception
    */
    public CfgDb getCfgDbByPK(long cfgDbId)  throws  Exception;

    /**
    * 通过MAP参数查询数据库
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgDb> getCfgDbListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询数据库
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgDb> getCfgDbListByExample(CfgDb_Example example) throws  Exception;


    /**
    * 通过MAP参数查询数据库数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgDbCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询数据库数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgDbCountByExample(CfgDb_Example example) throws  Exception;


 }
