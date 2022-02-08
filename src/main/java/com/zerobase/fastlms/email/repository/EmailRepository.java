package com.zerobase.fastlms.email.repository;

import com.zerobase.fastlms.email.Entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, String> {


}
