package com.hframework.graphdb.sec.service.interfaces;

import java.util.*;
import com.hframework.graphdb.sec.domain.model.Organize;
import com.hframework.graphdb.sec.domain.model.Organize_Example;


public interface IOrganizeSV   {

  
    /**
    * 创建组织
    * @param organize
    * @return
    * @throws Exception
    */
    public int create(Organize organize) throws  Exception;

    /**
    * 批量维护组织
    * @param organizes
    * @return
    * @throws Exception
    */
    public int batchOperate(Organize[] organizes) throws  Exception;
    /**
    * 更新组织
    * @param organize
    * @return
    * @throws Exception
    */
    public int update(Organize organize) throws  Exception;

    /**
    * 通过查询对象更新组织
    * @param organize
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Organize organize, Organize_Example example) throws  Exception;

    /**
    * 删除组织
    * @param organize
    * @return
    * @throws Exception
    */
    public int delete(Organize organize) throws  Exception;


    /**
    * 删除组织
    * @param organizeId
    * @return
    * @throws Exception
    */
    public int delete(long organizeId) throws  Exception;


    /**
    * 查找所有组织
    * @return
    */
    public List<Organize> getOrganizeAll()  throws  Exception;

    /**
    * 通过组织ID查询组织
    * @param organizeId
    * @return
    * @throws Exception
    */
    public Organize getOrganizeByPK(long organizeId)  throws  Exception;

    /**
    * 通过MAP参数查询组织
    * @param params
    * @return
    * @throws Exception
    */
    public List<Organize> getOrganizeListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询组织
    * @param example
    * @return
    * @throws Exception
    */
    public List<Organize> getOrganizeListByExample(Organize_Example example) throws  Exception;

    /**
    * 通过父级组织ID查询组织树
    * @param organize
    * @return
    * @throws Exception
    */
    public Map<Long, List<Organize>> getOrganizeTreeByParentId(Organize organize, Organize_Example example)  throws  Exception;

    /**
    * 通过MAP参数查询组织数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getOrganizeCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询组织数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getOrganizeCountByExample(Organize_Example example) throws  Exception;


 }
