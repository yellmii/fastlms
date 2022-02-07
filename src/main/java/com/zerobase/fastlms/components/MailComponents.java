package com.zerobase.fastlms.components;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

//보통 메일 서버 셋팅 필요
//SMTP라는 메일 프로토콜 구축
//시스템 구축 : 도메인 구매 -> 도메인에 해당하는 메일 서버 구축 -> 그 메일 서버로 메일 전송
//개인적으로 위 작업을 진행하기 어려우므로 구글에서 제공하는 소스를 이용

@RequiredArgsConstructor
@Component
public class MailComponents {

    private final JavaMailSender javaMailSender;

    public boolean sendMail(String mail, String subject, String text){

        boolean result = false;

        //MimeMessagePreparator은 인터페이스이기 때문에 생성될 수 없으므로 익명으로 오버라이드 되는 것
        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setTo(mail);
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setText(text, true);
            }
        };

        try{
            javaMailSender.send(msg);
            result = true;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return result;

    }
    /*
    public void sendMailTest() {

        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo("choiyl930@naver.com");
        msg.setSubject("hello");
        msg.setText("nice to meet you");

        javaMailSender.send(msg);

    }

    public boolean sendMail(String mail, String subject, String text){

        boolean result = false;

        //MimeMessagePreparator은 인터페이스이기 때문에 생성될 수 없으므로 익명으로 오버라이드 되는 것
        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setTo(mail);
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setText(text, true);
            }
        };

        try{
            javaMailSender.send(msg);
            result = true;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return result;

    }
     */
}
