package com.nimoh.jobManager.data.dto.crawler;


import lombok.*;

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
