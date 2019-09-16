package com.vai.news.util;

import lombok.extern.log4j.Log4j2;
import lombok.NonNull;

import com.vai.news.models.NewsNotification;

/**
 * This module is responsible for publishing NewsNotifications.
 */
@Log4j2
public class NotificationPublisher {

    /**
     * Given a NewsNotification, logs it to the console.
     * @param notification
     */
    public void publish(@NonNull final NewsNotification notification) {
        log.info("Published news notification: {}", notification);
    }
}
