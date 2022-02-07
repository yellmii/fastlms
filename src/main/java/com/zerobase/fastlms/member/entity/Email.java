package com.zerobase.fastlms.member.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Email {

    @Id
    private String templateId;
    private String emailSubject;
    private String emailText;
}
