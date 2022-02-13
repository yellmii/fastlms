package com.zerobase.fastlms.admin.log;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class logInfo {

    public static String getClientIp(HttpServletRequest request) {
        request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String clientIp = request.getHeader("X-FORWARDED-FOR");
        if(clientIp == null){
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    public static String getUserAgent(HttpServletRequest request) {

        String requestUserAgent = request.getHeader("X-FORWARDED-FOR");
        String userAgent = "";

        try{
            if(requestUserAgent.length() > 0) {
                if (requestUserAgent.indexOf("Trident") > -1) {
                    userAgent = "MSIE";
                } else if (requestUserAgent.indexOf("Chrome") > -1) {
                    userAgent = "Chrome";
                } else if (requestUserAgent.indexOf("Opera") > -1) {
                    userAgent = "Opera";
                } else if (requestUserAgent.indexOf("iPhone") > -1
                        && requestUserAgent.indexOf("Mobile") > -1) {
                    userAgent = "iPhone";
                } else if (requestUserAgent.indexOf("Android") > -1
                        && requestUserAgent.indexOf("Mobile") > -1) {
                    userAgent = "Android";
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }


        return userAgent;

    }
}
