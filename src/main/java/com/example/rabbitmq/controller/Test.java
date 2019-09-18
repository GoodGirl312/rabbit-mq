package com.example.rabbitmq.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ys
 * @Description
 * @Date 2019/9/17 18:03
 */
public class Test {

    public static void main(String[] args) {

        List<List<String>> rows = new ArrayList<>();

        for (int i = 0; i <500000 ; i++) {
            rows.add(CollUtil.newArrayList(i*(1111111111) + ""));
        }

        BigExcelWriter writer= ExcelUtil.getBigWriter("e:/aa.xlsx");
        // 一次性写出内容，使用默认样式
        writer.write(rows);
        // 关闭writer，释放内存
        writer.close();
    }
}
