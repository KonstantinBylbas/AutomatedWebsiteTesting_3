package org.example;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Lab3 {
    private static final String baseUrl = "https://petstore.swagger.io/v2";
    private static final  String PET = "/pet",
            PET_ID = PET + "/{petId}";


    @BeforeClass
    public void setup(){
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test
    public  void verifyGETAction(){
        given().pathParam("petId", Integer.toString(Faker.instance().number().numberBetween(1,5))).get(PET_ID).then().statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "verifyGETAction")
    public  void verifyPUTAction(){
        Map<String, ?> body = Map.of(
                "id", Integer.toString(Faker.instance().number().numberBetween(1,5)),
                "name", Faker.instance().animal().name(),
                "status", Integer.valueOf("1")
        );
        given().body(body).put(PET).then().statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "verifyGETAction")
    public  void verifyPOSTAction(){
        Map<String, ?> body = Map.of(
                "name", Faker.instance().animal().name(),
                "status", Integer.valueOf("1")
        );
        given().body(body).post(PET).then().statusCode(HttpStatus.SC_OK);
    }
}