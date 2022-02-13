package com.zerobase.fastlms.member.service;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.log.dto.LogDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberService extends UserDetailsService {
    boolean register(MemberInput parameter);

    /**
     * uuid에 해당하는 계정을 활성화
     */
    boolean emailAuth(String uuid);

    /**
     * 입력한 이메일로 비밀번호 초기화 정보를 전송
     */
    boolean sendResetPassword(ResetPasswordInput resetPasswordInput);

    /**
     * 입력받은 uuid에 대해서 비밀번호 초기화
     */
    boolean resetPassword(String id, String userPassword);

    /**
     * 입력받은 uuid값이 유효한지 확인
     */
    boolean checkResetPassword(String uuid);

    /**
     * 회원 목록 리턴(관리자에서만 사용)
     */
    List<MemberDto> list(MemberParam parameter);

    /**
     * 회원 상세 목록
     */
    MemberDto detail(String userId);

    /**
     * 회원 상태 변경
     */
    boolean updateStatus(String userId, String userStatus);

    /**
     * 회원 비밀번호 변경
     */
    boolean updatePassword(String userId, String userPassword);

    /**
     * 로그인 정보
     */
    boolean log(String userId, LocalDateTime LoginDt, String userAgent, String clientIp);

    /**
     * 로그 상세 정보
     */
    LogDto logDetail(String userId);
}
