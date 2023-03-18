package com.nimoh.jobManager.controller;

import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import com.nimoh.jobManager.data.dto.crawler.JobPlanetDto;
import com.nimoh.jobManager.service.crawler.JobPlanetService;
import com.nimoh.jobManager.service.crawler.JobSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 크롤러 컨트롤러
 *
 * @author nimoh
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/crawler")
public class CrawlerController {

    Logger logger = LoggerFactory.getLogger(CrawlerController.class);

    private final JobSearchService jobSearchService;
    private final JobPlanetService jobPlanetService;

    /**
     * 사람인 채용공고를 크롤링하여 List로 반환
     *
     * @param params 검색 옵션 QueryString
     * @return 성공 시 Http Status 200 && 검색 결과
     */
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
    ){
        logger.info("get /saramin Query :" + params);
        List<JobCrawlerDto> result = jobSearchService.getSearchList(params, "saraminCrawler");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 잡코리아 채용공고를 크롤링하여 List로 반환
     *
     * @param params 검색 옵션 QueryString
     * @return 성공 시 Http Status 200 && 검색 결과
     */
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
    ){
        logger.info("get /jobkorea Query :" + params);
        List<JobCrawlerDto> result = jobSearchService.getSearchList(params,"jobKoreaCrawler");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 인크루트 채용공고를 크롤링하여 List로 반환
     *
     * @param params 검색 옵션 QueryString
     * @return 성공 시 Http Status 200 && 검색 결과
     */
    @Operation(
            summary = "인크루트 채용공고 크롤링", description = "인크루트 채용공고를 크롤링하여 List로 반환", parameters = {}
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
    ){
        logger.info("get /incruit Query :" + params);
        List<JobCrawlerDto> result = jobSearchService.getSearchList(params,"incruitCrawler");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 잡플래닛 회사정보를 크롤링하여 반환
     *
     * @param companyName 검색할 회사명
     * @return 성공 시 Http Status 200 && 검색 결과
     */
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
    ){
        logger.info("get /jobplanet Query :" + companyName);
        JobPlanetDto result = jobPlanetService.getCompanyRate(companyName);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
