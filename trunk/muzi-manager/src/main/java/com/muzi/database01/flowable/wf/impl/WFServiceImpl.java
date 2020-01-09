package com.muzi.database01.flowable.wf.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.muzi.database01.flowable.entity.HiTask;
import com.muzi.database01.flowable.entity.WFBase;
import com.muzi.database01.flowable.mapper.WFMapper;
import com.muzi.database01.flowable.service.HiTaskService;
import com.muzi.database01.flowable.wf.WFService;
import com.muzi.database01.flowable.wf.WFTaskService;
import org.flowable.engine.impl.persistence.deploy.DeploymentManager;
import org.flowable.spring.ProcessEngineFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor=Exception.class)
public class WFServiceImpl implements WFService {

    private static final Logger LOG = LoggerFactory.getLogger(WFServiceImpl.class);

    @Autowired
    private ProcessEngineFactoryBean processEngineFactoryBean;

    @Autowired
    private HiTaskService hiTaskService;

    @Autowired
    private WFTaskService wfTaskService;

    @Autowired
    private WFMapper wfMapper;

    @Override
    public void complete(String taskId, WFBase model, String completeUserId, String comment) {
        this.complete(taskId, model, new HashMap<>(1),false, completeUserId, comment, true);
    }

    @Override
    public void complete(String taskId, WFBase model, Map<String, Object> map, boolean forceComplete, String completeUserId, String comment) {
        this.complete(taskId, model, map,forceComplete, completeUserId, comment, true);
    }

    @Override
    public void complete(String taskId, WFBase model, Map<String, Object> map, boolean forceComplete, String completeUserId, String comment, boolean limitRole) {
        LOG.debug("===================> complete");
        clearCache(taskId);

        /*调用基盘api推动流程*/
        wfTaskService.complete(taskId, model, map, forceComplete, completeUserId);

        this.updateHiTaskintAssignee(completeUserId, taskId, model.getProcInstId());

        // 更新remark，每个流程节点的通过审批意见
        this.updateRemark(model.getProcInstId(), taskId, comment);

        String taskIdByProc = this.getTaskIdByProc(model.getProcInstId());

        if (limitRole){
            // 获取本阶段审批的人
            //  从act_ru_identitylink表中删除本阶段审批的人 TYPE_ = 'candidate'
        }

        // 下一节点审批人，包含该节点审批人，跳过下一节点
        List<String> procUserIds = this.getProcUser(model.getProcInstId());
        boolean done = procUserIds.stream().anyMatch(p -> completeUserId.equals(p));
        if (done){
            this.complete(taskIdByProc, model, map, forceComplete, completeUserId, comment, true);
        }
    }
    /**
     * 强制清楚缓存 流程定义内容相关
     * @param taskId 任务id
     */
    public void clearCache(String taskId){

        String actProcdefId = hiTaskService.getOne(new QueryWrapper<HiTask>()
            .select("act_procdef_id")
            .eq("task_id", taskId)
            .last("limit 1")
        ).getActProcdefId();

        DeploymentManager deploymentManager = this.processEngineFactoryBean.getProcessEngineConfiguration().getDeploymentManager();
        deploymentManager.getProcessDefinitionCache().remove(actProcdefId);
        deploymentManager.getKnowledgeBaseCache().remove(actProcdefId);
        deploymentManager.getAppResourceCache().remove(actProcdefId);
        deploymentManager.getProcessDefinitionInfoCache().remove(actProcdefId);

    }

    @Override
    public void updateHiTaskintAssignee(String uerId, String taskId, String procInstId){
        wfMapper.updateHiTaskintAssignee(uerId, taskId, procInstId);
    }

    @Override
    public void updateRemark(String proc_id, String taskId, String remark) {
        LOG.debug("==============> updateRemark");
        wfMapper.updateRemark(proc_id, taskId, remark);
    }

    @Override
    public String getTaskIdByProc(String procInstId){
        return  this.wfMapper.getTaskIdByProc(procInstId);
    }

    @Override
    public List<String> getProcUser(String procId){
        return wfMapper.getProcUser(procId);
    }
}
