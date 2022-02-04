package com.zerobase.fastlms.member.service.impl;

import com.zerobase.fastlms.components.MailComponents;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;

    @Override
    public boolean register(MemberInput parameter) {

        //.findById() : Id를 가지고 데이터가 존재하는지 확인해주는 함수
        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if(optionalMember.isPresent()){

            return false;
        }

        String uuid = UUID.randomUUID().toString();

        Member member = new Member();
        member.setUserId(parameter.getUserId());
        member.setUserName(parameter.getUserName());
        member.setUserPhone(parameter.getUserPhone());
        member.setUserPassword(parameter.getUserPassword());
        member.setRegDt(LocalDateTime.now());
        member.setEmailAuthYn(false); //회원가입이므로 처음에 인증 안되어있을테니 false
        member.setEmailAuthKey(uuid);  //회원가입이므로 처음에 인증키를 랜덤으로
        memberRepository.save(member);

        String email = parameter.getUserId();
        String subject = "Congraturation!";
        String text = "<p>Congraturation your sign in</p><p>Please Check under the link.</p>"
                + "<div><a href='http://localhost:8080/member/email-auth?id=" + uuid + "'>link</a></div>";

        mailComponents.sendMail(email, subject, text);

        return true;
    }

    @Override
    public boolean emailAuth(String uuid) {

        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
        if(!optionalMember.isPresent()){
            return false;
        }

        Member member = optionalMember.get();
        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }
}
