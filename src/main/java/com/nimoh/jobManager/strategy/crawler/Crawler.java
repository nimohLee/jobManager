package com.nimoh.jobManager.strategy;

import com.nimoh.jobManager.commons.crawler.StrategyName;

public interface Crawler {
    void doStuff();

    StrategyName getStrategyName();
}
