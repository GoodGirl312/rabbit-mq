package com.example.rabbitmq.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ys
 * @since 2019-08-29
 */
@Data
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    @TableField("product_name")
    private String productName;
    @TableField("product_count")
    private Integer productCount;
    @TableField("left_count")
    private Integer leftCount;


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productCount=" + productCount +
                ", leftCount=" + leftCount +
                '}';
    }
}
