package com.example.demo.config;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RestAssuredService {
      private final String baseUrl;

    public RestAssuredService(@Value("${petclinic.base-url}") String baseUrl) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException("petclinic.base-url should not blanc");
        }

        this.baseUrl = removeTrailingSlash(baseUrl);

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public RequestSpecification request() {
        return RestAssured
            .given()
            .baseUri(baseUrl)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON);
    }

    public String baseUrl() { return baseUrl; }

    private String removeTrailingSlash(String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
}
