package com.nimoh.jobManager.service.crawler;

import com.nimoh.jobManager.data.dto.crawler.JobPlanetDto;

import java.io.IOException;
import java.util.Map;

public interface JobPlanetService {
    JobPlanetDto getCompanyRate(String companyName);
}
