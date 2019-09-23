package com.vai.news.di;

import javax.inject.Singleton;
import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import lombok.NonNull;
import okhttp3.OkHttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.vai.news.util.TickerProvider;
import com.vai.news.util.NasdaqScraper;
import com.vai.news.util.NotificationPublisher;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
/**
 * Dagger module which provides dependencies for the NasdaqNewsScraperLambda.
 */
@Module
public class NasdaqNewsScraperLambdaModule {

    private static final String NOTIFICATION_TOPIC_ARN = "NOTIFICATION_TOPIC_ARN";

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
    NotificationPublisher provideNotificationPublisher(@NonNull final AmazonSNS amazonSNS,
                                                       @NonNull final ObjectMapper mapper,
                                                       @NonNull @Named(NOTIFICATION_TOPIC_ARN) final String arn) {
        return new NotificationPublisher(amazonSNS, mapper, arn);
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient();
    }

    @Singleton
    @Provides
    ObjectMapper provideObjectMapper() {
        return new ObjectMapper();
    }

    @Singleton
    @Provides
    AmazonSNS provideAmazonSNS() {
        return AmazonSNSClientBuilder.defaultClient();
    }

    @Singleton
    @Provides
    @Named(NOTIFICATION_TOPIC_ARN)
    String provideNotificationTopicArn() {
        return System.getenv(NOTIFICATION_TOPIC_ARN);
    }

}
