package com.nimoh.jobManager.commons.crawler.crawlerSort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JobKoreaRecruitSort {
    RELATION("ExactDesc"),
    REG_DATE("RegDtDesc"),
    EDIT_DATE("EditDtDesc"),
    CLOSING_DATE("ApplyCloseDtAsc"),
    READ_COUNT("ReadCntDesc"),
    APPLY_COUNT("ApplicantDesc")
    ;

    private final String resultSort;
}

