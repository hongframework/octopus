package com.hframework.graphdb.sec.service.interfaces;

import java.util.*;
import com.hframework.graphdb.sec.domain.model.UserAuthorize;
import com.hframework.graphdb.sec.domain.model.UserAuthorize_Example;


public interface IUserAuthorizeSV   {

  
    /**
    * 创建用户授权
    * @param userAuthorize
    * @return
    * @throws Exception
    */
    public int create(UserAuthorize userAuthorize) throws  Exception;

    /**
    * 批量维护用户授权
    * @param userAuthorizes
    * @return
    * @throws Exception
    */
    public int batchOperate(UserAuthorize[] userAuthorizes) throws  Exception;
    /**
    * 更新用户授权
    * @param userAuthorize
    * @return
    * @throws Exception
    */
    public int update(UserAuthorize userAuthorize) throws  Exception;

    /**
    * 通过查询对象更新用户授权
    * @param userAuthorize
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(UserAuthorize userAuthorize, UserAuthorize_Example example) throws  Exception;

    /**
    * 删除用户授权
    * @param userAuthorize
    * @return
    * @throws Exception
    */
    public int delete(UserAuthorize userAuthorize) throws  Exception;


    /**
    * 删除用户授权
    * @param userAuthorizeId
    * @return
    * @throws Exception
    */
    public int delete(long userAuthorizeId) throws  Exception;


    /**
    * 查找所有用户授权
    * @return
    */
    public List<UserAuthorize> getUserAuthorizeAll()  throws  Exception;

    /**
    * 通过用户授权ID查询用户授权
    * @param userAuthorizeId
    * @return
    * @throws Exception
    */
    public UserAuthorize getUserAuthorizeByPK(long userAuthorizeId)  throws  Exception;

    /**
    * 通过MAP参数查询用户授权
    * @param params
    * @return
    * @throws Exception
    */
    public List<UserAuthorize> getUserAuthorizeListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询用户授权
    * @param example
    * @return
    * @throws Exception
    */
    public List<UserAuthorize> getUserAuthorizeListByExample(UserAuthorize_Example example) throws  Exception;


    /**
    * 通过MAP参数查询用户授权数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getUserAuthorizeCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询用户授权数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getUserAuthorizeCountByExample(UserAuthorize_Example example) throws  Exception;


 }
