package com.nimoh.jobManager.service;

import com.nimoh.jobManager.exception.restTemplate.RestTemplateErrorResult;
import com.nimoh.jobManager.exception.restTemplate.RestTemplateException;
import com.nimoh.jobManager.service.api.RestTemplateService;
import com.nimoh.jobManager.service.api.RestTemplateServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class RestTemplateServiceTest {

    @Autowired
    private RestTemplateService restTemplateService;


    @TestConfiguration
    class Config {
        @Bean
        RestTemplateService restTemplateService() {
            return new RestTemplateServiceImpl();
        }
    }


    @Nested
    @DisplayName("getCodecode()")
    class getGeocode {

        @Test
        void 좌표가져오기실패_Parameter가null() {
            //given
            String parameter = null;
            //when
            final RestTemplateException exception = assertThrows(RestTemplateException.class, () -> restTemplateService.getGeocode(parameter));
            //then
            Assertions.assertThat(exception.getErrorResult()).isEqualTo(RestTemplateErrorResult.LOCATION_IS_NULL);
        }

        @Test
        void 좌표가져오기성공() {
            //given
            String parameter = "서울특별시 강남구 논현동";
            //when
            Map<String, String> result = restTemplateService.getGeocode(parameter);
            //then
            Assertions.assertThat(result).isNotNull();
        }
    }
}