package com.nimoh.jobManager.crawler;

import org.jsoup.nodes.Document;

public interface JobPlanetHtmlParser<T>{
    T parseHTML(Document document);
}
