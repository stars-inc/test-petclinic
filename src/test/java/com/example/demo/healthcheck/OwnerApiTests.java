package com.example.demo.healthcheck;

import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

import com.example.demo.config.RestAssuredService;
import lombok.RequiredArgsConstructor;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Import(RestAssuredService.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
public class OwnerApiTests {

  private final RestAssuredService restAssured;
  
  @Test
  @DisplayName("GET /actuator/health should return 200 && UP")
  void healthCheckShouldReturnUp() {
    restAssured.request()
      .when()
        .get("/actuator/health")
      .then()
        .statusCode(200)
        .body("status", equalTo("UP"));
  }  
}
