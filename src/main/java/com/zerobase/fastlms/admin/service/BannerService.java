package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.BannerParam;
import com.zerobase.fastlms.admin.model.CategoryInput;
import com.zerobase.fastlms.course.model.CourseInput;

import java.util.List;

public interface BannerService {

    List<BannerDto> list(BannerParam parameter);

    boolean add(BannerInput parameter);

    boolean set(BannerInput parameter);

    boolean del(String idList);

    BannerDto getById(long id);
}
