package com.muzi.communal.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DefaultBaseMapper<T> extends BaseMapper<T> {

    @Override
    int insert(T entity);

    @Override
    int deleteById(Serializable id);

    @Override
    int deleteByMap(Map<String, Object> columnMap);

    @Override
    int delete(Wrapper<T> wrapper);

    @Override
    int deleteBatchIds(Collection<? extends Serializable> idList);

    @Override
    int updateById(T entity);

    @Override
    int update(T entity, Wrapper<T> updateWrapper);

    @Override
    T selectById(Serializable id);

    @Override
    List<T> selectBatchIds(Collection<? extends Serializable> idList);

    @Override
    List<T> selectByMap(Map<String, Object> columnMap);

    @Override
    T selectOne(Wrapper<T> queryWrapper);

    @Override
    Integer selectCount(Wrapper<T> queryWrapper);

    @Override
    List<T> selectList(Wrapper<T> queryWrapper);

    @Override
    List<Map<String, Object>> selectMaps(Wrapper<T> queryWrapper);

    @Override
    List<Object> selectObjs(Wrapper<T> queryWrapper);

    @Override
    IPage<T> selectPage(IPage<T> page, Wrapper<T> queryWrapper);

    @Override
    IPage<Map<String, Object>> selectMapsPage(IPage<T> page, Wrapper<T> queryWrapper);
}
