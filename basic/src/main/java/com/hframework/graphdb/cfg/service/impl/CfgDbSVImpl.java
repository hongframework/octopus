package com.hframework.graphdb.cfg.service.impl;

import java.util.*;

import com.hframework.graphdb.cfg.dao.CfgDbMapper;
import com.hframework.graphdb.cfg.domain.model.CfgDb;
import com.hframework.graphdb.cfg.domain.model.CfgDb_Example;
import com.hframework.graphdb.cfg.service.interfaces.ICfgDbSV;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service("iCfgDbSV")
public class CfgDbSVImpl  implements ICfgDbSV {

	@Resource
	private CfgDbMapper cfgDbMapper;
  


    /**
    * 创建数据库
    * @param cfgDb
    * @return
    * @throws Exception
    */
    public int create(CfgDb cfgDb) throws Exception {
        return cfgDbMapper.insertSelective(cfgDb);
    }

    /**
    * 批量维护数据库
    * @param cfgDbs
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgDb[] cfgDbs) throws  Exception{
        int result = 0;
        if(cfgDbs != null) {
            for (CfgDb cfgDb : cfgDbs) {
                if(cfgDb.getId() == null) {
                    result += this.create(cfgDb);
                }else {
                    result += this.update(cfgDb);
                }
            }
        }
        return result;
    }

    /**
    * 更新数据库
    * @param cfgDb
    * @return
    * @throws Exception
    */
    public int update(CfgDb cfgDb) throws  Exception {
        return cfgDbMapper.updateByPrimaryKeySelective(cfgDb);
    }

    /**
    * 通过查询对象更新数据库
    * @param cfgDb
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgDb cfgDb, CfgDb_Example example) throws  Exception {
        return cfgDbMapper.updateByExampleSelective(cfgDb, example);
    }

    /**
    * 删除数据库
    * @param cfgDb
    * @return
    * @throws Exception
    */
    public int delete(CfgDb cfgDb) throws  Exception {
        return cfgDbMapper.deleteByPrimaryKey(cfgDb.getId());
    }

    /**
    * 删除数据库
    * @param cfgDbId
    * @return
    * @throws Exception
    */
    public int delete(long cfgDbId) throws  Exception {
        return cfgDbMapper.deleteByPrimaryKey(cfgDbId);
    }

    /**
    * 查找所有数据库
    * @return
    */
    public List<CfgDb> getCfgDbAll()  throws  Exception {
        return cfgDbMapper.selectByExample(new CfgDb_Example());
    }

    /**
    * 通过数据库ID查询数据库
    * @param cfgDbId
    * @return
    * @throws Exception
    */
    public CfgDb getCfgDbByPK(long cfgDbId)  throws  Exception {
        return cfgDbMapper.selectByPrimaryKey(cfgDbId);
    }


    /**
    * 通过MAP参数查询数据库
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgDb> getCfgDbListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询数据库
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgDb> getCfgDbListByExample(CfgDb_Example example) throws  Exception {
        return cfgDbMapper.selectByExample(example);
    }

    /**
    * 通过MAP参数查询数据库数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgDbCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询数据库数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgDbCountByExample(CfgDb_Example example) throws  Exception {
        return cfgDbMapper.countByExample(example);
    }


  	//getter
 	
	public CfgDbMapper getCfgDbMapper(){
		return cfgDbMapper;
	}
	//setter
	public void setCfgDbMapper(CfgDbMapper cfgDbMapper){
    	this.cfgDbMapper = cfgDbMapper;
    }
}
