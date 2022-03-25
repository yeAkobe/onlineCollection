package com.zzx.eduservice.client;

import com.zzx.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component //教给spring管理
public class VodClientImpl implements VodClient {
    //出错之后会执行，兜底方法
    @Override
    public R removeAliyunVideoById(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R removeBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}

