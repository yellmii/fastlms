package com.zerobase.fastlms.member.controller;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.course.service.TakeCourseService;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MemberController {

    //생성자가 주입형태를 통해서 객체가 생성될 수 있도록
    private final MemberService memberService;
    private final TakeCourseService takeCourseService;

    /*
    //@RequiredArgsConstructor 하면 생성자 안 만들어도 됨
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
    */

    @RequestMapping("/member/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/member/find/password")
    public String findPassword() {
        return "/member/find_password";
    }

    @PostMapping("/member/find/password")
    public String findPasswordSubmit(Model model, ResetPasswordInput resetPasswordInput) {

        boolean result = false;

        try{
            result = memberService.sendResetPassword(resetPasswordInput);
        } catch (Exception e){

        }

        model.addAttribute("result", result);
        return "member/find_password_result";
        //return "redirect:/"; //비밀번호 찾기 후에 메인페이지로 이동(주소까지 메인페이지로 이동하기 위해 index가 아닌 redirect:/ 사용)
    }

    //@RequestMapping(value = "/member/register", method = RequestMethod.GET)
    @GetMapping("/member/register")
    public String register() {
        return "member/register";
    }

    //데이터 받기
    //@RequestMapping(value = "/member/register", method = RequestMethod.POST)
    @PostMapping("/member/register")
    public String registerSubmit(Model model, HttpServletRequest request, MemberInput parameter) { //Model : client에게 데이터 내리기 위해 사용하는 인터페이스

        boolean result = memberService.register(parameter);
        model.addAttribute("result", result);
        return "member/register_complete";
    }

    @GetMapping("/member/email-auth")
    public String emailAuth(Model model, HttpServletRequest request){

        String uuid = request.getParameter("id");

        System.out.println(uuid);

        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result", result); // view에 전송하기 위해 model에 담기

        return "/member/email_auth";
    }

    @GetMapping("/member/info")
    public String memberInfo(Model model, Principal principal){

        String userId = principal.getName();

        MemberDto member = memberService.detail(userId);

        model.addAttribute("detail", member);

        return "member/info";
    }

    @PostMapping("/member/info")
    public String memberInfoSubmit(Model model, MemberInput parameter, Principal principal){

        String userId = principal.getName();
        parameter.setUserId(userId);

        ServiceResult result = memberService.updateMember(parameter);

        return "redirect:/member/info";
    }

    @GetMapping("/member/password")
    public String memberPassword(Model model, Principal principal){

        String userId = principal.getName();
        MemberDto member = memberService.detail(userId);

        model.addAttribute("detail", member);

        return "member/password";
    }

    @PostMapping("/member/password")
    public String memberPasswordSubmit(Model model, MemberInput parameter, Principal principal){

        String userId = principal.getName();
        parameter.setUserId(userId);

        ServiceResult result = memberService.updateMemberPassword(parameter);

        if(!result.isResult()){
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/member/info";
    }

    @GetMapping("/member/takecourse")
    public String memberTakeCourse(Model model, Principal principal){

        String userId = principal.getName();

        List<TakeCourseDto> list = takeCourseService.myCourse(userId);

        model.addAttribute("list", list);

        return "member/takecourse";
    }

    @GetMapping("/member/reset/password")
    public String resetPassword(Model model, HttpServletRequest request){

        String uuid = request.getParameter("id");
        boolean result = memberService.checkResetPassword(uuid);

        model.addAttribute("result", result);

        return "member/reset_password";
    }

    @PostMapping("/member/reset/password")
    public String resetPasswordSubmit(Model model, ResetPasswordInput resetPasswordInput){

        boolean result = false;
        try{
            result = memberService.resetPassword(resetPasswordInput.getId(), resetPasswordInput.getUserPassword());
        } catch(Exception e) {

        }

        model.addAttribute("result", result);

        return "member/reset_password_result";
    }

    @GetMapping("/member/withdraw")
    public String memeberWithdraw(Model model){

        return "member/withdraw";
    }

    @PostMapping("/member/withdraw")
    public String memeberWithdrawSubmit(Model model, MemberInput parameter, Principal principal){

        String userId = principal.getName();

        ServiceResult result = memberService.withdraw(userId, parameter.getUserPassword());
        if(!result.isResult()){
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/member/logout";
    }
}
