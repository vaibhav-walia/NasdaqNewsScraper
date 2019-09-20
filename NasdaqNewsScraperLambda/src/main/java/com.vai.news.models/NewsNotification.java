package com.vai.news.models;

import lombok.Data;
import lombok.Builder;

import  java.util.List;

/**
 * Pojo representing a News Notification.
 */
@Builder
@Data
public class NewsNotification {

    private String keyword;

    private List<NewsArticle> articles;
}
