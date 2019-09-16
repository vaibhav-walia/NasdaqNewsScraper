package com.vai.news.util;

import java.util.List;
import lombok.NonNull;
import com.google.common.collect.ImmutableList;

import com.vai.news.models.NewsNotification;
import com.vai.news.models.NewsArticle;

/**
 * Module responsible for scraping the Nasdaq website for news related to provided keywords.
 */
public class NasdaqScraper {

    /**
     * Scrapes the Nasdaq website for news related to the input keyword and returns a NewsNotification.
     */
    public NewsNotification scrape(@NonNull final String keyword) {
        return NewsNotification.builder()
                .keyword(keyword)
                .articles(getArticles(keyword))
                .build();
    }

    private List<NewsArticle> getArticles(final String keyword) {
        return ImmutableList.<NewsArticle>builder()
                .add(NewsArticle.builder()
                        .title(keyword + " news1")
                        .URL("www.google.com")
                        .build()
                )
                .add(NewsArticle.builder()
                        .title(keyword + " news2")
                        .URL("www.amazon.com")
                        .build()
                )
                .build();
    }
}
