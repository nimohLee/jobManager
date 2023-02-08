package com.nimoh.jobManager.commons.jsoup;

import org.jsoup.Connection;

public interface JsoupConnection {
    public Connection connect(String url);
}
