package com.ncepu.eg.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ncepu.eg.mapper.CategoryMapper;
import com.ncepu.eg.pojo.Category;
import com.ncepu.eg.pojo.PageBean;
import com.ncepu.eg.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {
        //补充属性值
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setIsDeleted(0);
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {
        return categoryMapper.list();
    }



    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryMapper.deleteById(id);
    }

    @Override
    public PageBean<Category> listInfo(Integer pageNum,
                                       Integer pageSize,
                                       String categoryName) {
        //1.创建PageBean对象
        PageBean<Category> pbi = new PageBean<>();

        //2.开启分页查询 PageHelper
        PageHelper.startPage(pageNum, pageSize);

//        Map<String,Object> map = ThreadLocalUtil.get();
//        Integer userId = (Integer) map.get("id");
        List<Category> as = categoryMapper.listInfo(categoryName);
        //Page中提供了方法,可以获取PageHelper分页查询后 得到的总记录条数和当前页数据
        Page<Category> p = (Page<Category>) as;

        //把数据填充到PageBean对象中
        pbi.setTotal(p.getTotal());
        pbi.setItems(p.getResult());
        return pbi;
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.findById(id);
    }
}
