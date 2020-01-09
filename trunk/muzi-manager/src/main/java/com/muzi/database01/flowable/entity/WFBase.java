package com.muzi.database01.flowable.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.muzi.communal.baseentity.DefaultIntegerEntity;

import java.util.ArrayList;
import java.util.List;

public class WFBase extends DefaultIntegerEntity {

    private String procInstId;
    private String wfStatus;
    @TableField(
            exist = false
    )
    private String procDefId;
    @TableField(
            exist = false
    )
    private String procDefKey;
    @TableField(
            exist = false
    )
    private String taskDefName;
    @TableField(
            exist = false
    )
    private String taskDefKey;
    @TableField(
            exist = false
    )
    private String taskId;
    @TableField(
            exist = false
    )
    private List<String> candidateUsers = new ArrayList();
    @TableField(
            exist = false
    )
    private String wfReason;

    public WFBase() {
    }

    public String getWfReason() {
        return this.wfReason;
    }

    public void setWfReason(String wfReason) {
        this.wfReason = wfReason;
    }

    public String getProcInstId() {
        return this.procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getWfStatus() {
        return this.wfStatus;
    }

    public void setWfStatus(String wfStatus) {
        this.wfStatus = wfStatus;
    }

    public String getProcDefId() {
        return this.procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getTaskDefName() {
        return this.taskDefName;
    }

    public void setTaskDefName(String taskDefName) {
        this.taskDefName = taskDefName;
    }

    public String getTaskDefKey() {
        return this.taskDefKey;
    }

    public void setTaskDefKey(String taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcDefKey() {
        return this.procDefKey;
    }

    public void setProcDefKey(String procDefKey) {
        this.procDefKey = procDefKey;
    }

    public List<String> getCandidateUsers() {
        return this.candidateUsers;
    }

    public void setCandidateUsers(List<String> candidateUsers) {
        this.candidateUsers = candidateUsers;
    }



}
