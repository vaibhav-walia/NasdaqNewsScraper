package com.vai.news.di;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

import lombok.NonNull;
import okhttp3.OkHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    NasdaqScraper provideNasdaqScraper(@NonNull final OkHttpClient client, @NonNull final ObjectMapper mapper) {
        return new NasdaqScraper(client, mapper);
    }

    @Singleton
    @Provides
    NotificationPublisher provideNotificationPublisher() {
        return new NotificationPublisher();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    ObjectMapper  provideObjectMapper() {
        return new ObjectMapper();
    }

}
