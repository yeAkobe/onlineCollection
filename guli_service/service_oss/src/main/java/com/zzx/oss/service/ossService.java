package com.zzx.oss.service;

import org.springframework.web.multipart.MultipartFile;

public
interface ossService {
    String uploadFileAvatar(MultipartFile file);
}
