package com.hframework.graphdb.cfg.service.impl;

import java.util.*;

import com.hframework.graphdb.cfg.dao.CfgRelatMapper;
import com.hframework.graphdb.cfg.domain.model.CfgRelat;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.graphdb.cfg.domain.model.CfgRelat_Example;
import com.hframework.graphdb.cfg.service.interfaces.ICfgRelatSV;

@Service("iCfgRelatSV")
public class CfgRelatSVImpl  implements ICfgRelatSV {

	@Resource
	private CfgRelatMapper cfgRelatMapper;
  


    /**
    * 创建数据关系
    * @param cfgRelat
    * @return
    * @throws Exception
    */
    public int create(CfgRelat cfgRelat) throws Exception {
        return cfgRelatMapper.insertSelective(cfgRelat);
    }

    /**
    * 批量维护数据关系
    * @param cfgRelats
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgRelat[] cfgRelats) throws  Exception{
        int result = 0;
        if(cfgRelats != null) {
            for (CfgRelat cfgRelat : cfgRelats) {
                if(cfgRelat.getId() == null) {
                    result += this.create(cfgRelat);
                }else {
                    result += this.update(cfgRelat);
                }
            }
        }
        return result;
    }

    /**
    * 更新数据关系
    * @param cfgRelat
    * @return
    * @throws Exception
    */
    public int update(CfgRelat cfgRelat) throws  Exception {
        return cfgRelatMapper.updateByPrimaryKeySelective(cfgRelat);
    }

    /**
    * 通过查询对象更新数据关系
    * @param cfgRelat
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgRelat cfgRelat, CfgRelat_Example example) throws  Exception {
        return cfgRelatMapper.updateByExampleSelective(cfgRelat, example);
    }

    /**
    * 删除数据关系
    * @param cfgRelat
    * @return
    * @throws Exception
    */
    public int delete(CfgRelat cfgRelat) throws  Exception {
        return cfgRelatMapper.deleteByPrimaryKey(cfgRelat.getId());
    }

    /**
    * 删除数据关系
    * @param cfgRelatId
    * @return
    * @throws Exception
    */
    public int delete(long cfgRelatId) throws  Exception {
        return cfgRelatMapper.deleteByPrimaryKey(cfgRelatId);
    }

    /**
    * 查找所有数据关系
    * @return
    */
    public List<CfgRelat> getCfgRelatAll()  throws  Exception {
        return cfgRelatMapper.selectByExample(new CfgRelat_Example());
    }

    /**
    * 通过数据关系ID查询数据关系
    * @param cfgRelatId
    * @return
    * @throws Exception
    */
    public CfgRelat getCfgRelatByPK(long cfgRelatId)  throws  Exception {
        return cfgRelatMapper.selectByPrimaryKey(cfgRelatId);
    }


    /**
    * 通过MAP参数查询数据关系
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgRelat> getCfgRelatListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询数据关系
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgRelat> getCfgRelatListByExample(CfgRelat_Example example) throws  Exception {
        return cfgRelatMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询数据关系数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgRelatCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询数据关系数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgRelatCountByExample(CfgRelat_Example example) throws  Exception {
        return cfgRelatMapper.countByExample(example);
    }


  	//getter
 	
	public CfgRelatMapper getCfgRelatMapper(){
		return cfgRelatMapper;
	}
	//setter
	public void setCfgRelatMapper(CfgRelatMapper cfgRelatMapper){
    	this.cfgRelatMapper = cfgRelatMapper;
    }
}
