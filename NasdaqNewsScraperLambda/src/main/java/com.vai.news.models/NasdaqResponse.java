package com.vai.news.models;

import java.util.List;

import lombok.Data;

/**
 * Pojo which represents the response received from the nasdaq website.
 */
@Data
public class NasdaqResponse {

    private String resultsCount;

    private String searchString;

    private List<String> items;
}
