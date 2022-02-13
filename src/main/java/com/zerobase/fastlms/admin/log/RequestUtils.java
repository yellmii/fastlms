package com.zerobase.fastlms.admin.log;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class RequestUtils {
    public static String getClientIp(HttpServletRequest request) {
        String ip = null;
        ip = request.getHeader("X-Forwarded-For");

        if(ip == null){
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static String getUserAgent(HttpServletRequest request) {
        String agent = request.getHeader("User-Agent");
        String brower = null;
        String os = null;

        if (agent != null) {
            if (agent.indexOf("Trident") > -1) {
                brower = "MSIE";
            } else if (agent.indexOf("Chrome") > -1) {
                brower = "Chrome";
            } else if (agent.indexOf("Opera") > -1) {
                brower = "Opera";
            } else if (agent.indexOf("iPhone") > -1 && agent.indexOf("Mobile") > -1) {
                brower = "iPhone";
            } else if (agent.indexOf("Android") > -1 && agent.indexOf("Mobile") > -1) {
                brower = "Android";
            }
        }

        if(agent.indexOf("NT 6.0") != -1) os = "Windows Vista/Server 2008";
        else if(agent.indexOf("NT 5.2") != -1) os = "Windows Server 2003";
        else if(agent.indexOf("NT 5.1") != -1) os = "Windows XP";
        else if(agent.indexOf("NT 5.0") != -1) os = "Windows 2000";
        else if(agent.indexOf("NT") != -1) os = "Windows NT";
        else if(agent.indexOf("9x 4.90") != -1) os = "Windows Me";
        else if(agent.indexOf("98") != -1) os = "Windows 98";
        else if(agent.indexOf("95") != -1) os = "Windows 95";
        else if(agent.indexOf("Win16") != -1) os = "Windows 3.x";
        else if(agent.indexOf("Windows") != -1) os = "Windows";
        else if(agent.indexOf("Linux") != -1) os = "Linux";
        else if(agent.indexOf("Macintosh") != -1) os = "Macintosh";
        else os = "";

        return agent + brower + os;
    }
}
