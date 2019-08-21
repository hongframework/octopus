package com.hframework.graphdb.cfg.dao;

import com.hframework.graphdb.cfg.domain.model.CfgRelat;
import com.hframework.graphdb.cfg.domain.model.CfgRelat_Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CfgRelatMapper {
    int countByExample(CfgRelat_Example example);

    int deleteByExample(CfgRelat_Example example);

    int deleteByPrimaryKey(Long id);

    int insert(CfgRelat record);

    int insertSelective(CfgRelat record);

    List<CfgRelat> selectByExample(CfgRelat_Example example);

    CfgRelat selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CfgRelat record, @Param("example") CfgRelat_Example example);

    int updateByExample(@Param("record") CfgRelat record, @Param("example") CfgRelat_Example example);

    int updateByPrimaryKeySelective(CfgRelat record);

    int updateByPrimaryKey(CfgRelat record);
}