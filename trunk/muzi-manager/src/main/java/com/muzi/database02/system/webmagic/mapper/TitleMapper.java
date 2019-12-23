package com.muzi.database02.system.webmagic.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.muzi.database02.system.webmagic.entity.Title;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author libin
 * @since 2019-12-20
 */
public interface TitleMapper extends BaseMapper<Title> {

    /*@Select(" select * from t_title")*/
    List<Title> selectAll();
}
