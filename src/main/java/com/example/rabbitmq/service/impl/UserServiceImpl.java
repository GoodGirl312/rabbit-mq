package com.example.rabbitmq.service.impl;

import com.example.rabbitmq.entity.User;
import com.example.rabbitmq.mapper.UserMapper;
import com.example.rabbitmq.service.IUserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ys
 * @since 2019-08-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
