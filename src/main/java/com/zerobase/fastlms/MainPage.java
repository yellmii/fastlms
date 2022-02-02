package com.zerobase.fastlms;

//MainPage 클래스를 만든 목적
//주소(논리적주소=인터넷주소)와 물리적 파일(프로그래밍을 해야 하기 위한)을 매핑하기 위해서

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//하나의 주소에 대해서
//어디서, 누가 매핑을 하느냐?
//1. 클래스 -> 비효율적
//2. 속성  ->  부적절
//3. 메서드 -> 주소에 따른 대응을 위한 방안으로 적절

//주소를 매핑하기 위한 특정 클래스는 Controller라고 칭함.
@RestController
public class MainPage {

    @RequestMapping("/")
    public String index(){
        return "index page";
    }

    @RequestMapping("/hello")
    public String hello(){
        String msg = "hello \r\n website";
        return msg;
    }
}
