package com.muzi.database02.system.webmagic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.muzi.database02.system.webmagic.entity.Title;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author libin
 * @since 2019-12-20
 */
public interface TitleService extends IService<Title> {

    List<Title> selectAll();
}
