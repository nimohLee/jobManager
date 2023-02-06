package com.nimoh.jobManager.strategy.crawler;

import com.nimoh.jobManager.commons.crawler.StrategyName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class CrawlerStrategyFactory {
    private Map<StrategyName, Crawler> crawlers;

    @Autowired
    public CrawlerStrategyFactory(Set<Crawler> crawlerSet) {
        createStrategy(crawlerSet);
    }

    public Crawler findCrawler(StrategyName strategyName) {
        return crawlers.get(strategyName);
    }

    private void createStrategy(Set<Crawler> crawlerSet) {
        crawlers = new HashMap<>();
        crawlerSet.forEach(
                crawler -> crawlers.put(crawler.getStrategyName(), crawler)
        );
    }
}
