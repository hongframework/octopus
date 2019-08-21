package com.hframework.graphdb.sec.service.interfaces;

import java.util.*;
import com.hframework.graphdb.sec.domain.model.Menu;
import com.hframework.graphdb.sec.domain.model.Menu_Example;


public interface IMenuSV   {

  
    /**
    * 创建菜单
    * @param menu
    * @return
    * @throws Exception
    */
    public int create(Menu menu) throws  Exception;

    /**
    * 批量维护菜单
    * @param menus
    * @return
    * @throws Exception
    */
    public int batchOperate(Menu[] menus) throws  Exception;
    /**
    * 更新菜单
    * @param menu
    * @return
    * @throws Exception
    */
    public int update(Menu menu) throws  Exception;

    /**
    * 通过查询对象更新菜单
    * @param menu
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Menu menu, Menu_Example example) throws  Exception;

    /**
    * 删除菜单
    * @param menu
    * @return
    * @throws Exception
    */
    public int delete(Menu menu) throws  Exception;


    /**
    * 删除菜单
    * @param menuId
    * @return
    * @throws Exception
    */
    public int delete(long menuId) throws  Exception;


    /**
    * 查找所有菜单
    * @return
    */
    public List<Menu> getMenuAll()  throws  Exception;

    /**
    * 通过菜单ID查询菜单
    * @param menuId
    * @return
    * @throws Exception
    */
    public Menu getMenuByPK(long menuId)  throws  Exception;

    /**
    * 通过MAP参数查询菜单
    * @param params
    * @return
    * @throws Exception
    */
    public List<Menu> getMenuListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询菜单
    * @param example
    * @return
    * @throws Exception
    */
    public List<Menu> getMenuListByExample(Menu_Example example) throws  Exception;

    /**
    * 通过父级菜单ID查询菜单树
    * @param menu
    * @return
    * @throws Exception
    */
    public Map<Long, List<Menu>> getMenuTreeByParentId(Menu menu, Menu_Example example)  throws  Exception;

    /**
    * 通过MAP参数查询菜单数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getMenuCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询菜单数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getMenuCountByExample(Menu_Example example) throws  Exception;


 }
