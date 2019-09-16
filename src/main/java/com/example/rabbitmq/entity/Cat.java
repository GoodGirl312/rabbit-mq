package com.example.rabbitmq.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author ys
 * @since 2019-09-12
 */
@Data
public class Cat implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 父节点
     */
    @TableField("parent_id")
    private Integer parentId;
    /**
     * 当前节点名字
     */
    private String name;

    @TableField(exist = false)
    private List<Cat> childList;

    @Override
    public String toString() {
        return "Cat{" +
        ", id=" + id +
        ", parentId=" + parentId +
        ", name=" + name +
        "}";
    }
}
