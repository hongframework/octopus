package com.hframework.graphdb.sec.service.impl;

import java.util.*;

import com.hframework.graphdb.sec.service.interfaces.IDictionarySV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.graphdb.sec.domain.model.Dictionary;
import com.hframework.graphdb.sec.domain.model.Dictionary_Example;
import com.hframework.graphdb.sec.dao.DictionaryMapper;

@Service("iDictionarySV")
public class DictionarySVImpl  implements IDictionarySV {

	@Resource
	private DictionaryMapper dictionaryMapper;
  


    /**
    * 创建字典
    * @param dictionary
    * @return
    * @throws Exception
    */
    public int create(Dictionary dictionary) throws Exception {
        return dictionaryMapper.insertSelective(dictionary);
    }

    /**
    * 批量维护字典
    * @param dictionarys
    * @return
    * @throws Exception
    */
    public int batchOperate(Dictionary[] dictionarys) throws  Exception{
        int result = 0;
        if(dictionarys != null) {
            for (Dictionary dictionary : dictionarys) {
                if(dictionary.getDictionaryId() == null) {
                    result += this.create(dictionary);
                }else {
                    result += this.update(dictionary);
                }
            }
        }
        return result;
    }

    /**
    * 更新字典
    * @param dictionary
    * @return
    * @throws Exception
    */
    public int update(Dictionary dictionary) throws  Exception {
        return dictionaryMapper.updateByPrimaryKeySelective(dictionary);
    }

    /**
    * 通过查询对象更新字典
    * @param dictionary
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(Dictionary dictionary, Dictionary_Example example) throws  Exception {
        return dictionaryMapper.updateByExampleSelective(dictionary, example);
    }

    /**
    * 删除字典
    * @param dictionary
    * @return
    * @throws Exception
    */
    public int delete(Dictionary dictionary) throws  Exception {
        return dictionaryMapper.deleteByPrimaryKey(dictionary.getDictionaryId());
    }

    /**
    * 删除字典
    * @param dictionaryId
    * @return
    * @throws Exception
    */
    public int delete(long dictionaryId) throws  Exception {
        return dictionaryMapper.deleteByPrimaryKey(dictionaryId);
    }

    /**
    * 查找所有字典
    * @return
    */
    public List<Dictionary> getDictionaryAll()  throws  Exception {
        return dictionaryMapper.selectByExample(new Dictionary_Example());
    }

    /**
    * 通过字典ID查询字典
    * @param dictionaryId
    * @return
    * @throws Exception
    */
    public Dictionary getDictionaryByPK(long dictionaryId)  throws  Exception {
        return dictionaryMapper.selectByPrimaryKey(dictionaryId);
    }


    /**
    * 通过MAP参数查询字典
    * @param params
    * @return
    * @throws Exception
    */
    public List<Dictionary> getDictionaryListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询字典
    * @param example
    * @return
    * @throws Exception
    */
    public List<Dictionary> getDictionaryListByExample(Dictionary_Example example) throws  Exception {
        return dictionaryMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询字典数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getDictionaryCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询字典数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getDictionaryCountByExample(Dictionary_Example example) throws  Exception {
        return dictionaryMapper.countByExample(example);
    }


  	//getter
 	
	public DictionaryMapper getDictionaryMapper(){
		return dictionaryMapper;
	}
	//setter
	public void setDictionaryMapper(DictionaryMapper dictionaryMapper){
    	this.dictionaryMapper = dictionaryMapper;
    }
}
