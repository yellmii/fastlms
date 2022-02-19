package com.zerobase.fastlms.course.service;

import com.zerobase.fastlms.course.dto.CourseDto;
import com.zerobase.fastlms.course.dto.TakeCourseDto;
import com.zerobase.fastlms.course.model.*;

import java.util.List;

public interface TakeCourseService {

    /**
     * 수강 목록
     */
    List<TakeCourseDto> list(TakeCourseParam parameter);

    /**
     * 수강 상세 정보
     */
    TakeCourseDto detail(long id);

    /**
     * 수강 상태 처리
     */
    ServiceResult updateStatus(long id, String status);

    /**
     * 수강 내역 목록
     */
    List<TakeCourseDto> myCourse(String userId);

    /**
     * 수강 취소 처리
     */
    ServiceResult cancel(long id);
}
