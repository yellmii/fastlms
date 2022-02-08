package com.zerobase.fastlms.admin;

import java.time.LocalDateTime;

public class MemberDto {

    String user_id;
    String user_name;
    String user_phone;
    String user_password;
    LocalDateTime reg_dt;

    boolean email_auth_yn;
    LocalDateTime email_auth_dt;
    String email_auth_key;

    String reset_password_key;
    LocalDateTime reset_password_limit_dt;
    boolean admin_yn;
}
