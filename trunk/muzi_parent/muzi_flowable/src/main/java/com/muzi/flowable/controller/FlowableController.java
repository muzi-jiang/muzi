package com.muzi.flowable.controller;

import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.common.impl.identity.Authentication;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: libin
 * @Date: 2020/12/03 10:46
 */
@Slf4j
@RestController
@RequestMapping("flowable")
public class FlowableController {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private ProcessEngine processEngine;

    @Resource
    private HistoryService historyService;

    /**
     * 创建流程
     *
     * @param userId 用户id
     * @param days 请假天数
     * @param reason 请假原因
     * @param key 流程key
     * @return 创建结果
     */
    @GetMapping("add")
    public String addExpense(String userId, String days, String reason,String key) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("employee", userId);
        map.put("nrOfHolidays", days);
        map.put("description", reason);


        runtimeService.startProcessInstanceByKey(key, "", map);
        //设置流程发起人
        Authentication.setAuthenticatedUserId(userId);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, map);
        //这个方法最终使用一个ThreadLocal类型的变量进行存储，也就是与当前的线程绑定，所以流程实例启动完毕之后，需要设置为null，防止多线程的时候出问题。
        Authentication.setAuthenticatedUserId(null);

        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        taskService.complete(task.getId(), map);
        return "提交成功,流程ID为：" + processInstance.getId();
    }


    @GetMapping("getInitiateUserId")
    public String getInitiateUserId(String procInsId) {
        HistoricProcessInstance hi = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(procInsId)
                .singleResult();
        return hi.getStartUserId();
    }


    /**
     * 获取指定用户组流程任务列表
     *
     * @param group 用户组
     * @return 查询列表
     */
    @GetMapping("list")
    public Object list(String group) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(group).list();
        return tasks.toString();
    }

    /**
     * 通过/拒绝任务
     *
     * @param taskId 任务id
     * @param approved 1 ：true  2：false
     * @return 结果
     */
    @GetMapping("apply")
    public String apply(String taskId, String approved) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return "流程不存在";
        }
        Map<String, Object> variables = new HashMap<>();
        Boolean apply = "1".equals(approved);
        variables.put("approved", apply);
        taskService.complete(taskId, variables);
        return "审批是否通过：" + approved;
    }

    /**
     * 查看历史流程记录
     *
     * @param processInstanceId 流程实例id
     * @return 查询结果
     */
    @GetMapping("historyList")
    public Object getHistoryList(String processInstanceId) {
        return historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).finished().orderByHistoricActivityInstanceEndTime().asc().list();
    }

    /**
     * 驳回流程实例
     *
     * @param taskId 任务id
     * @param targetTaskKey Key
     * @return 驳回结果
     */
    @GetMapping("rollbask")
    public String rollbaskTask(String taskId, String targetTaskKey) {
        Task currentTask = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (currentTask == null) {
            return "节点不存在";
        }
        List<String> key = new ArrayList<>();
        key.add(currentTask.getTaskDefinitionKey());


        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(currentTask.getProcessInstanceId())
                .moveActivityIdsToSingleActivityId(key, targetTaskKey)
                .changeState();
        return "驳回成功...";
    }


    /**
     * 终止流程实例
     *
     * @param processInstanceId 流程实例id
     */
    public String deleteProcessInstanceById(String processInstanceId) {
        // ""这个参数本来可以写删除原因
        runtimeService.deleteProcessInstance(processInstanceId, "");
        return "终止流程实例成功";
    }


    /**
     * 挂起流程实例
     *
     * @param processInstanceId 当前流程实例id
     */
    @GetMapping("hangUp")
    public String handUpProcessInstance(String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
        return "挂起流程成功...";
    }

    /**
     * 恢复（唤醒）被挂起的流程实例
     *
     * @param processInstanceId 流程实例id
     */
    @GetMapping("recovery")
    public String activateProcessInstance(String processInstanceId) {
        runtimeService.activateProcessInstanceById(processInstanceId);
        return "恢复流程成功...";
    }


    /**
     * 判断传入流程实例在运行中是否存在
     *
     * @param processInstanceId 流程实例id
     * @return Boolean
     */
    @GetMapping("isExist/running")
    public Boolean isExistProcIntRunning(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) {
            return false;
        }
        return true;
    }

    /**
     * 判断流程实例在历史记录中是否存在
     * @param processInstanceId 流程实例id
     * @return Boolean
     */
    @GetMapping("isExist/history")
    public Boolean isExistProcInHistory(String processInstanceId) {
        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (historicProcessInstance == null) {
            return false;
        }
        return true;
    }


    /**
     * 我发起的流程实例列表
     *
     * @param userId
     * @return 流程实例列表
     */
    @GetMapping("myTasks")
    public List<HistoricProcessInstance> getMyStartProcint(String userId) {
        return historyService
                .createHistoricProcessInstanceQuery()
                .startedBy(userId)
                .orderByProcessInstanceStartTime()
                .asc()
                .list();
    }


    /**
     * 查询流程图
     *
     * @param httpServletResponse 响应
     * @param processId 流程id
     * @throws Exception 异常信息
     */
    @RequestMapping(value = "processDiagram")
    public void genProcessDiagram(HttpServletResponse httpServletResponse, String processId) throws Exception {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(processId).singleResult();

        //流程走完的不显示图
        if (pi == null) {
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();

        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel, "png", activityIds, flows,
                engconf.getActivityFontName(),
                engconf.getLabelFontName(),
                engconf.getAnnotationFontName(),
                engconf.getClassLoader(), 1.0);
        OutputStream out = null;
        byte[] buf = new byte[1024];
        int legth = 0;
        try {
            out = httpServletResponse.getOutputStream();
            while ((legth = in.read(buf)) != -1) {
                out.write(buf, 0, legth);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }



}
