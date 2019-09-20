package com.vai.news.util;

import com.google.common.collect.ImmutableList;
import java.util.List;
import lombok.extern.log4j.Log4j2;

/**
 * Module to provide a list of stock tickers.
 */
@Log4j2
public class TickerProvider {

    /**
     * Provides a list of stock tickers.
     * @return List<String> of stock tickers.
     */
    public List<String> provide() {
        log.info("Providing tickers");
        return ImmutableList.<String>builder()
                .add("HEXO")
                .add("ACB")
                .add("ZNGA")
                .add("XMLV")
                .add("VYM")
                .build();
    }
}
