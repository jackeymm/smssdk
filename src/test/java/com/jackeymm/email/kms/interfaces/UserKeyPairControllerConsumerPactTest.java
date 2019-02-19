package com.jackeymm.email.kms.interfaces;

import au.com.dius.pact.consumer.ConsumerPactTestMk2;
import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.*;
import static org.testcontainers.shaded.org.apache.http.HttpHeaders.CONTENT_TYPE;

public class UserKeyPairControllerConsumerPactTest extends ConsumerPactTestMk2 {

    private ObjectMapper objectMapper = new ObjectMapper();
    private String path = "/register";

    @Override
    protected RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put(CONTENT_TYPE, APPLICATION_FORM_URLENCODED_VALUE);

        try {
            return builder
                    .given("Register - a10 register success")
                    .uponReceiving("request 4 register KeyPair")
                    .method("POST")
                    .body("token=smsToken&email=a10@email.com")
                    .path(path)
                    .headers(headers)
                    .willRespondWith()
                    .status(201)
                    .headers(singletonMap(CONTENT_TYPE, APPLICATION_JSON_VALUE))
                    .body(objectMapper.writeValueAsString(Response.ok(CREATED, "success")))
                    .toPact();
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected String providerName() {
        return "sms-server";
    }

    @Override
    protected String consumerName() {
        return "sms-sdk";
    }

    @Override
    protected void runTest(MockServer mockServer) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new SilentResponseErrorHandler());
        String url = mockServer.getUrl() + path;

        ResponseEntity<Response<String>> responseEntity = restTemplate.exchange(url, POST, httpEntityOf("smsToken", "a10@email.com"), responseType());
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(CREATED);
    }

    private HttpEntity<MultiValueMap<String, String>> httpEntityOf(String token, String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("token", token);
        map.add("email", email);

        return new HttpEntity<>(map, headers);
    }

    private HttpEntity<MultiValueMap<String, String>> httpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        return new HttpEntity<>(null, headers);
    }

    private ParameterizedTypeReference<Response<String>> responseType() {
        return new ParameterizedTypeReference<Response<String>>() {
        };
    }
}
