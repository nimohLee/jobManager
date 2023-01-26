package com.nimoh.jobManager.service.api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;

@Service
public class RestTemplateServiceImpl implements RestTemplateService{

    @Value("${properties.kakaoAPI}") // 변수 파일에 등록된 java.file.test 값 가져오기
    private String REST_API_KEY;
    public Map<String, String> getGeocode(String location){
        RestTemplate restTemplate = new RestTemplate();

        final HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + REST_API_KEY);

        final HttpEntity<String> entity = new HttpEntity<String>(headers);
        //URI를 빌드한다
        URI uri = UriComponentsBuilder
                .fromUriString("http://dapi.kakao.com")
                .path("/v2/local/search/address.json")
                .queryParam("query",location)
                .encode(Charset.defaultCharset())
                .build()
                .toUri();
        ParameterizedTypeReference<Map<String, Object>> typeRef =
                new ParameterizedTypeReference<Map<String, Object>>() {};

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                typeRef);
        return getXY(response);
    }

    private Map<String, String> getXY(ResponseEntity<Map<String, Object>> response) {
        ArrayList myArray = (ArrayList) Objects.requireNonNull(response.getBody()).get("documents");
        Map<String,Object> myObject = (Map<String, Object>) myArray.get(0);
        Map<String,Object> address = (Map<String, Object>) myObject.get("address");
        String x = (String) address.get("x");
        String y = (String) address.get("y");
        Map<String, String> result =  new HashMap<>();
        result.put("x",x);
        result.put("y",y);
        return result;
    }

}
