package com.zzx.cmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzx.cmsservice.entity.CrmBanner;
import com.zzx.cmsservice.entity.vo.BannerQuery;
import com.zzx.cmsservice.mapper.CrmBannerMapper;
import com.zzx.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author zzx
 * @since 2022-03-16
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    //@Cacheable用于查询
    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    //查询所有banner
    public List<CrmBanner> getAllBanner() {
        List<CrmBanner> list = baseMapper.selectList(null);
        return list;
    }

    //多条件带分页查询
    @Override
    public void pageQuery(Page<CrmBanner> bannerPage, BannerQuery bannerQuery) {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();

        if (bannerQuery!=null){
            String name = bannerQuery.getName();
            String begin = bannerQuery.getBegin();
            String end = bannerQuery.getEnd();

            if (!StringUtils.isEmpty(name)){
                wrapper.like("title",name);
            }
            if (!StringUtils.isEmpty(begin)){
                wrapper.ge("gmt_create",begin);
            }
            if (!StringUtils.isEmpty(end)){
                wrapper.le("gmt_modified",end);
            }
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //带上门判断后的条件进行分页查询
        baseMapper.selectPage(bannerPage, wrapper);
    }
}
