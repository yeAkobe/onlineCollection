package com.zzx.vdo.entity;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantVodUtils implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String accessKeyId;

    @Value("${aliyun.vod.file.keysecret}")
    private String accessKeySecret;

    public static String ACCESSKEY_ID;
    public static String ACCESSKEY_SECRET;


    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESSKEY_ID = this.accessKeyId;
        ACCESSKEY_SECRET = this.accessKeySecret;
    }
}

