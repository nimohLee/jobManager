package com.nimoh.jobManager.crawler;

import org.jsoup.nodes.Document;
import java.util.List;

public interface JobHtmlParser<T> {
    List<T> parseHTML(Document document);
}
