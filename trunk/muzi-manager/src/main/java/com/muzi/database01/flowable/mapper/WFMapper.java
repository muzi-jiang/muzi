package com.muzi.database01.flowable.mapper;

import java.util.List;

public interface WFMapper {

    void updateHiTaskintAssignee(String uerId, String taskId, String procInstId);

    void updateRemark(String proc_id,String taskId,String remark);

    String getTaskIdByProc(String procInstId);

    List<String> getProcUser(String procId);
}
