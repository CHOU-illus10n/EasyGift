package com.ncepu.eg.service.impl;

import com.ncepu.eg.mapper.GiftImgMapper;
import com.ncepu.eg.pojo.GiftImgVO;
import com.ncepu.eg.service.GiftImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/14 19:09
 */
@Service
public class GiftImgServiceImpl implements GiftImgService {

    @Autowired
    private GiftImgMapper giftImgMapper;

    @Override
    public List<GiftImgVO> list(Integer giftId) {
        return giftImgMapper.list(giftId);
    }
}
