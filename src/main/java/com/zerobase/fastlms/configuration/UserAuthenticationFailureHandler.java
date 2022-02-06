package com.zerobase.fastlms.configuration;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    //에러가 발생했을 때 호출되는 메서드를 오버라이드
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String msg = "Failed Log in";

        if(exception instanceof InternalAuthenticationServiceException) {
            msg = exception.getMessage();
        }

        setUseForward(true);
        setDefaultFailureUrl("/member/login?error=true");
        request.setAttribute("errorMessage", msg);

        System.out.println("Failed Log in");

        super.onAuthenticationFailure(request, response, exception);
    }
}
