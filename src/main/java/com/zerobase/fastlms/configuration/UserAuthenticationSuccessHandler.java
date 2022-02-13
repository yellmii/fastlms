package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.admin.log.RequestUtils;
import com.zerobase.fastlms.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Configuration
public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private MemberService memberService;

    //로그인 성공시 호출되는 메서드를 오버라이드
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        System.out.println("Success Log in");

        boolean result = memberService.log(authentication.getName(), LocalDateTime.now(), RequestUtils.getUserAgent(request), RequestUtils.getClientIp(request));

        super.onAuthenticationSuccess(request, response, authentication);
    }


}
