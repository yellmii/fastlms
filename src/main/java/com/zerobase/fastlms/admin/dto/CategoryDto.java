package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {

    Long id;
    String categoryName;
    int sortValue;
    boolean usingYn;

    public static List<CategoryDto> of(List<Category> categories){
        if(categories != null){
            List<CategoryDto> categoryDtos = new ArrayList<>();
            for(Category x : categories){
                categoryDtos.add(of(x));
            }
            return categoryDtos;
        }
        return null;
    }

    public static CategoryDto of(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .sortValue(category.getSortValue())
                .usingYn(category.isUsingYn())
                .build();
    }
}
