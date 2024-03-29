package com.nimoh.jobManager.commons.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum JobSearchSite {

    SARAMIN("사람인"),
    JOBKOREA("잡코리아"),
    INCRUIT("인크루트");

    private final String name;
}
