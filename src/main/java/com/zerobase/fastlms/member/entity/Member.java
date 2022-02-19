package com.zerobase.fastlms.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

//데이터베이스와 매핑시키기 위해 클래스 생성
//@Entity -> 데이터베이스에서 테이블을 뜻함
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Member implements MemberCode{

    @Id
    private String userId;
    private String userName;
    private String userPhone;
    private String userPassword;
    private LocalDateTime regDt;
    private LocalDateTime uptDt;

    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;
    private String emailAuthKey;

    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitDt;

    private boolean adminYn;

    private String userStatus;

    private LocalDateTime loginDt;

    private String zipcode;
    private String addr;
    private String addrDetail;

}
