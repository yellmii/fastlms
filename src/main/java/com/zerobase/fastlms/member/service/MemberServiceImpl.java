package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.log.dto.LogDto;
import com.zerobase.fastlms.admin.log.Entity.LogHistory;
import com.zerobase.fastlms.admin.log.repository.LogRepository;
import com.zerobase.fastlms.admin.mapper.MemberMapper;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.components.MailComponents;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.email.Entity.Email;
import com.zerobase.fastlms.email.repository.EmailRepository;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.entity.MemberCode;
import com.zerobase.fastlms.member.exception.MemberNotEmailAuthException;
import com.zerobase.fastlms.member.exception.MemberStopUserException;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;
    private final EmailRepository emailRepository;
    private final MemberMapper memberMapper;
    private final LogRepository logRepository;

    @Override
    public boolean register(MemberInput parameter) {

        //.findById() : Id를 가지고 데이터가 존재하는지 확인해주는 함수
        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if(optionalMember.isPresent()){

            return false;
        }

        Optional<Email> optionalEmail = emailRepository.findById("signin");
        Email email2 = optionalEmail.get();

        String encPassword = BCrypt.hashpw(parameter.getUserPassword(), BCrypt.gensalt());
        String uuid = UUID.randomUUID().toString();

        Member member = new Member();
        member.setUserId(parameter.getUserId());
        member.setUserName(parameter.getUserName());
        member.setUserPhone(parameter.getUserPhone());
        member.setUserPassword(encPassword);
        member.setRegDt(LocalDateTime.now());
        member.setEmailAuthYn(false); //회원가입이므로 처음에 인증 안되어있을테니 false
        member.setEmailAuthKey(uuid);  //회원가입이므로 처음에 인증키를 랜덤으
        member.setUserStatus(Member.MEMBER_STATUS_REQ);
        memberRepository.save(member);

        String email = parameter.getUserId();
        String subject = email2.getEmailSubject();
        String text = parameter.getUserName() + "님! " + email2.getEmailText() + uuid + "'>link</a></div>";

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

        if(member.isEmailAuthYn()){
            return false;
        }

        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        member.setUserStatus(Member.MEMBER_STATUS_ING);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean log(String userId, LocalDateTime loginDt, String userAgent, String clientIp) {
        LogHistory logHistory = new LogHistory();
        Optional<Member> optionalMember = memberRepository.findById(userId);

        logHistory.setUserId(userId);
        logHistory.setLoginDt(loginDt);
        logHistory.setUserAgent(userAgent);
        logHistory.setClientIp(clientIp);
        logRepository.save(logHistory);

        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("Not Found");
        }

        Member member = optionalMember.get();

        member.setLoginDt(loginDt);
        memberRepository.save(member);

        return true;
    }

    @Override
    public LogDto logDetail(String userId) {
        Optional<LogHistory> optionalLogHistory = logRepository.findById(userId);
        if(!optionalLogHistory.isPresent()){
            return null;
        }

        LogHistory logHistory = optionalLogHistory.get();

        return LogDto.of(logHistory);
    }

    @Override
    public boolean sendResetPassword(ResetPasswordInput resetPasswordInput) {

        Optional<Email> optionalEmail = emailRepository.findById("repassword");
        Email email2 = optionalEmail.get();

        Optional<Member> optionalMember = memberRepository.findByUserIdAndUserName(resetPasswordInput.getUserId(), resetPasswordInput.getUserName());
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("Not Found");
        }

        Member member = optionalMember.get();

        String uuid = UUID.randomUUID().toString();

        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1));
        memberRepository.save(member);

        String email = resetPasswordInput.getUserId();
        String subject = email2.getEmailSubject();
        String text = resetPasswordInput.getUserName() + "님! " + email2.getEmailText() + uuid + "'>link</a></div>";


        mailComponents.sendMail(email, subject, text);

        return true;
    }

    @Override
    public boolean resetPassword(String uuid, String userPassword) {

        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("Not Found");
        }

        Member member = optionalMember.get();

        //초기화 날짜가 유효한지 체크
        if(member.getResetPasswordLimitDt() == null) {
            throw new RuntimeException("Unmatch Date");
        }

        if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Unmatch Date");
        }

        String encPassword = BCrypt.hashpw(userPassword, BCrypt.gensalt());
        member.setUserPassword(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {

        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if(!optionalMember.isPresent()){
            return false;
        }

        Member member = optionalMember.get();

        //초기화 날짜가 유효한지 체크
        if(member.getResetPasswordLimitDt() == null) {
            throw new RuntimeException("Unmatch Date");
        }

        if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Unmatch Date");
        }

        return true;
    }

    @Override
    public List<MemberDto> list(MemberParam parameter) {

        long totalCount = memberMapper.selectListCount(parameter);
        List<MemberDto> list = memberMapper.selectList(parameter);
        if(!CollectionUtils.isEmpty(list)){
            int i = 0;
            for(MemberDto x : list){
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }
        return list;
        //return memberRepository.findAll();
    }

    @Override
    public MemberDto detail(String userId) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            return null;
        }

        Member member = optionalMember.get();

        return MemberDto.of(member);
    }

    @Override
    public boolean updateStatus(String userId, String userStatus) {

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("Not Found");
        }

        Member member = optionalMember.get();

        member.setUserStatus(userStatus);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean updatePassword(String userId, String userPassword) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("Not Found");
        }

        Member member = optionalMember.get();

        String encPassword = BCrypt.hashpw(userPassword, BCrypt.gensalt());

        member.setUserPassword(encPassword);
        memberRepository.save(member);

        return true;
    }

    @Override
    public ServiceResult updateMemberPassword(MemberInput parameter) {

        String userId = parameter.getUserId();

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            return new ServiceResult(false, "Not Found");
        }

        Member member = optionalMember.get();

        if(!BCrypt.checkpw(parameter.getUserPassword(), member.getUserPassword())){
            return new ServiceResult(false, "Not Match");
        }

        String encPassword = BCrypt.hashpw(parameter.getNewPassword(), BCrypt.gensalt());
        member.setUserPassword(encPassword);
        memberRepository.save(member);

        return new ServiceResult(true);
    }

    @Override
    public ServiceResult updateMember(MemberInput parameter) {

        String userId = parameter.getUserId();

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            return new ServiceResult(false, "Not Found");
        }

        Member member = optionalMember.get();

        System.out.println(member);

        member.setUserPhone(parameter.getUserPhone());
        member.setZipcode(parameter.getZipcode());
        member.setAddr(parameter.getAddr());
        member.setAddrDetail(parameter.getAddrDetail());
        member.setUptDt(LocalDateTime.now());
        memberRepository.save(member);

        return new ServiceResult(true);
    }

    @Override
    public ServiceResult withdraw(String userId, String password) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            return new ServiceResult(false, "Not Found");
        }

        Member member = optionalMember.get();

        if(!PasswordUtil.equals(password, member.getUserPassword())){
            return new ServiceResult(false, "비밀번호가 일치하지 않습니다.");
        }

        member.setUserName("삭제회원");
        member.setUserPhone("");
        member.setUserPassword("");
        member.setRegDt(null);
        member.setUptDt(null);
        member.setEmailAuthYn(false);
        member.setEmailAuthDt(null);
        member.setEmailAuthKey("");
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        member.setUserStatus(MemberCode.MEMBER_STATUS_WITHDRAW);
        member.setZipcode("");
        member.setAddr("");
        member.setAddrDetail("");
        memberRepository.save(member);

        return new ServiceResult(true);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findById(username);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("Not Found");
        }

        Member member = optionalMember.get();

        //이메일 인증을 안 했을 경우, 로그인 못하게 예외 발생
        if(Member.MEMBER_STATUS_REQ.equals(member.getUserStatus())){
            throw new MemberNotEmailAuthException("Please Check your Email link");
        }
        //정지된 회원일 경우, 로그인 못하게 예외 처리
        if(Member.MEMBER_STATUS_STOP.equals(member.getUserStatus())){
            throw new MemberStopUserException("STOP Member");
        }
        //탈퇴한 회원일 경우, 로그인 못하게 예외 처리
        if(Member.MEMBER_STATUS_WITHDRAW.equals(member.getUserStatus())){
            throw new MemberStopUserException("WITHDRAW Member");
        }
        /*
        //이메일 인증을 안 했을 경우, 로그인 못하게 예외 발생
        if(!member.isEmailAuthYn()){
            throw new MemberNotEmailAuthException("Please Check your Email link");
        }
        */
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));

        if(member.isAdminYn()){
            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(member.getUserId(), member.getUserPassword(), grantedAuthorityList);
    }
}
