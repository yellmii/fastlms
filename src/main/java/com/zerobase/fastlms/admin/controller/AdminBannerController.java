package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.BannerDto;
import com.zerobase.fastlms.admin.dto.CategoryDto;
import com.zerobase.fastlms.admin.model.BannerInput;
import com.zerobase.fastlms.admin.model.BannerParam;
import com.zerobase.fastlms.admin.model.CategoryInput;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.admin.service.CategoryService;
import com.zerobase.fastlms.course.controller.BaseController;
import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.model.CourseInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class AdminBannerController extends BaseController {

    private final BannerService bannerService;

    @GetMapping("/admin/banner/list.do")
    public String list(Model model, BannerParam parameter){

        parameter.init();

        List<BannerDto> bannerList = bannerService.list(parameter);

        long totalCount = 0;

        if(!CollectionUtils.isEmpty(bannerList)){
            totalCount = bannerList.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

        model.addAttribute("totalCount", totalCount);
        model.addAttribute("list", bannerList);
        model.addAttribute("pager", pagerHtml);


        return "admin/banner/list";
    }

    @GetMapping(value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String add(Model model, HttpServletRequest request, BannerInput parameter){

        //model.addAttribute("category", bannerService.list());

        boolean editMode = request.getRequestURI().contains("/edit.do");
        BannerDto detail = new BannerDto();

        if(editMode){
            long id = parameter.getId();
            BannerDto existBanner = bannerService.getById(id);
            if(existBanner == null) {
                model.addAttribute("message", "Not Course.");
                return "common/error";
            }
            detail = existBanner;
        }
        model.addAttribute("detail", detail);
        model.addAttribute("editMode", editMode);

        return "admin/banner/add";
    }

    private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFileName) {
        LocalDate now = LocalDate.now();

        String dirs[] = {
                String.format("%s/%d/", baseLocalPath, now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())
        };

        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        for(String dir : dirs) {
            File file = new File(dir);
            if(!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension ="";
        if(originalFileName != null){
            int dotPos = originalFileName.lastIndexOf(".");
            if(dotPos > -1) {
                fileExtension = originalFileName.substring(dotPos + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newFilename = String.format("%s%s", dirs[2], uuid);
        String newUrlFilename = String.format("%s%s", urlDir, uuid);
        if(fileExtension.length() > 0) {
            newFilename += "." + fileExtension;
            newUrlFilename += "." + fileExtension;
        }

        String[] returnFilename = {newFilename, newUrlFilename};
        return returnFilename;
    }

    @PostMapping (value = {"/admin/banner/add.do", "/admin/banner/edit.do"})
    public String addSubmit(Model model, HttpServletRequest request, MultipartFile file, BannerInput parameter){

        String saveFileName = "";
        String urlFileName = "";

        if(file != null) {
            String originalFileName = file.getOriginalFilename();

            String baseLocalPath = "C:\\Users\\81809\\IdeaProjects\\fastlms\\files";
            String baseUrlPath = "";

            String[] arrFileName = getNewSaveFile(baseLocalPath, baseUrlPath, originalFileName);

            saveFileName = arrFileName[0];
            urlFileName = arrFileName[1];

            try {
                File newFile = new File(saveFileName);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {

            }
        }

        parameter.setFilename(saveFileName);
        parameter.setUrlFilename(urlFileName);

        boolean editMode = request.getRequestURI().contains("/edit.do");
        if(editMode){
            long id = parameter.getId();
            BannerDto existBanner = bannerService.getById(id);
            if(existBanner == null) {
                model.addAttribute("message", "Not Course.");
                return "common/error";
            }

            boolean result = bannerService.set(parameter);

        } else{
            boolean result = bannerService.add(parameter);
        }

        return "redirect:/admin/banner/list.do";
    }

    @PostMapping ("/admin/banner/delete.do")
    public String del(Model model, HttpServletRequest request, BannerInput parameter){

        boolean result = bannerService.del(parameter.getIdList());

        return "redirect:/admin/banner/list.do";
    }



}
