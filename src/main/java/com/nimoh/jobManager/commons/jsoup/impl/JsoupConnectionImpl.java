package com.nimoh.jobManager.commons.jsoup.impl;

import com.nimoh.jobManager.commons.jsoup.JsoupConnection;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

@Component
public class JsoupConnectionImpl implements JsoupConnection {
    @Override
    public Connection connect(String url) {
        return Jsoup.connect(url);
    }
}
