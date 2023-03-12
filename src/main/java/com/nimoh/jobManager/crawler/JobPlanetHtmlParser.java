package com.nimoh.jobManager.crawler;

import org.jsoup.nodes.Document;
import java.util.List;

public interface JobPlanetHtmlParser<T>{
    T parseHTML(Document document);
}
