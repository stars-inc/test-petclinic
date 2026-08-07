package com.example.demo.config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RestAssuredService {
  private final RequestSpecification requestSpecification;

  public RestAssuredService(@Value("${petclinic.base-url}") String baseUrl) {
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

    this.requestSpecification = new RequestSpecBuilder()
      .setBaseUri(removeTrailingSlash(baseUrl))
      .setContentType(ContentType.JSON)
      .setAccept(ContentType.JSON)
      .build();
  }

  public RequestSpecification spec() { return requestSpecification; }

    private String removeTrailingSlash(String url) {
      if (url == null || url.isBlank()) {
        throw new IllegalArgumentException("baseUlr -> not be blank");
      }

        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }
}
