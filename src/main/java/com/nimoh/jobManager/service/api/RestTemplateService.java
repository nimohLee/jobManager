package com.nimoh.jobManager.service.api;

import java.util.Map;

public interface RestTemplateService {
    Map<String, String> getGeocode(String location);
}
