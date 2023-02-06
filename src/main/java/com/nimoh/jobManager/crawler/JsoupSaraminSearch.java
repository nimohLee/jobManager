package com.nimoh.jobManager.crawler;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;

public class JsoupSaraminSearch {
    public static String getSearchList(Map searchOption) {
        final String searchList = "https://www.saramin.co.kr/zf_user/search/recruit?search_done=y&search_optional_item=n&company_cd=0%2C1%2C2%2C3%2C4%2C5%2C6%2C7%2C9%2C10&show_applied=&quick_apply=&except_read=&ai_head_hunting=&mainSearch=n"
                + "&searchword=" +searchOption.get("searchWord")
                + "&recruitPage=" + searchOption.get("recruitPage")
                + "&recruitPageCount="+ searchOption.get("recruitPageCount")
                + "&recruitSort=" + searchOption.get("recruitSort");


        Connection conn = Jsoup.connect(searchList);

        try {
            Document document = conn.get();
            String jobInfo = getJobInfo(document); // 칼럼명
            return jobInfo;
        } catch (IOException ignored) {
        }
        return null;
    }


    public static String getJobInfo(Document document) {
        Elements itemRecruit = document.select("item_recruit");
        StringBuilder sb = new StringBuilder();
        for (Element element : itemRecruit) {
            String title = element.select("area_job job_tit a").attr("title");
            sb.append(title);
        }
        return sb.toString();
    }

    public static String getJobIanfo(Document document) {
        Elements stockTableBody = document.select("table.type_2 tbody tr");
        StringBuilder sb = new StringBuilder();
        for (Element element : stockTableBody) {
            if (element.attr("onmouseover").isEmpty()) {
                continue;
            }

            for (Element td : element.select("td")) {
                String text;
                if (td.select(".center a").attr("href").isEmpty()) {
                    text = td.text();
                } else {
                    text = "https://finance.naver.com" + td.select(".center a").attr("href");
                }
                sb.append(text);
                sb.append("   ");
            }
            sb.append(System.getProperty("line.separator")); //줄바꿈
        }
        return sb.toString();
    }
}
