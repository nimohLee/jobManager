package com.nimoh.jobManager.commons.jsoup;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;

public interface JsoupConnection {
    Document get(String url) throws IOException;

}
