package com.vai.news.lambda;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Objects;
import java.util.Collection;

import com.google.common.collect.ImmutableMap;

import com.vai.news.util.NasdaqScraper;
import com.vai.news.util.TickerProvider;
import com.vai.news.util.NotificationPublisher;

import lombok.AccessLevel;
import javax.inject.Inject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.vai.news.models.GatewayResponse;
import com.vai.news.di.NasdaqNewsScraperLambdaComponent;
import com.vai.news.di.DaggerNasdaqNewsScraperLambdaComponent;

/**
 * Handler for requests to Lambda function.
 */
public class NasdaqNewsScraperLambda implements RequestHandler<Object, Object> {

    private TickerProvider tickerProvider;

    private NasdaqScraper nasdaqScraper;

    private NotificationPublisher notificationPublisher;

    private static final NasdaqNewsScraperLambdaComponent component = DaggerNasdaqNewsScraperLambdaComponent.create();

    /**
     * Constructor to initialize dependencies.
     */
    public NasdaqNewsScraperLambda() {
        this.tickerProvider = component.getTickerProvider();
        this.nasdaqScraper = component.getNasdaqScraper();
        this.notificationPublisher = component.getNotificationPublisher();
    }

    /**
     * Request handler for incoming requests. Servers as entry point for the news generation proccess.
     */
    public Object handleRequest(final Object input, final Context context) {
        tickerProvider.provide()
                .stream()
                .map(nasdaqScraper::scrape)
                .filter(Objects::nonNull)
                .forEach(notificationPublisher::publish);

        return GatewayResponse.builder()
                .body("{ \"message\": \"published all news notifications \"  }")
                .headers(ImmutableMap.of(
                        "Content-Type", "application/json",
                        "X-Custom-Header", "application/json"))
                .statusCode(200)
                .build();
    }
}
