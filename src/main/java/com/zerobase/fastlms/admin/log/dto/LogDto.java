package com.zerobase.fastlms.admin.log.dto;

import com.zerobase.fastlms.admin.log.Entity.LogHistory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LogDto {

    String userId;
    String clientIp;
    String userAgnet;
    LocalDateTime loginDt;

    public static LogDto of(LogHistory logHistory) {
        return LogDto.builder()
                .userId(logHistory.getUserId())
                .clientIp(logHistory.getClientIp())
                .userAgnet(logHistory.getUserAgent())
                .loginDt(logHistory.getLoginDt())
                .build();
    }

}
