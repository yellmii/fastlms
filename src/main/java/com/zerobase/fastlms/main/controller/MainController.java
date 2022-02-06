package com.zerobase.fastlms.main.controller;

//MainPage 클래스를 만든 목적
//주소(논리적주소=인터넷주소)와 물리적 파일(프로그래밍을 해야 하기 위한)을 매핑하기 위해서

import com.zerobase.fastlms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//하나의 주소에 대해서
//어디서, 누가 매핑을 하느냐?
//1. 클래스 -> 비효율적
//2. 속성  ->  부적절
//3. 메서드 -> 주소에 따른 대응을 위한 방안으로 적절

//주소를 매핑하기 위한 특정 클래스는 Controller라고 칭함.
//@RestController와 @Controller의 차이
@RequiredArgsConstructor
@Controller
public class MainController {

    private final MailComponents mailComponents;

    @RequestMapping("/")
    public String index(){
        /*
        String mail = "choiyl930@naver.com";
        String subject = "hello choi!";
        String text = "<p>hello</p><p>nice to meet you</p>";
        mailComponents.sendMail(mail, subject, text);
*/
        return "index"; //매핑되는 메서드의 리턴되는 문자열은 파일명이라고 인식하므로 index.html이 표시된다.
    }

    /*
    //request : web -> server
    //response : server -> web
    @RequestMapping("/hello")
    public void hello(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter printWriter = response.getWriter(); //getWriter()는 예외 발생시키므로 예외처리 해줘야함.(①. try-catch로 감싸기 ②.throws하기)
        String msg = "hello \r\n website \r\n 안녕하세요";

        printWriter.write(msg);
        printWriter.close();
    }
*/

}
