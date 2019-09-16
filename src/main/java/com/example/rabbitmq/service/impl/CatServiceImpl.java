package com.example.rabbitmq.service.impl;

import com.example.rabbitmq.entity.Cat;
import com.example.rabbitmq.mapper.CatMapper;
import com.example.rabbitmq.service.ICatService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ys
 * @since 2019-09-12
 */
@Service
public class CatServiceImpl extends ServiceImpl<CatMapper, Cat> implements ICatService {

}
