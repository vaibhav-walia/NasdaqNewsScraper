package com.vai.news.di;

import javax.inject.Singleton;
import dagger.Component;

import com.vai.news.util.NasdaqScraper;
import com.vai.news.util.NotificationPublisher;
import com.vai.news.util.TickerProvider;
import com.vai.news.lambda.NasdaqNewsScraperLambda;

/**
 * Dagger component used for di.
 */
@Singleton
@Component(modules = {NasdaqNewsScraperLambdaModule.class})
public interface NasdaqNewsScraperLambdaComponent {
    TickerProvider getTickerProvider();
    NasdaqScraper getNasdaqScraper();
    NotificationPublisher getNotificationPublisher();
}
