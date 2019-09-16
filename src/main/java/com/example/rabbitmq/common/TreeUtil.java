package com.example.rabbitmq.common;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.rabbitmq.entity.Cat;
import com.example.rabbitmq.mapper.CatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ys
 * @Description  将无序的集合 变成有序的tree
 * @Date 2019/9/12 17:28
 */
@Component
public class TreeUtil {
    @Autowired
    CatMapper catMapper;

    /**
     * 方法一：
     * @param id
     * @return
     */
    public List<Cat> getTree(long id){
       List<Cat> nodes = catMapper.selectList(new EntityWrapper<Cat>().eq("parent_id",id));
       return dealNodes(nodes);
    }

    private List<Cat> dealNodes(List<Cat> nodes) {
        for (Cat node : nodes) {
            List<Cat> childNodes = catMapper.selectList(new EntityWrapper<Cat>().eq("parent_id",node.getId()));
            if(!CollectionUtils.isEmpty(childNodes)){
                node.setChildList(childNodes);
                dealNodes(childNodes);
            }else {
                break;
            }

        }
        return nodes;
    }

    /**
     * 方法二：
     * catList: 无序
     */
    public List<Cat> getTreeCat(List<Cat> catList,Integer id){
        List<Cat> collect = catList.stream().filter(item -> {
            return item.getParentId().equals(id);
        }).collect(Collectors.toList());

        dealChildNodes(collect,catList);
        return collect;
    }

    private void dealChildNodes(List<Cat> parents,List<Cat> all) {
        for (Cat cat : parents) {
            List<Cat> childList = all.stream().filter(item -> {
                return item.getParentId().equals(cat.getId());
            }).collect(Collectors.toList());

            if(!CollectionUtils.isEmpty(childList)){
                cat.setChildList(childList);
                dealChildNodes(childList,all);
            }else {
                break;
            }

        }
    }
}
