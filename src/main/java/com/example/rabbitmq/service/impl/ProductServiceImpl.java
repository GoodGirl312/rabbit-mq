package com.example.rabbitmq.service.impl;

import com.example.rabbitmq.entity.Product;
import com.example.rabbitmq.entity.User;
import com.example.rabbitmq.mapper.ProductMapper;
import com.example.rabbitmq.mapper.UserMapper;
import com.example.rabbitmq.service.IProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ys
 * @since 2019-08-29
 */
@Log4j2
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    private static int productId = 1;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ProductMapper productMapper;

    @Override
    public  void  dealUserRobbing(String mobile) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        synchronized (this){
            Product product = productMapper.selectById(productId);
            if(product.getLeftCount()>0){
                product.setLeftCount(product.getLeftCount() - 1);
                productMapper.updateById(product);
                userMapper.insert(new User(mobile,format.format(new Date()),1));
                log.info("用户抢单成功"+ mobile);
            }else{
                throw new Exception("抢单失败！您的手速太慢了！");
            }
        }
    }
}
