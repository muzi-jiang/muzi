package com.muzi.database02.system.webmagic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muzi.database02.system.webmagic.entity.Title;
import com.muzi.database02.system.webmagic.mapper.TitleMapper;
import com.muzi.database02.system.webmagic.service.TitleService;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Title> selectAll() {
        return baseMapper.selectAll();
    }
}
