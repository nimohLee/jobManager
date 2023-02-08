package com.nimoh.jobManager.commons.crawler.crawlerSort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IncruitRecruitSort {
    ACCURACY("rank"),
    REG_DATE("reg"),
    EDIT_DATE("mod"),
    CLOSING_DATE("invite"),
    READ_COUNT("viewcnt"),
    APPLY_COUNT("applycnt");

    private final String resultSort;
}
