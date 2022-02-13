package com.zerobase.fastlms.admin.log.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class LogHistory {

    @Id
    String userId;

    LocalDateTime loginDt;
    String clientIp;
    String userAgent;

}
