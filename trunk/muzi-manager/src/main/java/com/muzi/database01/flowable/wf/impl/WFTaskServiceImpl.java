package com.muzi.database01.flowable.wf.impl;

import com.muzi.database01.flowable.entity.WFBase;
import com.muzi.database01.flowable.wf.WFTaskService;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional
public class WFTaskServiceImpl implements WFTaskService {

    private static final Logger LOG = LoggerFactory.getLogger(WFTaskServiceImpl.class);

    @Autowired
    private TaskService taskService;

    @Override
    public void complete(String taskId, WFBase model, Map<String, Object> map, boolean forceComplete, String completeUserId) {
        if (LOG.isInfoEnabled()) {
            LOG.info("taskId:{}", taskId);
            LOG.info("model:{}", model.toString());
            LOG.info("map:{}", map);
        }

        if (model != null && model.getId() != null && !"0".equals(model.getId()) && !StringUtils.isBlank(model.getId().toString())) {
            Task task = null;
            if (StringUtils.isNotBlank(taskId)) {
                task = (Task)((TaskQuery)this.taskService.createTaskQuery().taskId(taskId)).singleResult();
            } else{
                List<Task> tasks = ((TaskQuery)this.taskService.createTaskQuery().processInstanceBusinessKey(model.getId().toString())).list();
                if (tasks == null || tasks.isEmpty()) {
                    throw new RuntimeException("任务不存在");
                }

                if (tasks.size() > 1) {
                    throw new RuntimeException("有多个任务");
                }

                task = (Task)tasks.get(0);
            }

            if (task == null) {
                LOG.error("没有查询到对应任务,taskId:{" + taskId + "},modelId:{" + model.getId() + "}");
                throw new RuntimeException("任务不存在");
            } else if (forceComplete && StringUtils.isNotBlank(task.getAssignee()) && !task.getAssignee().equals(completeUserId)) {
                LOG.error("任务已被其他人员签收，签收人员:" + task.getAssignee());
                throw new RuntimeException("任务被其他人签收或处理");
            } else {
                if (StringUtils.isNotBlank(task.getAssignee()) && !task.getAssignee().equals(completeUserId)) {
                    this.taskService.unclaim(task.getId());
                }
                if (StringUtils.isNotBlank(completeUserId)) {
                    map.put("completeUserId", completeUserId);
                    if (!completeUserId.equals(task.getAssignee())) {
                        this.taskService.claim(task.getId(), completeUserId);
                    }
                }
                map.put("model", model);
                //flowable 推动流程
                this.taskService.complete(task.getId(), map);
               // this.updateBusinessTable(model); (暂定不要)
            }
        }



    }

    /**
     * 更新 业务表中的审批状态 (暂时不需要)
     * @param model
     */
    private void updateBusinessTable(WFBase model) {
        Set<String> status = new HashSet();
        List<Task> tasks = ((TaskQuery)this.taskService.createTaskQuery().processInstanceId(model.getProcInstId())).list();
        if (tasks == null || tasks.isEmpty()) {
            status.add("流程结束");
        }

        tasks.forEach((task) -> {
            status.add(task.getName());
        });

//        Workflow workflow = this.workflowService.findByKey(model.getProcDefKey());
//        String sql = "update %s set wf_status = '%s' where id = %s";
//        sql = String.format(sql, workflow.getBusinessTable(), String.join(",", status), model.getId());
//        LOG.info("updateBusinessTable:{}", sql);
//        this.sqlMapper.execute(sql);
    }
}
