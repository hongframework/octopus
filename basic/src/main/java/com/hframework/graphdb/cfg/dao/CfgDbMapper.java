package com.hframework.graphdb.cfg.dao;

import com.hframework.graphdb.cfg.domain.model.CfgDb;
import com.hframework.graphdb.cfg.domain.model.CfgDb_Example;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgDbMapper {
    int countByExample(CfgDb_Example example);

    int deleteByExample(CfgDb_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(CfgDb record);

    int insertSelective(CfgDb record);

    List<CfgDb> selectByExample(CfgDb_Example example);

    CfgDb selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CfgDb record, @Param("example") CfgDb_Example example);

    int updateByExample(@Param("record") CfgDb record, @Param("example") CfgDb_Example example);

    int updateByPrimaryKeySelective(CfgDb record);

    int updateByPrimaryKey(CfgDb record);
}