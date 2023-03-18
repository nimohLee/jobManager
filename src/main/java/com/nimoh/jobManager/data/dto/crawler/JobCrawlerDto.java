package com.nimoh.jobManager.data.dto.crawler;

import lombok.*;
import java.util.List;

/**
 * 구직사이트 크롤러 DTO
 *
 * @author nimoh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JobCrawlerDto {
    private String title;

    private String companyName;

    private String companyUrl;

    private String url;

    private String jobDate;

    private List<String> jobCondition;

    private int resultCount;
}
