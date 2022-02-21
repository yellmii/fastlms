package com.zerobase.fastlms.admin.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BannerParam extends CommonParam {

    long id;
    String bannerName;
    String filename;
    String urlFilename;
    String alterText;
    String url;
    String clickTarget;
    int sortValue;
    boolean frontYn;

    LocalDateTime regDt;
    LocalDateTime uptDt;

}
