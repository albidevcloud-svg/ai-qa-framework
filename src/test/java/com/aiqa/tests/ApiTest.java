package com.aiqa.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

public class ApiTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void getSinglePost() {
        given()
                .when().get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", equalTo(1))
                .body("title", notNullValue());
    }

    @Test
    public void getAllPosts() {
        given()
                .when().get("/posts")
                .then()
                .statusCode(200)
                .body("$", hasSize(100));
    }

    @Test
    public void createPost() {
        String body = """
                {
                  "title": "My test post",
                  "body": "Created by automation",
                  "userId": 1
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when().post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("My test post"))
                .body("id", notNullValue());
    }

    @Test
    public void updatePost() {
        String body = """
                {
                  "id": 1,
                  "title": "Updated title",
                  "body": "Updated body",
                  "userId": 1
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when().put("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Updated title"));
    }

    @Test
    public void deletePost() {
        given()
                .when().delete("/posts/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void getMissingPostReturns404() {
        given()
                .when().get("/posts/99999")
                .then()
                .statusCode(404);
    }
}
