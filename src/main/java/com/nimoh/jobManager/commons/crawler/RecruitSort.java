package com.nimoh.jobManager.commons.crawler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecruitSort {
    RELATION("relation"),
    ACCURACY("accuracy"),
    REG_DATE("reg_dt"),
    EDIT_DATE("edit_dt"),
    CLOSING_DATE("closing_dt"),
    APPLY_COUNT("apply_cnt"),
    EMPLOY_COUNT("employ_cnt")
    ;

    private final String resultSort;
}
