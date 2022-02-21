package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.entity.Category;
import com.zerobase.fastlms.admin.mapper.BannerMapper;
import com.zerobase.fastlms.admin.mapper.CategoryMapper;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.BannerParam;
import com.zerobase.fastlms.admin.model.CategoryInput;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import com.zerobase.fastlms.admin.repository.CategoryRepository;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;
    private final BannerMapper bannerMapper;

    private Sort getSortBySortValueDesc() {
        return Sort.by(Sort.Direction.DESC, "sortValue");
    }

    @Override
    public List<BannerDto> list(BannerParam parameter) {

        long totalCount = bannerMapper.selectListCount(parameter);
        List<BannerDto> list = bannerMapper.selectList(parameter);
        if(!CollectionUtils.isEmpty(list)){
            int i = 0;
            for(BannerDto x : list){
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }
        return list;

    }

    @Override
    public boolean add(BannerInput parameter) {

        Banner banner = Banner.builder()
                .bannerName(parameter.getBannerName())
                .filename(parameter.getFilename())
                .urlFilename(parameter.getUrlFilename())
                .alterText(parameter.getAlterText())
                .url(parameter.getUrl())
                .clickTarget(parameter.getClickTarget())
                .sortValue(parameter.getSortValue())
                .frontYn(parameter.isFrontYn())
                .regDt(LocalDateTime.now())
                .build();
        bannerRepository.save(banner);

        return true;
    }

    @Override
    public boolean set(BannerInput parameter) {

        Optional<Banner> bannerOptional = bannerRepository.findById(parameter.getId());

        if(!bannerOptional.isPresent()){
            return false;
        }

        Banner banners = bannerOptional.get();

        banners.setBannerName(parameter.getBannerName());
        banners.setUrl(parameter.getUrl());
        banners.setClickTarget(parameter.getClickTarget());
        banners.setSortValue(parameter.getSortValue());
        banners.setFrontYn(parameter.isFrontYn());
        banners.setUptDt(LocalDateTime.now());
        banners.setFilename(parameter.getFilename());
        banners.setUrlFilename(parameter.getUrlFilename());
        banners.setUptDt(LocalDateTime.now());
        bannerRepository.save(banners);

        return true;
    }

    @Override
    public boolean del(String idList) {
        if(idList != null && idList.length() > 0){
            String[] ids = idList.split(",");
            for(String x : ids){
                long id = 0L;
                try{
                    id = Long.parseLong(x);
                } catch(Exception e){

                }

                if(id > 0) {
                    bannerRepository.deleteById(id);
                }
            }
        }
        return true;
    }

    @Override
    public BannerDto getById(long id) {
        return bannerRepository.findById(id).map(BannerDto::of).orElse(null);
    }
}
