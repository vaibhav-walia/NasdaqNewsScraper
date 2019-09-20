package com.vai.news.models;

import lombok.Builder;
import lombok.Data;

/**
 * Pojo representing a news article.
 */
@Builder
@Data
public class NewsArticle {

    private String title;

    private String url;
}
