package com.example.demo.owner;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasLength;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static com.example.demo.owner.data.OwnerDto.defaultOwnerRequest;
import static com.example.demo.owner.data.OwnerDto.invalidOwnerRequest;
import static com.example.demo.owner.data.OwnerDto.updatedOwnerRequest;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestConstructor.AutowireMode;

import com.example.demo.DemoApplication;
import com.example.demo.config.RestAssuredService;

import io.restassured.response.Response;
import lombok.RequiredArgsConstructor;


@SpringBootTest(classes = DemoApplication.class, webEnvironment = WebEnvironment.NONE)
@Import(RestAssuredService.class)
@TestConstructor(autowireMode = AutowireMode.ALL)
@RequiredArgsConstructor
public class OwnerApiTest {

  private final RestAssuredService restAssured;
  private static final String OWNERS_ENDPOINT = "/api/owners";
  
  @Test
  @DisplayName("Owner CRUD flow: create, get, update, delete, get after delete")
  void ownerCrudFTest() {

    Map<String, Object> createOwnerRequest = defaultOwnerRequest();

    // Create Owner
    Integer ownerId = restAssured
      .request()
        .body(createOwnerRequest)
          .when()
            .post(OWNERS_ENDPOINT)
          .then()
              .statusCode(201)
              .body("id", notNullValue())
              .body("firstName", equalTo("TestFirstName"))
              .body("lastName", equalTo("TestLastName"))
              .body("address", equalTo("Test address"))
              .body("city", equalTo("Vilnius"))
              .body("telephone", equalTo("8600000012"))
              .extract()
              .path("id");

    assertNotNull(ownerId, "should return owner's ID");

    // Get Owner by ID
    restAssured
      .request()
      .pathParam("ownerId", ownerId)
      .when()
        .get(OWNERS_ENDPOINT + "/{ownerId}")
      .then()
        .statusCode(200)
        .body("id", equalTo(ownerId))
        .body("firstName", equalTo("TestFirstName"))
        .body("lastName", equalTo("TestLastName"))
        .body("address", equalTo("Test address"))
        .body("city", equalTo("Vilnius"))
        .body(
          "telephone", 
          allOf(
            not(blankOrNullString()),
            hasLength(greaterThanOrEqualTo(10))
          )
        );

    // Update Owner by ID
    Map<String, Object> updateOwnerRequest = updatedOwnerRequest();

    restAssured
      .request()
      .pathParam("ownerId", ownerId)
      .body(updateOwnerRequest)
        .when()
          .put(OWNERS_ENDPOINT + "/{ownerId}")
        .then()
          .statusCode(204);

    // Get Owner after update
    restAssured
      .request()
      .pathParam("ownerId", ownerId)
        .when()
          .get(OWNERS_ENDPOINT + "/{ownerId}")
        .then()
          .statusCode(200)
          .body("id", equalTo(ownerId))
          .body("firstName", equalTo(updateOwnerRequest.get("firstName")));
  }

  @Test
  @DisplayName("POST /api/owners with invalid data returns validation error")
  void invalidOwnerShouldReturnValidationError() {

    Map<String, Object> invalidOwnerRequest = invalidOwnerRequest();

    Response response = restAssured
      .request()
        .body(invalidOwnerRequest)
          .when()
            .post(OWNERS_ENDPOINT)
          .then()
            .statusCode(400)
            .extract()
            .response();

    String responseBody = response.asString();

    assertThat(responseBody, not(blankOrNullString()));

    assertThat(
      responseBody,
      anyOf(
        containsString("errors"),
        containsStringIgnoringCase("validation")
      )
    );
  }
}
