package com.example.rabbitmq.mapper;

import com.example.rabbitmq.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ys
 * @since 2019-08-29
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
