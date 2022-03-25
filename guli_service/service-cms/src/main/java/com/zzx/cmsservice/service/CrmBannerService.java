package com.zzx.cmsservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzx.cmsservice.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzx.cmsservice.entity.vo.BannerQuery;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author zzx
 * @since 2022-03-16
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> getAllBanner();

    void pageQuery(Page<CrmBanner> bannerPage, BannerQuery bannerQuery);
}
