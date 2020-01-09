package com.muzi.database01.flowable.wf;

import com.muzi.database01.flowable.entity.WFBase;

import java.util.Map;

public interface WFTaskService {

    void complete(String var1, WFBase var2, Map<String, Object> var3, boolean var4, String var5);
}
