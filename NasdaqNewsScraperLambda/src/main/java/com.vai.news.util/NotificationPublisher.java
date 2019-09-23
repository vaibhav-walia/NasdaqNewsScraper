package com.vai.news.util;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.NonNull;
import lombok.SneakyThrows;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.vai.news.models.NewsNotification;

/**
 * This module is responsible for publishing NewsNotifications.
 */
@AllArgsConstructor
@Log4j2
public class NotificationPublisher {

    private final AmazonSNS amazonSNS;

    private final ObjectMapper mapper;

    private final String notificationTopicArn;

    /**
     * Given a NewsNotification, logs it to the console.
     * @param notification
     */
    @SneakyThrows
    public void publish(@NonNull final NewsNotification notification) {
        final PublishResult publishResult = amazonSNS.publish(notificationTopicArn, mapper.writeValueAsString(notification));
        log.info("Published news notification : {}", publishResult);
    }
}
