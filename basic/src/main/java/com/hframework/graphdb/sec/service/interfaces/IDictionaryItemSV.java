package com.hframework.graphdb.sec.service.interfaces;

import java.util.*;
import com.hframework.graphdb.sec.domain.model.DictionaryItem;
import com.hframework.graphdb.sec.domain.model.DictionaryItem_Example;


public interface IDictionaryItemSV   {

  
    /**
    * 创建字典项
    * @param dictionaryItem
    * @return
    * @throws Exception
    */
    public int create(DictionaryItem dictionaryItem) throws  Exception;

    /**
    * 批量维护字典项
    * @param dictionaryItems
    * @return
    * @throws Exception
    */
    public int batchOperate(DictionaryItem[] dictionaryItems) throws  Exception;
    /**
    * 更新字典项
    * @param dictionaryItem
    * @return
    * @throws Exception
    */
    public int update(DictionaryItem dictionaryItem) throws  Exception;

    /**
    * 通过查询对象更新字典项
    * @param dictionaryItem
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(DictionaryItem dictionaryItem, DictionaryItem_Example example) throws  Exception;

    /**
    * 删除字典项
    * @param dictionaryItem
    * @return
    * @throws Exception
    */
    public int delete(DictionaryItem dictionaryItem) throws  Exception;


    /**
    * 删除字典项
    * @param dictionaryItemId
    * @return
    * @throws Exception
    */
    public int delete(long dictionaryItemId) throws  Exception;


    /**
    * 查找所有字典项
    * @return
    */
    public List<DictionaryItem> getDictionaryItemAll()  throws  Exception;

    /**
    * 通过字典项ID查询字典项
    * @param dictionaryItemId
    * @return
    * @throws Exception
    */
    public DictionaryItem getDictionaryItemByPK(long dictionaryItemId)  throws  Exception;

    /**
    * 通过MAP参数查询字典项
    * @param params
    * @return
    * @throws Exception
    */
    public List<DictionaryItem> getDictionaryItemListByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询字典项
    * @param example
    * @return
    * @throws Exception
    */
    public List<DictionaryItem> getDictionaryItemListByExample(DictionaryItem_Example example) throws  Exception;


    /**
    * 通过MAP参数查询字典项数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getDictionaryItemCountByParam(Map<String, Object> params)  throws  Exception;


    /**
    * 通过查询对象查询字典项数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getDictionaryItemCountByExample(DictionaryItem_Example example) throws  Exception;


 }
