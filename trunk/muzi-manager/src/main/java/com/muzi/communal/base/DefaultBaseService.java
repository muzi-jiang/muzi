package com.muzi.communal.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.UpdateChainWrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface DefaultBaseService<T> extends IService<T> {

    @Override
    boolean save(T entity);

    @Override
    default boolean saveBatch(Collection<T> entityList) {
        return false;
    }

    @Override
    boolean saveBatch(Collection<T> entityList, int batchSize);

    @Override
    default boolean saveOrUpdateBatch(Collection<T> entityList) {
        return false;
    }

    @Override
    boolean saveOrUpdateBatch(Collection<T> entityList, int batchSize);

    @Override
    boolean removeById(Serializable id);

    @Override
    boolean removeByMap(Map<String, Object> columnMap);

    @Override
    boolean remove(Wrapper<T> queryWrapper);

    @Override
    boolean removeByIds(Collection<? extends Serializable> idList);

    @Override
    boolean updateById(T entity);

    @Override
    boolean update(T entity, Wrapper<T> updateWrapper);

    @Override
    default boolean update(Wrapper<T> updateWrapper) {
        return false;
    }

    @Override
    default boolean updateBatchById(Collection<T> entityList) {
        return false;
    }

    @Override
    boolean updateBatchById(Collection<T> entityList, int batchSize);

    @Override
    boolean saveOrUpdate(T entity);

    @Override
    T getById(Serializable id);

    @Override
    Collection<T> listByIds(Collection<? extends Serializable> idList);

    @Override
    Collection<T> listByMap(Map<String, Object> columnMap);

    @Override
    default T getOne(Wrapper<T> queryWrapper) {
        return null;
    }

    @Override
    T getOne(Wrapper<T> queryWrapper, boolean throwEx);

    @Override
    Map<String, Object> getMap(Wrapper<T> queryWrapper);

    @Override
    <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);

    @Override
    int count(Wrapper<T> queryWrapper);

    @Override
    default int count() {
        return 0;
    }

    @Override
    List<T> list(Wrapper<T> queryWrapper);

    @Override
    default List<T> list() {
        return null;
    }

    @Override
    IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);

    @Override
    default IPage<T> page(IPage<T> page) {
        return null;
    }

    @Override
    List<Map<String, Object>> listMaps(Wrapper<T> queryWrapper);

    @Override
    default List<Map<String, Object>> listMaps() {
        return null;
    }

    @Override
    default List<Object> listObjs() {
        return null;
    }

    @Override
    default <V> List<V> listObjs(Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    default List<Object> listObjs(Wrapper<T> queryWrapper) {
        return null;
    }

    @Override
    <V> List<V> listObjs(Wrapper<T> queryWrapper, Function<? super Object, V> mapper);

    @Override
    IPage<Map<String, Object>> pageMaps(IPage<T> page, Wrapper<T> queryWrapper);

    @Override
    default IPage<Map<String, Object>> pageMaps(IPage<T> page) {
        return null;
    }

    @Override
    BaseMapper<T> getBaseMapper();

    @Override
    default QueryChainWrapper<T> query() {
        return null;
    }

    @Override
    default LambdaQueryChainWrapper<T> lambdaQuery() {
        return null;
    }

    @Override
    default UpdateChainWrapper<T> update() {
        return null;
    }

    @Override
    default LambdaUpdateChainWrapper<T> lambdaUpdate() {
        return null;
    }
}
