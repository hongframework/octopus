package com.hframework.graphdb.sec.service.interfaces;

import java.util.*;
import com.hframework.graphdb.sec.domain.model.RoleAuthorize;
import com.hframework.graphdb.sec.domain.model.RoleAuthorize_Example;


public interface IRoleAuthorizeSV   {

  
    /**
    * 创建角色授权
    * @param roleAuthorize
    * @return
    * @throws Exception
    */
    public int create(RoleAuthorize roleAuthorize) throws  Exception;

    /**
    * 批量维护角色授权
    * @param roleAuthorizes
    * @return
    * @throws Exception
    */
    public int batchOperate(RoleAuthorize[] roleAuthorizes) throws  Exception;
    /**
    * 更新角色授权
    * @param roleAuthorize
    * @return
    * @throws Exception
    */
    public int update(RoleAuthorize roleAuthorize) throws  Exception;

    /**
    * 通过查询对象更新角色授权
    * @param roleAuthorize
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(RoleAuthorize roleAuthorize, RoleAuthorize_Example example) throws  Exception;

    /**
    * 删除角色授权
    * @param roleAuthorize
    * @return
    * @throws Exception
    */
    public int delete(RoleAuthorize roleAuthorize) throws  Exception;


    /**
    * 删除角色授权
    * @param roleAuthorizeId
    * @return
    * @throws Exception
    */
    public int delete(long roleAuthorizeId) throws  Exception;


    /**
    * 查找所有角色授权
    * @return
    */
    public List<RoleAuthorize> getRoleAuthorizeAll()  throws  Exception;

    /**
    * 通过角色授权ID查询角色授权
    * @param roleAuthorizeId
    * @return
    * @throws Exception
    */
    public RoleAuthorize getRoleAuthorizeByPK(long roleAuthorizeId)  throws  Exception;

    /**
    * 通过MAP参数查询角色授权
    * @param params
    * @return
    * @throws Exception
    */
    public List<RoleAuthorize> getRoleAuthorizeListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询角色授权
    * @param example
    * @return
    * @throws Exception
    */
    public List<RoleAuthorize> getRoleAuthorizeListByExample(RoleAuthorize_Example example) throws  Exception;


    /**
    * 通过MAP参数查询角色授权数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getRoleAuthorizeCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询角色授权数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getRoleAuthorizeCountByExample(RoleAuthorize_Example example) throws  Exception;


 }
