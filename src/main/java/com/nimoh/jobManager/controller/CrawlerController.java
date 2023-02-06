package com.nimoh.jobManager.controller;

import com.nimoh.jobManager.commons.crawler.StrategyName;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import com.nimoh.jobManager.service.crawler.CrawlerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/crawler")
public class CrawlerController {

    Logger logger = LoggerFactory.getLogger(CrawlerController.class);

    private final CrawlerService crawlerService;

    @Autowired
    public CrawlerController(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @Operation(
            summary = "사람인 채용공고 크롤링", description = "사람인 채용공고를 크롤링하여 List로 반환",parameters = {}
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "조회 성공"),
                    @ApiResponse(responseCode = "400",description = "요청값이 잘못되었습니다"),
                    @ApiResponse(responseCode = "500", description = "서버 에러")
            }
    )
    @GetMapping("saramin")
    public ResponseEntity<List<JobCrawlerDto>> getSearchList(
            @RequestParam Map<String, String> params
    ) throws IOException
    {
        logger.info("get /saramin Query :" + params);
        List<JobCrawlerDto> result = crawlerService.getSearchList(params, StrategyName.SARAMIN);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
