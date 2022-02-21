package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.entity.Category;
import com.zerobase.fastlms.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BannerDto {

    Long id;

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

    long totalCount;
    long seq;

    public static BannerDto of(Banner banner) {
        return BannerDto.builder()
                .id(banner.getId())
                .bannerName(banner.getBannerName())
                .filename(banner.getFilename())
                .urlFilename(banner.getUrlFilename())
                .alterText(banner.getAlterText())
                .url(banner.getUrl())
                .clickTarget(banner.getClickTarget())
                .sortValue(banner.getSortValue())
                .frontYn(banner.isFrontYn())
                .regDt(banner.getRegDt())
                .uptDt(banner.getUptDt())
                .build();
    }

    public static List<BannerDto> of(List<Banner> banners) {
        if(banners != null){
            List<BannerDto> bannerDtos = new ArrayList<>();
            for(Banner x : banners){
                bannerDtos.add(of(x));
            }
            return bannerDtos;
        }
        return null;
    }

    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return regDt != null ? regDt.format(formatter) : "";
    }

    public String getUptDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return uptDt != null ? uptDt.format(formatter) : "";
    }

}
