package com.hyperskill.qrcodeapi;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class QrCodeRestAssuredTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void testHealthEndpoint() {
        given()
                .when()
                .get("/api/health")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void testQrCodeEndpoint_withValidParams() {
        given()
                .queryParam("contents", "hello world")
                .queryParam("size", 300)
                .queryParam("correction", "M")
                .queryParam("type", "png")
                .when()
                .get("/api/qrcode")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType("image/png");
    }

    @Test
    void testQrCodeEndpoint_withInvalidCorrection() {
        given()
                .queryParam("contents", "hello")
                .queryParam("correction", "Z")
                .when()
                .get("/api/qrcode")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
