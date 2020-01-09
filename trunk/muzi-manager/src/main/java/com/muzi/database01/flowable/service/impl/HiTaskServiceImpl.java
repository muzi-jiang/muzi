package com.muzi.database01.flowable.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muzi.database01.flowable.entity.HiTask;
import com.muzi.database01.flowable.mapper.HiTaskMapper;
import com.muzi.database01.flowable.service.HiTaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class HiTaskServiceImpl extends ServiceImpl<HiTaskMapper, HiTask> implements HiTaskService {
}
