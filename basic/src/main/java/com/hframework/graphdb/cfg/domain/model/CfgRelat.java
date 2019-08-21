package com.hframework.graphdb.cfg.domain.model;

import java.util.Date;

public class CfgRelat {
    private Long id;

    private Byte type;

    private String sourceTable;

    private String targetTable;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private String description;

    private Long dbId;

    private String sourceField;

    private String targetField;

    public CfgRelat(Long id, Byte type, String sourceTable, String targetTable, Byte status, Long creatorId, Date createTime, Long modifierId, Date modifyTime, String description, Long dbId, String sourceField, String targetField) {
        this.id = id;
        this.type = type;
        this.sourceTable = sourceTable;
        this.targetTable = targetTable;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.description = description;
        this.dbId = dbId;
        this.sourceField = sourceField;
        this.targetField = targetField;
    }

    public Long getId() {
        return id;
    }

    public Byte getType() {
        return type;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public String getTargetTable() {
        return targetTable;
    }

    public Byte getStatus() {
        return status;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public String getDescription() {
        return description;
    }

    public Long getDbId() {
        return dbId;
    }

    public String getSourceField() {
        return sourceField;
    }

    public String getTargetField() {
        return targetField;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public void setType(Byte type) {
        this.type=type;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable=sourceTable;
    }

    public void setTargetTable(String targetTable) {
        this.targetTable=targetTable;
    }

    public void setStatus(Byte status) {
        this.status=status;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId=creatorId;
    }

    public void setCreateTime(Date createTime) {
        this.createTime=createTime;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId=modifierId;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime=modifyTime;
    }

    public void setDescription(String description) {
        this.description=description;
    }

    public void setDbId(Long dbId) {
        this.dbId=dbId;
    }

    public void setSourceField(String sourceField) {
        this.sourceField=sourceField;
    }

    public void setTargetField(String targetField) {
        this.targetField=targetField;
    }

    public CfgRelat() {
        super();
    }
}