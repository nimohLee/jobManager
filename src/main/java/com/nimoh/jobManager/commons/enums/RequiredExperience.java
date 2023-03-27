package com.nimoh.jobManager.commons.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RequiredExperience {

    NEW("신입"),
    EXPERIENCED("경력");

    private final String career;
}
