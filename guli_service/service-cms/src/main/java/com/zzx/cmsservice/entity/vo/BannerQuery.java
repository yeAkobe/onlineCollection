package com.zzx.cmsservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BannerQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    //幻灯片名字
    private String name;

    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    private String end;
}

