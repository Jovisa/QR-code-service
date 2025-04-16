package com.hyperskill.qrcodeapi;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


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

    @Test
    public void testValidQrCodeGeneration() {
        given()
                .queryParam("contents", "abcdef")
                .when()
                .get("/api/qrcode")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType("image/png");
    }

    @Test
    public void testInvalidCorrectionParam() {
        given()
                .queryParam("contents", "abcde")
                .queryParam("correction", "S")
                .when()
                .get("/api/qrcode")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("error", equalTo("Permitted error correction levels are L, M, Q, H"));
    }

    @Test
    public void testBlankContents() {
        given()
                .queryParam("contents", "")
                .queryParam("correction", "S")
                .when()
                .get("/api/qrcode")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("error", equalTo("Contents cannot be null or blank"));
    }

    @Test
    public void testInvalidCorrectionAndType() {
        given()
                .queryParam("contents", "abcde")
                .queryParam("correction", "S")
                .queryParam("type", "tiff")
                .when()
                .get("/api/qrcode")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("error", equalTo("Permitted error correction levels are L, M, Q, H"));
    }

    @Test
    public void testAllParamsValid() {
        given()
                .queryParam("contents", "hello")
                .queryParam("size", 300)
                .queryParam("correction", "H")
                .queryParam("type", "png")
                .when()
                .get("/api/qrcode")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType("image/png");
    }

    @Test
    public void testInvalidSizeWithValidCorrection() {
        given()
                .queryParam("contents", "abc")
                .queryParam("size", -100)
                .queryParam("correction", "H")
                .when()
                .get("/api/qrcode")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("error", equalTo("Image size must be between 150 and 350 pixels"));
    }

    @Test
    public void testInvalidTypeOnly() {
        given()
                .queryParam("contents", "abcde")
                .queryParam("type", "tiff")
                .when()
                .get("/api/qrcode")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .body("error", equalTo("Only png, jpeg and gif image types are supported"));
    }

}
