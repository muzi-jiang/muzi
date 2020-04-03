package com.muzi.system.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.muzi.system.entity.User;
import com.muzi.system.mapper.UserMapper;
import com.muzi.system.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author libin
 * @since 2019-12-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
