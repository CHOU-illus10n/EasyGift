package com.ncepu.eg.service;


import com.ncepu.eg.pojo.Admin;

public interface AdminService {
    Admin findByAdminAccount(String adminUserAccount);

    void updateAvatar(Admin admin);

    void update(Admin admin);
}
