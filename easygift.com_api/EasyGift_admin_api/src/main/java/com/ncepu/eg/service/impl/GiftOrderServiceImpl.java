package com.ncepu.eg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ncepu.eg.mapper.GiftOrderMapper;
import com.ncepu.eg.pojo.GiftOrder;
import com.ncepu.eg.pojo.PageBean;
import com.ncepu.eg.service.GiftOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/14 1:06
 */
@Service
public class GiftOrderServiceImpl implements GiftOrderService {

    @Autowired
    private GiftOrderMapper giftOrderMapper;


    @Override
    public PageBean<GiftOrder> list(Integer pageNum, Integer pageSize) {
        //1.创建PageBean对象
        PageBean<GiftOrder> pbi = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);

//        Map<String,Object> map = ThreadLocalUtil.get();
//        Integer userId = (Integer) map.get("id");
        List<GiftOrder> as = giftOrderMapper.list();
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<GiftOrder> p = (Page<GiftOrder>) as;

        //把数据填充到PageBean对象中
        pbi.setTotal(p.getTotal());
        pbi.setItems(p.getResult());
        return pbi;
    }

    @Override
    public void delete(Integer orderId) {
        giftOrderMapper.delete(orderId);
    }
}
