package com.example.group.restTemplate;

import com.example.group.web.exception.RoleNotFoundException;
import com.example.group.web.exception.RoleServiceDownException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

/**
 * Custom Response Handler for RestTemplate
 *
 * @author Siddharth Mehta
 */
@Component
@Slf4j
public class RoleRestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

        if (httpResponse.getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {
            log.error("Role Service Down!");
            throw new RoleServiceDownException("Role Service Down!");
        }
    }
}
