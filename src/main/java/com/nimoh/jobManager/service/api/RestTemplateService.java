package com.nimoh.jobManager.service.api;

import java.util.Map;

public interface RestTemplateService {
    public Map<String, String> getGeocode(String location);
}
