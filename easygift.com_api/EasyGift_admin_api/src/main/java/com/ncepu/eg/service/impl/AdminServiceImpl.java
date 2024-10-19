package com.ncepu.eg.service.impl;


import com.ncepu.eg.mapper.AdminMapper;
import com.ncepu.eg.pojo.Admin;
import com.ncepu.eg.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zwy
 * @version 1.0
 * @description: TODO
 * @date 2023/12/12 18:35
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin findByAdminAccount(String adminUserAccount) {
        Admin a = adminMapper.findByAdminAccount(adminUserAccount);
        return a;
    }

    @Override
    public void updateAvatar(Admin admin) {
        adminMapper.updateAvatar(admin);
    }

    @Override
    public void update(Admin admin) {

        adminMapper.update(admin);
    }
}
