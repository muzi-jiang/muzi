package com.muzi.database01.flowable.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.muzi.communal.baseentity.DefaultIntegerEntity;

import java.util.Date;

@TableName("wf_hi_task")
public class HiTask extends DefaultIntegerEntity {
    private String actProcdefId;
    private String actProcdefKey;
    private String actId;
    private String actName;
    private String actType;
    private String procInstId;
    private String sourceTaskId;
    private String sourceTaskKey;
    private String sourceTaskName;
    private String taskId;
    private Integer completedUserId;
    private Date completedTime;
    private String completedUserName;
    @TableField("remark_")
    private String remark;
    private String sequenceFlowId;
    private String sequenceFlowName;
    private String businessKey;

    public HiTask() {
    }

    public String getActProcdefId() {
        return this.actProcdefId;
    }

    public void setActProcdefId(String actProcdefId) {
        this.actProcdefId = actProcdefId;
    }

    public String getActProcdefKey() {
        return this.actProcdefKey;
    }

    public void setActProcdefKey(String actProcdefKey) {
        this.actProcdefKey = actProcdefKey;
    }

    public String getActId() {
        return this.actId;
    }

    public void setActId(String actId) {
        this.actId = actId;
    }

    public String getActName() {
        return this.actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getActType() {
        return this.actType;
    }

    public void setActType(String actType) {
        this.actType = actType;
    }

    public String getProcInstId() {
        return this.procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getCompletedUserId() {
        return this.completedUserId;
    }

    public void setCompletedUserId(Integer completedUserId) {
        this.completedUserId = completedUserId;
    }

    public Date getCompletedTime() {
        return this.completedTime;
    }

    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }

    public String getCompletedUserName() {
        return this.completedUserName;
    }

    public void setCompletedUserName(String completedUserName) {
        this.completedUserName = completedUserName;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark_(String remark) {
        this.remark = remark;
    }

    public String getSequenceFlowId() {
        return this.sequenceFlowId;
    }

    public void setSequenceFlowId(String sequenceFlowId) {
        this.sequenceFlowId = sequenceFlowId;
    }

    public String getSequenceFlowName() {
        return this.sequenceFlowName;
    }

    public void setSequenceFlowName(String sequenceFlowName) {
        this.sequenceFlowName = sequenceFlowName;
    }

    public String getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getSourceTaskId() {
        return this.sourceTaskId;
    }

    public void setSourceTaskId(String sourceTaskId) {
        this.sourceTaskId = sourceTaskId;
    }

    public String getSourceTaskKey() {
        return this.sourceTaskKey;
    }

    public void setSourceTaskKey(String sourceTaskKey) {
        this.sourceTaskKey = sourceTaskKey;
    }

    public String getSourceTaskName() {
        return this.sourceTaskName;
    }

    public void setSourceTaskName(String sourceTaskName) {
        this.sourceTaskName = sourceTaskName;
    }
}