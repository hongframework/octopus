package com.hframework.graphdb.sec.service.interfaces;

import java.util.*;
import com.hframework.graphdb.sec.domain.model.User;
import com.hframework.graphdb.sec.domain.model.User_Example;


public interface IUserSV   {

  
    /**
    * 创建用户
    * @param user
    * @return
    * @throws Exception
    */
    public int create(User user) throws  Exception;

    /**
    * 批量维护用户
    * @param users
    * @return
    * @throws Exception
    */
    public int batchOperate(User[] users) throws  Exception;
    /**
    * 更新用户
    * @param user
    * @return
    * @throws Exception
    */
    public int update(User user) throws  Exception;

    /**
    * 通过查询对象更新用户
    * @param user
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(User user, User_Example example) throws  Exception;

    /**
    * 删除用户
    * @param user
    * @return
    * @throws Exception
    */
    public int delete(User user) throws  Exception;


    /**
    * 删除用户
    * @param userId
    * @return
    * @throws Exception
    */
    public int delete(long userId) throws  Exception;


    /**
    * 查找所有用户
    * @return
    */
    public List<User> getUserAll()  throws  Exception;

    /**
    * 通过用户ID查询用户
    * @param userId
    * @return
    * @throws Exception
    */
    public User getUserByPK(long userId)  throws  Exception;

    /**
    * 通过MAP参数查询用户
    * @param params
    * @return
    * @throws Exception
    */
    public List<User> getUserListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询用户
    * @param example
    * @return
    * @throws Exception
    */
    public List<User> getUserListByExample(User_Example example) throws  Exception;


    /**
    * 通过MAP参数查询用户数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getUserCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询用户数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getUserCountByExample(User_Example example) throws  Exception;


 }
