package com.zerobase.fastlms.course.model;

import lombok.Data;

@Data
public class CourseInput {

    long id;
    long categoryId;
    String subject;

    String keyword;
    String summary;
    String contents;
    String saleEndDtText;
    long price;
    long salePrice;

    //삭제를 위한
    String idList;

    String filename;
    String urlFilename;

}
