package com.ncepu.eg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ncepu.eg.mapper.GiftMapper;
import com.ncepu.eg.pojo.GiftInfo;
import com.ncepu.eg.pojo.GiftVO;
import com.ncepu.eg.pojo.PageBean;
import com.ncepu.eg.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/13 10:18
 */
@Service
public class GiftServiceImpl implements GiftService {
    @Autowired
    private GiftMapper giftMapper;



    @Override
    public PageBean<GiftVO> list(Integer pageNum, Integer pageSize, Integer categoryId, Integer state) {
        //1.创建PageBean对象
        PageBean<GiftVO> pb = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);

//        Map<String,Object> map = ThreadLocalUtil.get();
//        Integer userId = (Integer) map.get("id");
        List<GiftVO> as = giftMapper.list(categoryId,state);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<GiftVO> p = (Page<GiftVO>) as;

        //把数据填充到PageBean对象中
        pb.setTotal(p.getTotal());
        pb.setItems(p.getResult());
        return pb;
    }

    @Override
    public PageBean<GiftInfo> listDetail(Integer pageNum, Integer pageSize) {
        //1.创建PageBean对象
        PageBean<GiftInfo> pbi = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum,pageSize);

//        Map<String,Object> map = ThreadLocalUtil.get();
//        Integer userId = (Integer) map.get("id");
        List<GiftInfo> as = giftMapper.listDetail();
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<GiftInfo> p = (Page<GiftInfo>) as;

        //把数据填充到PageBean对象中
        pbi.setTotal(p.getTotal());
        pbi.setItems(p.getResult());
        return pbi;
    }

    @Override
    public List<GiftVO> listAll() {
        return giftMapper.listAll();
    }

    @Override
    public GiftVO getOne(Integer id) {
        return giftMapper.getOne(id);
    }

    @Override
    public void changeState(Integer id,Integer state) {
        giftMapper.changeState(id,state);
    }

    @Override
    public void delete(Integer id) {
        giftMapper.delete(id);
    }


}
