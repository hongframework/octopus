package com.hframework.graphdb.cfg.service.impl;

import java.util.*;

import com.hframework.graphdb.cfg.dao.CfgDatasetMapper;
import com.hframework.graphdb.cfg.domain.model.CfgDataset;
import com.hframework.graphdb.cfg.domain.model.CfgDataset_Example;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.hframework.graphdb.cfg.service.interfaces.ICfgDatasetSV;

@Service("iCfgDatasetSV")
public class CfgDatasetSVImpl  implements ICfgDatasetSV {

	@Resource
	private CfgDatasetMapper cfgDatasetMapper;
  


    /**
    * 创建数据集
    * @param cfgDataset
    * @return
    * @throws Exception
    */
    public int create(CfgDataset cfgDataset) throws Exception {
        return cfgDatasetMapper.insertSelective(cfgDataset);
    }

    /**
    * 批量维护数据集
    * @param cfgDatasets
    * @return
    * @throws Exception
    */
    public int batchOperate(CfgDataset[] cfgDatasets) throws  Exception{
        int result = 0;
        if(cfgDatasets != null) {
            for (CfgDataset cfgDataset : cfgDatasets) {
                if(cfgDataset.getId() == null) {
                    result += this.create(cfgDataset);
                }else {
                    result += this.update(cfgDataset);
                }
            }
        }
        return result;
    }

    /**
    * 更新数据集
    * @param cfgDataset
    * @return
    * @throws Exception
    */
    public int update(CfgDataset cfgDataset) throws  Exception {
        return cfgDatasetMapper.updateByPrimaryKeySelective(cfgDataset);
    }

    /**
    * 通过查询对象更新数据集
    * @param cfgDataset
    * @param example
    * @return
    * @throws Exception
    */
    public int updateByExample(CfgDataset cfgDataset, CfgDataset_Example example) throws  Exception {
        return cfgDatasetMapper.updateByExampleSelective(cfgDataset, example);
    }

    /**
    * 删除数据集
    * @param cfgDataset
    * @return
    * @throws Exception
    */
    public int delete(CfgDataset cfgDataset) throws  Exception {
        return cfgDatasetMapper.deleteByPrimaryKey(cfgDataset.getId());
    }

    /**
    * 删除数据集
    * @param cfgDatasetId
    * @return
    * @throws Exception
    */
    public int delete(long cfgDatasetId) throws  Exception {
        return cfgDatasetMapper.deleteByPrimaryKey(cfgDatasetId);
    }

    /**
    * 查找所有数据集
    * @return
    */
    public List<CfgDataset> getCfgDatasetAll()  throws  Exception {
        return cfgDatasetMapper.selectByExampleWithBLOBs(new CfgDataset_Example());
    }

    /**
    * 通过数据集ID查询数据集
    * @param cfgDatasetId
    * @return
    * @throws Exception
    */
    public CfgDataset getCfgDatasetByPK(long cfgDatasetId)  throws  Exception {
        return cfgDatasetMapper.selectByPrimaryKey(cfgDatasetId);
    }


    /**
    * 通过MAP参数查询数据集
    * @param params
    * @return
    * @throws Exception
    */
    public List<CfgDataset> getCfgDatasetListByParam(Map<String, Object> params)  throws  Exception {
        return null;
    }



    /**
    * 通过查询对象查询数据集
    * @param example
    * @return
    * @throws Exception
    */
    public List<CfgDataset> getCfgDatasetListByExample(CfgDataset_Example example) throws  Exception {
        return cfgDatasetMapper.selectByExampleWithBLOBs(example);
    }

    /**
    * 通过MAP参数查询数据集数量
    * @param params
    * @return
    * @throws Exception
    */
    public int getCfgDatasetCountByParam(Map<String, Object> params)  throws  Exception {
        return 0;
    }

    /**
    * 通过查询对象查询数据集数量
    * @param example
    * @return
    * @throws Exception
    */
    public int getCfgDatasetCountByExample(CfgDataset_Example example) throws  Exception {
        return cfgDatasetMapper.countByExample(example);
    }


  	//getter
 	
	public CfgDatasetMapper getCfgDatasetMapper(){
		return cfgDatasetMapper;
	}
	//setter
	public void setCfgDatasetMapper(CfgDatasetMapper cfgDatasetMapper){
    	this.cfgDatasetMapper = cfgDatasetMapper;
    }
}
