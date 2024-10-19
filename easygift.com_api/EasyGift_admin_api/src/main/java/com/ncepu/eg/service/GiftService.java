package com.ncepu.eg.service;

import com.ncepu.eg.pojo.GiftInfo;
import com.ncepu.eg.pojo.GiftVO;
import com.ncepu.eg.pojo.PageBean;

import java.util.List;

public interface GiftService {

    PageBean<GiftVO> list(Integer pageNum, Integer pageSize, Integer categoryId, Integer state);

    PageBean<GiftInfo> listDetail(Integer pageNum, Integer pageSize);

    List<GiftVO> listAll();

    GiftVO getOne(Integer id);

    void changeState(Integer id,Integer state);

    void delete(Integer id);
}
