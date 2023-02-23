package com.nimoh.jobManager.controller;

import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import com.nimoh.jobManager.data.dto.crawler.JobPlanetDto;
import com.nimoh.jobManager.service.crawler.JobPlanetService;
import com.nimoh.jobManager.service.crawler.JobSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/crawler")
public class CrawlerController {

    Logger logger = LoggerFactory.getLogger(CrawlerController.class);

    private final JobSearchService jobSearchService;
    private final JobPlanetService jobPlanetService;

    public CrawlerController(JobSearchService jobSearchService, JobPlanetService jobPlanetService) {
        this.jobSearchService = jobSearchService;
        this.jobPlanetService = jobPlanetService;
    }

    @Operation(
            summary = "사람인 채용공고 크롤링", description = "사람인 채용공고를 크롤링하여 List로 반환", parameters = {}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "조회 성공"),
                    @ApiResponse(responseCode = "400", description = "요청값이 잘못되었습니다"),
                    @ApiResponse(responseCode = "500", description = "서버 에러")
            }
    )
    @GetMapping("saramin")
    public ResponseEntity<List<JobCrawlerDto>> getSaraminList(
            @RequestParam Map<String, String> params
    ) throws IOException {
        logger.info("get /saramin Query :" + params);
        List<JobCrawlerDto> result = jobSearchService.getSearchList(params, "saraminCrawler");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(
            summary = "잡코리아 채용공고 크롤링", description = "잡코리아 채용공고를 크롤링하여 List로 반환", parameters = {}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "조회 성공"),
                    @ApiResponse(responseCode = "400", description = "요청값이 잘못되었습니다"),
                    @ApiResponse(responseCode = "500", description = "서버 에러")
            }
    )
    @GetMapping("jobkorea")
    public ResponseEntity<List<JobCrawlerDto>> getJobKoreaList(
            @RequestParam Map<String, String> params
    ) throws IOException {
        logger.info("get /jobkorea Query :" + params);
        List<JobCrawlerDto> result = jobSearchService.getSearchList(params,"jobKoreaCrawler");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(
            summary = "잡코리아 채용공고 크롤링", description = "잡코리아 채용공고를 크롤링하여 List로 반환", parameters = {}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "조회 성공"),
                    @ApiResponse(responseCode = "400", description = "요청값이 잘못되었습니다"),
                    @ApiResponse(responseCode = "500", description = "서버 에러")
            }
    )
    @GetMapping("incruit")
    public ResponseEntity<List<JobCrawlerDto>> getIncruitList(
            @RequestParam Map<String, String> params
    ) throws IOException {
        logger.info("get /incruit Query :" + params);
        List<JobCrawlerDto> result = jobSearchService.getSearchList(params,"incruitCrawler");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Operation(
            summary = "잡플래닛 기업검색 크롤링", description = "잡플래닛 기업검색를 크롤링하여 List로 반환", parameters = {}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "조회 성공"),
                    @ApiResponse(responseCode = "400", description = "요청값이 잘못되었습니다"),
                    @ApiResponse(responseCode = "500", description = "서버 에러")
            }
    )
    @GetMapping("jobplanet")
    public ResponseEntity<JobPlanetDto> getJobPlanet(
            @RequestParam String companyName
    ) throws IOException {
        logger.info("get /jobplanet Query :" + companyName);
        JobPlanetDto result = jobPlanetService.getCompanyRate(companyName);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
