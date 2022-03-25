package com.zzx.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zzx.oss.service.ossService;
import com.zzx.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public
class ossServiceImpl implements ossService {
    @Override
    public
    String uploadFileAvatar(MultipartFile file) {
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtil.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。

        InputStream inputStream = null;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 获取上传文件的输入流
            inputStream = file.getInputStream();

            //获取文件名称
            String fileName = file.getOriginalFilename();

            //获取一个随机值
            String uid = UUID.randomUUID().toString().replaceAll("-", "");
            fileName = uid + fileName;

            //使上传的文件保存到年月日的文件夹中
            String dtime = new DateTime().toString("yyyy/MM/dd");
            fileName = dtime + "/" + fileName;

            //调用oss实例中的方法实现上传
            //参数1： Bucket名称
            //参数2： 上传到oss文件路径和文件名称 /aa/bb/1.jpg
            //参数3： 上传文件的输入流
            ossClient.putObject(bucketName, fileName, inputStream);

            //把上传后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            //https://achang-edu.oss-cn-hangzhou.aliyuncs.com/default.gif
            String url = "http://"+bucketName+"."+endpoint+"/"+fileName ;
            return url;

        } catch (Exception oe) {
           oe.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
