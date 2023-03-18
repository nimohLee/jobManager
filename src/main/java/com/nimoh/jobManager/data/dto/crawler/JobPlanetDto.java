package com.nimoh.jobManager.data.dto.crawler;

import lombok.*;

/**
 * 잡플래닛 검색 DTO
 *
 * @author nimoh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JobPlanetDto {

    private String companyName;

    private String companyUrl;

    private String titleSub;

    private String rate;

}
