package com.vai.news.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

import com.vai.news.util.TickerProvider;
import com.vai.news.util.NasdaqScraper;
import com.vai.news.util.NotificationPublisher;

/**
 * Dagger module which provides dependencies for the NasdaqNewsScraperLambda.
 */
@Module
public class NasdaqNewsScraperLambdaModule {

    @Singleton
    @Provides
    TickerProvider provideTickerProvider() {
        return new TickerProvider();
    }

    @Singleton
    @Provides
    NasdaqScraper provideNasdaqScraper() {
        return new NasdaqScraper();
    }

    @Singleton
    @Provides
    NotificationPublisher provideNotificationPublisher() {
        return new NotificationPublisher();
    }
}
