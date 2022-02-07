package com.zerobase.fastlms.member.repository;

import com.zerobase.fastlms.member.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailRepository extends JpaRepository<Email, String> {

    Optional<Email> findByEmailSubjectAndEmailTextAndTemplateId(String templateId);

}
