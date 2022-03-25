package com.zzx.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//常量类，读取配置文件application.properties中的配置
@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyid;

    @Value("${aliyun.oss.file.keysecret}")
    private String keysecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;

    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

//
//    使用@Value读取application.properties里的配置内容
//
//    用spring的 InitializingBean 的 afterPropertiesSet 来初始化配置信息，这个方法将在所有的属性被初始化后调用。
    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID=this.keyid;
        KEY_SECRET=this.keysecret;
        END_POINT=this.endpoint;
        BUCKET_NAME=this.bucketname;
    }

}

