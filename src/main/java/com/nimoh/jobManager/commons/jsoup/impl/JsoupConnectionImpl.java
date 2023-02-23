package com.nimoh.jobManager.commons.jsoup.impl;

import com.nimoh.jobManager.commons.jsoup.JsoupConnection;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsoupConnectionImpl implements JsoupConnection {
    private Connection connect(String url) {
        return Jsoup.connect(url);
    }

    /**
     * 테스트 가능한 코드를 위해 구현체가 없는 Connection을 리턴하지 않고 Document를 리턴하도록 캡슐화
     * @param url
     * @return
     * @throws IOException
     */
    @Override
    public Document get(String url) throws IOException {
        Connection connect = connect(url);
        return connect.get();
    }
}
