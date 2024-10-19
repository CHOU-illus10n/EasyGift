package com.ncepu.eg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ncepu.eg.mapper.GoodOrderMapper;
import com.ncepu.eg.pojo.GoodOrder;
import com.ncepu.eg.pojo.PageBean;
import com.ncepu.eg.service.GoodOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/15 10:14
 */
@Service
public class GoodOrderServiceImpl implements GoodOrderService {

    @Autowired
    private GoodOrderMapper goodOrderMapper;

    @Override
    public PageBean<GoodOrder> list(Integer pageNum, Integer pageSize, Integer orderId) {
        //1.创建PageBean对象
        PageBean<GoodOrder> pbi = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);

        List<GoodOrder> as = goodOrderMapper.list(orderId);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<GoodOrder> p = (Page<GoodOrder>) as;

        //把数据填充到PageBean对象中
        pbi.setTotal(p.getTotal());
        pbi.setItems(p.getResult());
        return pbi;
    }

    @Override
    public void send(GoodOrder goodOrder) {

        if(goodOrder.getState() == 4) {
            goodOrderMapper.send(goodOrder);
        }else if(goodOrder.getState() == -1){
            goodOrderMapper.cancel(goodOrder);
        }
    }
}
