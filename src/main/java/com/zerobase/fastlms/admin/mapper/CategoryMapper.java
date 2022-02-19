package com.zerobase.fastlms.admin.mapper;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper { //Member라는 데이터베이스에 대해 쿼리를 실행시켜주는 인터페이스

    List<CategoryDto> select(CategoryDto parameter);

}
