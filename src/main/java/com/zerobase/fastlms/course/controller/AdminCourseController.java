package com.zerobase.fastlms.course.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class AdminCourseController {

    @GetMapping("/admin/course/list.do")
    public String list(Model model){


        return "admin/course/list";
    }


}
