package com.example.rabbitmq.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String mobile;
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    @TableField("create_time")
    private String createTime;
    @TableField("product_count")
    private int productCount;

    public User(String mobile, String createTime, int productCount) {
        this.mobile = mobile;
        this.createTime = createTime;
        this.productCount = productCount;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", createTime='" + createTime + '\'' +
                ", productCount='" + productCount + '\'' +
                '}';
    }
}
