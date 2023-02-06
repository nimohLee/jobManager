package com.nimoh.jobManager.controller;

import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/crawler")
public class CrawlerController {

    Logger logger = LoggerFactory.getLogger(CrawlerController.class);

    @GetMapping("saramin")
    public ResponseEntity<JobCrawlerDto> getSearchList(
            @Valid @RequestBody JobCrawlerDto jobCrawlerDto
    ){
        logger.info(jobCrawlerDto.toString());
        return ResponseEntity.ok().build();
    }
}
