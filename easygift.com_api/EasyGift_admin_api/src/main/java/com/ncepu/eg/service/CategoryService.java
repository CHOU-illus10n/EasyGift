package com.ncepu.eg.service;

import com.ncepu.eg.pojo.Category;
import com.ncepu.eg.pojo.PageBean;

import java.util.List;

public interface CategoryService {
    //新增分类
    void add(Category category);

    //列表查询
    List<Category> list();

    //更新分类
    void update(Category category);

    //删除分类
    void deleteById(Integer id);

    PageBean<Category> listInfo(Integer pageNum,
                                Integer pageSize,
                                String categoryName);

    Category findById(Integer id);
}
