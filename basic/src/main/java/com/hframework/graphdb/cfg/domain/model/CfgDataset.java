package com.hframework.graphdb.cfg.domain.model;

import java.util.Date;

public class CfgDataset {
    private Long id;

    private String tableName;

    private Byte status;

    private Long creatorId;

    private Date createTime;

    private Long modifierId;

    private Date modifyTime;

    private Long dbId;

    private String qListXml;

    private String qCondXml;

    private String eCreateXml;

    private String eUpdateXml;
    private String tableDesc;

    public CfgDataset(Long id, String tableName, Byte status, Long creatorId, Date createTime,
                      Long modifierId, Date modifyTime, Long dbId, String tableDesc) {
        this.id = id;
        this.tableName = tableName;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.dbId = dbId;
        this.tableDesc = tableDesc;
    }

    public CfgDataset(Long id, String tableName, Byte status, Long creatorId, Date createTime,
                      Long modifierId, Date modifyTime, Long dbId, String tableDesc, String qListXml, String qCondXml, String eCreateXml, String eUpdateXml) {
        this.id = id;
        this.tableName = tableName;
        this.status = status;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = modifierId;
        this.modifyTime = modifyTime;
        this.dbId = dbId;
        this.qListXml = qListXml;
        this.qCondXml = qCondXml;
        this.eCreateXml = eCreateXml;
        this.eUpdateXml = eUpdateXml;
        this.tableDesc = tableDesc;
    }


    public Long getId() {
        return id;
    }

    public String getTableName() {
        return tableName;
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

    public Long getDbId() {
        return dbId;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public void setTableName(String tableName) {
        this.tableName=tableName;
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

    public void setDbId(Long dbId) {
        this.dbId=dbId;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc=tableDesc;
    }

    public CfgDataset() {
        super();
    }

    public String getqListXml() {
        return qListXml;
    }

    public String getqCondXml() {
        return qCondXml;
    }

    public String geteCreateXml() {
        return eCreateXml;
    }

    public String geteUpdateXml() {
        return eUpdateXml;
    }

    public void setqListXml(String qListXml) {
        this.qListXml = qListXml;
    }

    public void setqCondXml(String qCondXml) {
        this.qCondXml = qCondXml;
    }

    public void seteCreateXml(String eCreateXml) {
        this.eCreateXml = eCreateXml;
    }

    public void seteUpdateXml(String eUpdateXml) {
        this.eUpdateXml = eUpdateXml;
    }
}