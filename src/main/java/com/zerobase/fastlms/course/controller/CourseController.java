package com.zerobase.fastlms.course.controller;

import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import com.zerobase.fastlms.course.model.CourseParam;
import com.zerobase.fastlms.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CourseController extends BaseController{

    private final CourseService courseService;
    private final CategoryService categoryService;

    @GetMapping("/course")
    public String course(Model model, CourseParam parameter){

        List<CourseDto> list = courseService.frontList(parameter);
        model.addAttribute("list", list);

        List<CategoryDto> categoryDtoList =categoryService.frontList(CategoryDto.builder().build());
        model.addAttribute("categoryList", categoryDtoList);

        return "course/index";
    }

    @GetMapping("/course/{id}")
    public String courseDetail(Model model, CourseParam parameter){

        CourseDto courseDto = courseService.frontDetail(parameter.getId());
        model.addAttribute("detail", courseDto);

        return "course/detail";
    }


}
