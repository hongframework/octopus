package com.hframework.graphdb.cfg.dao;

import com.hframework.graphdb.cfg.domain.model.CfgDataset;
import com.hframework.graphdb.cfg.domain.model.CfgDataset_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgDatasetMapper {
    int countByExample(CfgDataset_Example example);

    int deleteByExample(CfgDataset_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(CfgDataset record);

    int insertSelective(CfgDataset record);

    List<CfgDataset> selectByExampleWithBLOBs(CfgDataset_Example example);

    List<CfgDataset> selectByExample(CfgDataset_Example example);

    CfgDataset selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CfgDataset record, @Param("example") CfgDataset_Example example);

    int updateByExampleWithBLOBs(@Param("record") CfgDataset record, @Param("example") CfgDataset_Example example);

    int updateByExample(@Param("record") CfgDataset record, @Param("example") CfgDataset_Example example);

    int updateByPrimaryKeySelective(CfgDataset record);

    int updateByPrimaryKeyWithBLOBs(CfgDataset record);

    int updateByPrimaryKey(CfgDataset record);
}