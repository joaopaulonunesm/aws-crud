package com.project.aws.crud.employee.controller;

import com.project.aws.crud.employee.dto.EmployeeDTO;
import com.project.aws.crud.employee.model.Employee;
import com.project.aws.crud.employee.repository.EmployeeRepository;
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

import java.util.Arrays;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

    public static final String URL_BASE = "/employees";
    private static final String ID = "851bc83d-6b63-4be3-955c-8ff377777d8d";
    private static final String DOCUMENT = "11111111111";
    private static final String NAME = "Joao";

    @MockBean
    private EmployeeRepository employeeRepository;

    @LocalServerPort
    private int port;

    @BeforeEach
    public void before() {
        RestAssured.port = this.port;
    }

    @Test
    public void create(){
        Employee employeeMock = Employee.builder().document(DOCUMENT).firstName(NAME).build();

        when(employeeRepository.findByDocument(employeeMock.getDocument())).thenReturn(Optional.empty());
        when(employeeRepository.save(employeeMock)).thenReturn(employeeMock);

        createPost(URL_BASE, employeeMock.toDTO(), HttpStatus.OK)
                .body("document", equalTo(employeeMock.getDocument()))
                .body("firstName", equalTo(employeeMock.getFirstName()));
    }

    @Test
    public void createWithErrorWhenNotHaveDocument(){
        EmployeeDTO employeeDTOMock = EmployeeDTO.builder().firstName(NAME).build();
        createPost(URL_BASE, employeeDTOMock, HttpStatus.BAD_REQUEST);
    }

    @Test
    public void findById(){
        Employee employeeMock = Employee.builder().document(DOCUMENT).firstName(NAME).build();

        when(employeeRepository.findById(ID)).thenReturn(Optional.of(employeeMock));

        createGet(URL_BASE + "/" + ID, HttpStatus.OK)
                .body("document", equalTo(employeeMock.getDocument()))
                .body("firstName", equalTo(employeeMock.getFirstName()));
    }

    @Test
    public void findByDocument(){
        Employee employeeMock = Employee.builder().document(DOCUMENT).firstName(NAME).build();

        when(employeeRepository.findByDocument(DOCUMENT)).thenReturn(Optional.of(employeeMock));

        createGet(URL_BASE + "/document/" + DOCUMENT, HttpStatus.OK)
                .body("document", equalTo(employeeMock.getDocument()))
                .body("firstName", equalTo(employeeMock.getFirstName()));
    }

    @Test
    public void findAll(){
        Employee employee1Mock = Employee.builder().document(DOCUMENT).firstName(NAME).build();
        Employee employee2Mock = Employee.builder().document(DOCUMENT).firstName(NAME).build();

        when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee1Mock, employee2Mock));

        createGet(URL_BASE, HttpStatus.OK)
                .body("", hasSize(2));
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

    @Test
    public void update(){
        Employee employeeMock = Employee.builder().id(ID).document(DOCUMENT).firstName(NAME).build();

        when(employeeRepository.findById(employeeMock.getId())).thenReturn(Optional.of(employeeMock));

        Employee employeeChangedMock = Employee.builder().id(ID).document(DOCUMENT).firstName(NAME + "CHANGED").build();
        when(employeeRepository.save(employeeChangedMock)).thenReturn(employeeChangedMock);

        createPut(URL_BASE + "/" + ID, employeeChangedMock.toDTO(), HttpStatus.OK)
                .body("document", equalTo(employeeChangedMock.getDocument()))
                .body("firstName", equalTo(employeeChangedMock.getFirstName()));
    }

    @Test
    public void delete(){
        Employee employeeMock = Employee.builder().id(ID).document(DOCUMENT).firstName(NAME).build();
        when(employeeRepository.findById(employeeMock.getId())).thenReturn(Optional.of(employeeMock));

        doNothing().when(employeeRepository).delete(employeeMock);

        createDelete(URL_BASE + "/" + ID, HttpStatus.NO_CONTENT);
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
