package com.example.rabbitmq.service;

import com.example.rabbitmq.entity.Product;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ys
 * @since 2019-08-29
 */
public interface IProductService extends IService<Product> {

    void dealUserRobbing(String mobile) throws Exception;
}
