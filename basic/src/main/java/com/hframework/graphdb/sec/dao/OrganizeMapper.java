package com.hframework.graphdb.sec.dao;

import com.hframework.graphdb.sec.domain.model.Organize;
import com.hframework.graphdb.sec.domain.model.Organize_Example;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizeMapper {
    int countByExample(Organize_Example example);

    int deleteByExample(Organize_Example example);

    int deleteByPrimaryKey(Long organizeId);

    int insert(Organize record);

    int insertSelective(Organize record);

    List<Organize> selectByExample(Organize_Example example);

    Organize selectByPrimaryKey(Long organizeId);

    int updateByExampleSelective(@Param("record") Organize record, @Param("example") Organize_Example example);

    int updateByExample(@Param("record") Organize record, @Param("example") Organize_Example example);

    int updateByPrimaryKeySelective(Organize record);

    int updateByPrimaryKey(Organize record);
}