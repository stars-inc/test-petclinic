package com.example.demo.owner.data;

import java.util.Map;

public class OwnerDto {

    private OwnerDto() {}

    public static Map<String, Object> createOwnerRequest(
      String firstName,
      String lastName,
      String address,
      String city,
      String telephone
    ) {
        return Map.of(
          "firstName", firstName,
          "lastName", lastName,
          "address", address,
          "city", city,
          "telephone", telephone
        );
    }

    // Выявлен дефект на уровне OpenAPI спецификации, валидация номера 1-20 char, result -> min 10 char
    public static Map<String, Object> defaultOwnerRequest() {
        return createOwnerRequest(
          "TestFirstName",
          "TestLastName",
          "Test address",
          "Vilnius",
          "8600000012"
        );
    }

    // Выявлен дефект на уровне OpenAPI спецификации, PUT expected status code 200 but response 204, no response entity
    // Выявлен дефект на уровне OpenAPI спецификации, DELETE expected status code 200 but response 204, no response entity
    public static Map<String, Object> updatedOwnerRequest() {
        return createOwnerRequest(
          "UpdatedFirstName",
          "UpdatedLastName",
          "Updated address",
          "Kaunas",
          "8600000023"
        );
    }

    public static Map<String, Object> invalidOwnerRequest() {
        return createOwnerRequest(
          "",
          "",
          "",
          "",
          ""
        );
    }
}