package com.vai.news.util;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Objects;
import java.util.Collections;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import com.google.common.collect.ImmutableList;

import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

import com.vai.news.models.NewsNotification;
import com.vai.news.models.NewsArticle;
import com.vai.news.models.NasdaqResponse;

/**
 * Module responsible for scraping the Nasdaq website for news related to provided keywords.
 */
@Log4j2
@AllArgsConstructor
public class NasdaqScraper {

    private static final String REQUEST_URL_TEMPLATE =
            "https://www.nasdaq.com/api/v1/search?q=%s&offset=0&latest=1&filters=news_insights";

    private static final String BASE_URI = "https://www.nasdaq.com";

    private static final String HTML_TAG_A = "a";

    private static final String HTML_ATTRIBUTE_HREF = "href";

    private static final String HTML_ATTRIBUTE_TITLE = "title";

    private final OkHttpClient client;

    private final ObjectMapper mapper;

    /**
     * Scrapes the Nasdaq website for news related to the input keyword and returns a NewsNotification.
     */
    public Optional<NewsNotification> scrape(@NonNull final String keyword) {
        List<NewsArticle> articles = getArticles(keyword);
        if (articles.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(
                    NewsNotification.builder()
                            .keyword(keyword)
                            .articles(articles)
                            .build());
        }
    }

    @SneakyThrows
    private List<NewsArticle> getArticles(final String keyword) {
        Request request = new Request.Builder()
                .url(String.format(REQUEST_URL_TEMPLATE, keyword))
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (Objects.isNull(response) || Objects.isNull(response.body())) {
                log.warn("Received malformed response from nasdaq: {}", response);
                return Collections.emptyList();
            }
            String responseString = response.body().string();
            log.debug("Received response: {}", responseString);
            NasdaqResponse nasdaqResponse = mapper.readValue(responseString, NasdaqResponse.class);
            return nasdaqResponse.getItems()
                    .stream()
                    .map(this::extractArticle)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
        }
    }

    private Optional<NewsArticle> extractArticle(String html) {
        Whitelist whitelist = Whitelist.none()
                .addTags(HTML_TAG_A)
                .addAttributes(HTML_TAG_A, HTML_ATTRIBUTE_HREF, HTML_ATTRIBUTE_TITLE);
        Document doc = Jsoup.parse(Jsoup.clean(html, BASE_URI, whitelist));
        Element link = doc.select(HTML_TAG_A).first();

        if (Objects.isNull(link) || Objects.isNull(link.attr(HTML_ATTRIBUTE_HREF)) || Objects.isNull(link.attr(HTML_ATTRIBUTE_TITLE))) {
            log.warn("Unable to extract article from response: {}", html);
            return Optional.empty();
        }
        log.debug("Document body: {}", doc.body());
        return Optional.of(
                NewsArticle.builder()
                .title(link.attr(HTML_ATTRIBUTE_TITLE))
                .url(BASE_URI + link.attr(HTML_ATTRIBUTE_HREF))
                .build());
    }
}
