package com.nimoh.jobManager.data.dto.crawler;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JobCrawlerDto {
    @NotBlank(message = "검색어를 입력해주세요")
    private String searchWord;
    @NotBlank(message = "페이지를 입력해주세요")
    private String recruitPage;
    @NotBlank(message = "페이지 당 출력 개수를 입력해주세요")
    private String recruitPageCount;
    @NotBlank(message = "결과 정렬 방법을 입력해주세요")
    private String recruitSort;
}
