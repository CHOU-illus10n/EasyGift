package com.ncepu.easygift.service;

import org.springframework.web.multipart.MultipartFile;


public interface ObsService {
    String upload(MultipartFile file);
}
