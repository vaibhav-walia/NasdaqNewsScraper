package com.vai.news.models;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import lombok.Builder;
import lombok.Data;


/**
 * POJO containing response object for API Gateway.
 */
@Builder
@Data
public class GatewayResponse {

    private final String body;

    private final Map<String, String> headers;

    private final int statusCode;
}
