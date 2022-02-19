package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberDto {

    String userId;
    String userName;
    String userPhone;
    String userPassword;
    LocalDateTime regDt;
    LocalDateTime uptDt;

    boolean emailAuthYn;
    LocalDateTime emailAuthDt;
    String emailAuthKey;

    String resetPasswordKey;
    LocalDateTime resetPasswordLimitDt;
    boolean adminYn;
    String userStatus;

    long totalCount;
    long seq;

    LocalDateTime loginDt;

    String zipcode;
    String addr;
    String addrDetail;

    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .userId(member.getUserId())
                .userName(member.getUserName())
                .userPhone(member.getUserPhone())
                .regDt(member.getRegDt())
                .uptDt(member.getUptDt())
                .emailAuthYn(member.isEmailAuthYn())
                .emailAuthDt(member.getEmailAuthDt())
                .emailAuthKey(member.getEmailAuthKey())
                .resetPasswordKey(member.getResetPasswordKey())
                .resetPasswordLimitDt(member.getResetPasswordLimitDt())
                .adminYn(member.isAdminYn())
                .userStatus(member.getUserStatus())
                .loginDt(member.getLoginDt())
                .zipcode(member.getZipcode())
                .addr(member.getAddr())
                .addrDetail(member.getAddrDetail())
                .build();
    }

    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return regDt != null ? regDt.format(formatter) : "";
    }

    public String getUptDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return uptDt != null ? uptDt.format(formatter) : "";
    }

}
