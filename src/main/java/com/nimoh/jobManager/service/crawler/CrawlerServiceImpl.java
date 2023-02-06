package com.nimoh.jobManager.service.crawler;

import com.nimoh.jobManager.commons.crawler.StrategyName;
import com.nimoh.jobManager.data.dto.crawler.JobCrawlerDto;
import com.nimoh.jobManager.strategy.crawler.Crawler;
import com.nimoh.jobManager.strategy.crawler.CrawlerStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class CrawlerServiceImpl implements CrawlerService{

    @Autowired
    private CrawlerStrategyFactory crawlerStrategyFactory;

    public Crawler findCrawler(StrategyName strategyName){
        return crawlerStrategyFactory.findCrawler(strategyName);
    }

    @Override
    public List<JobCrawlerDto> getSearchList(Map<String, String> searchOption, StrategyName strategyName) throws IOException {
        Crawler crawler = this.findCrawler(strategyName);
        return crawler.getList(searchOption);
    }
}
