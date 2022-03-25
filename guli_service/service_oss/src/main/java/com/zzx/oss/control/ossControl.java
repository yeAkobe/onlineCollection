package com.zzx.oss.control;


import com.zzx.commonutils.R;
import com.zzx.oss.service.ossService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(description="阿里云文件管理")
@RestController
@RequestMapping("/edu_oss/fileoss")
//@CrossOrigin
public
class ossControl {
    @Autowired
    private ossService ossservice;
    @PostMapping("/upFile")
    public
    R UploadFile(MultipartFile file){
        String url = ossservice.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }
}
