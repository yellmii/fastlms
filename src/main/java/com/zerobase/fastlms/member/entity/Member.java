package com.zerobase.fastlms.member.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

//데이터베이스와 매핑시키기 위해 클래스 생성
//@Entity -> 데이터베이스에서 테이블을 뜻함
@Data
@Entity
public class Member {

    @Id
    private String userId;
    private String userName;
    private String userPhone;
    private String userPassword;
    private LocalDateTime regDt;

}
