package com.example.demo.service.impl;

import com.example.demo.entity.Title;
import com.example.demo.dao.TitleMapper;
import com.example.demo.service.TitleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author libin
 * @since 2019-12-20
 */
@Service
public class TitleServiceImpl extends ServiceImpl<TitleMapper, Title> implements TitleService {

}
