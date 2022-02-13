package com.zerobase.fastlms.admin.log.repository;

import com.zerobase.fastlms.admin.log.Entity.LogHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<LogHistory, String> {

}
