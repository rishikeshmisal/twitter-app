package com.twitter.twitterapp.exception;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

public class TAResponseErrorHandler implements ResponseErrorHandler {

    private static final org.slf4j.Logger logger = LoggerFactory
            .getLogger(TAResponseErrorHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {

        if (response.getStatusCode() != HttpStatus.OK) {
            HttpStatus status = response.getStatusCode();
            return (status == HttpStatus.FORBIDDEN
                    || status == HttpStatus.BAD_GATEWAY
                    || status == HttpStatus.UNAUTHORIZED);
        }

        return false;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        if(null != response){
            String errorString = getResponse(response);

            logger.error("Status: {} - {}", response.getStatusCode(),
                    response.getStatusText());

            if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new UnauthorizedException(errorString);
            }
        }
    }

    private String getResponse(ClientHttpResponse response) throws IOException {

        if (null == response) {
            return "";
        }

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(response.getBody()));
        StringWriter writer = new StringWriter();
        IOUtils.copy(reader, writer);
        return writer.toString();
    }
}
