package com.project.aws.crud.product.controller;

import com.project.aws.crud.product.dto.ProductDTO;
import com.project.aws.crud.product.model.Product;
import com.project.aws.crud.product.repository.ProductRepository;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    public static final String URL_BASE = "/products";

    private static final String ID = "851bc83d-6b63-4be3-955c-8ff377777d8d";
    private static final String NAME = "Name Product";
    private static final String SKU = "SKU";
    private static final BigDecimal PRICE = new BigDecimal("110");


    @MockBean
    private ProductRepository productRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void before() {
        RestAssured.port = this.port;
    }

    @Test
    public void create(){
        Product ProductMock = Product.builder().sku(SKU).name(NAME).price(PRICE).build();

        when(productRepository.findBySku(ProductMock.getSku())).thenReturn(Optional.empty());
        when(productRepository.save(ProductMock)).thenReturn(ProductMock);

        createPost(URL_BASE, ProductMock.toDTO(), HttpStatus.OK)
                .body("sku", equalTo(ProductMock.getSku()))
                .body("name", equalTo(ProductMock.getName()));
    }

    @Test
    public void createWithErrorWhenNotHaveDocument(){
        ProductDTO productDTOMock = ProductDTO.builder().build();
        createPost(URL_BASE, productDTOMock, HttpStatus.BAD_REQUEST);
    }

    @Test
    public void findAll(){
        Product Product1Mock = Product.builder().sku(SKU + "1").name(NAME).price(PRICE).build();
        Product Product2Mock = Product.builder().sku(SKU + "2").name(NAME).price(PRICE).build();

        when(productRepository.findAll()).thenReturn(Arrays.asList(Product1Mock, Product2Mock));

        createGet(URL_BASE, HttpStatus.OK)
                .body("", hasSize(2));
    }

    @Test
    public void update(){
        Product ProductMock = Product.builder().id(ID).sku(SKU).name(NAME).price(PRICE).build();

        when(productRepository.findById(ProductMock.getId())).thenReturn(Optional.of(ProductMock));

        Product ProductChangedMock = Product.builder().id(ID).sku(SKU).name(NAME + "CHANGED").price(PRICE).build();
        when(productRepository.save(ProductChangedMock)).thenReturn(ProductChangedMock);

        createPut(URL_BASE + "/" + ID, ProductChangedMock.toDTO(), HttpStatus.OK)
                .body("sku", equalTo(ProductMock.getSku()))
                .body("name", equalTo(ProductMock.getName()));
    }

    @Test
    public void delete(){
        Product ProductMock = Product.builder().id(ID).sku(SKU).name(NAME).price(PRICE).build();
        when(productRepository.findById(ProductMock.getId())).thenReturn(Optional.of(ProductMock));

        doNothing().when(productRepository).delete(ProductMock);

        createDelete(URL_BASE + "/" + ID, HttpStatus.NO_CONTENT);
    }

    private static ValidatableResponse createPost(String url, Object body, HttpStatus httpStatus) {
        return given().
                spec(createRequestSpecification(body)).
                log().
                all().
                when().
                post(url).
                then().
                statusCode(httpStatus.value());
    }

    private static ValidatableResponse createPut(String url, Object body, HttpStatus httpStatus) {
        return given().
                    spec(createRequestSpecification(body)).
                    log().
                        all().
                when().
                    put(url).
                then().
                    statusCode(httpStatus.value());
    }

    private static ValidatableResponse createDelete(String url, HttpStatus httpStatus) {
        return given().
                    log().
                        all().
                when().
                    delete(url).
                then().
                    statusCode(httpStatus.value());
    }

    private static ValidatableResponse createGet(String url, HttpStatus httpStatus) {
        return given().
                    log().
                        all().
                when().
                    get(url).
                then().
                    statusCode(httpStatus.value());
    }

    private static ValidatableResponse createGet(String url, RequestSpecification requestSpecification, HttpStatus httpStatus) {
        return given().
                    spec(requestSpecification).
                    log().
                        all().
                when().
                    get(url).
                then().
                    statusCode(httpStatus.value());
    }


    private static RequestSpecification createRequestSpecification(Object body) {
        return new RequestSpecBuilder().setContentType(ContentType.JSON)
                .addHeader("Accept", ContentType.JSON.toString())
                .setBody(body)
                .build();
    }
}
