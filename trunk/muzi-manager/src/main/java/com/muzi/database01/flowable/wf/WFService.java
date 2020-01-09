package com.muzi.database01.flowable.wf;

import com.muzi.database01.flowable.entity.WFBase;

import java.util.List;
import java.util.Map;

/**
 * 工作流相关service
 */
public interface WFService {

    /**
     * 完成当前节点并更新remark
     *
     * @param taskId         任务id
     * @param model          模型对象
     * @param completeUserId 执行人id
     * @param comment        remark
     */
    void complete(String taskId, WFBase model, String completeUserId,String comment);


    /**
     * 完成当前节点并更新remark
     *
     * @param taskId         任务id
     * @param model          模型对象
     * @param map            map
     * @param forceComplete  是否强制完成节点
     * @param completeUserId 执行人id
     * @param comment        remark
     */
    void complete(String taskId, WFBase model, Map<String, Object> map, boolean forceComplete, String completeUserId, String comment);



    /**
     * 完成当前节点并更新remark
     *
     * @param taskId         任务id
     * @param model          模型对象
     * @param map            map
     * @param forceComplete  是否强制完成节点
     * @param completeUserId 执行人id
     * @param comment        remark
     * @param limitRole      是否根据角色层级设置待处理人
     */
    void complete(String taskId, WFBase model, Map<String, Object> map, boolean forceComplete, String completeUserId,String comment, boolean limitRole);


    /**
     * 更新act_hi_taskinst表中assignee_字段
     * @param uerId 用户id
     * @param taskId 任务id
     * @param procInstId 实例id
     */
    void updateHiTaskintAssignee(String uerId, String taskId, String procInstId);


    /**
     * 更新remark
     *
     * @param proc_id 流程id
     * @param taskId  任务id
     * @param remark  remark
     */
    void updateRemark(String proc_id,String taskId,String remark);


    /**
     * 通过流程id查询任务id
     * @param procInstId 流程实例id
     * @return 任务id
     */
    String getTaskIdByProc(String procInstId);


    /**
     * 获取最新的流程分配人员
     * @param procId 流程id
     * @return 人员
     */
    List<String> getProcUser(String procId);
}
