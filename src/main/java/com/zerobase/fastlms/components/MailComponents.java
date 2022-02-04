package com.zerobase.fastlms.components;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

//보통 메일 서버 셋팅 필요
//SMTP라는 메일 프로토콜 구축
//시스템 구축 : 도메인 구매 -> 도메인에 해당하는 메일 서버 구축 -> 그 메일 서버로 메일 전송
//개인적으로 위 작업을 진행하기 어려우므로 구글에서 제공하는 소스를 이용

@RequiredArgsConstructor
@Component
public class MailComponents {

    private final JavaMailSender javaMailSender;

    public void sendMailTest() {

        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo("choiyl930@naver.com");
        msg.setSubject("hello");
        msg.setText("nice to meet you");

        javaMailSender.send(msg);

    }
}
