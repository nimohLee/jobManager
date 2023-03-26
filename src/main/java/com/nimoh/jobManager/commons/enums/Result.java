package com.nimoh.jobManager.commons.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Result {

    FAIL("탈락"),
    APPLICATION_COMPLETE("지원완료"),
    DOCUMENT_SCREENING("서류통과"),
    FIRST_INTERVIEW("1차면접통과"),
    LAST_INTERVIEW("최종면접통과"),
    PASS("최종합격");

    private final String resultName;
}
