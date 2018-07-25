package com.cyf.rest.test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.response.Response;

import org.junit.Before;
import org.junit.Test;

public class RestServiceTest {
	private String token = "";

	@Before
    public void setUp() {
        RestAssured.baseURI= "http://localhost";
        RestAssured.port = 8234;
        RestAssured.basePath = "/rs/rest";
        
        this.login();
    }
	
	@Test
	public void testHelloWith401() {
		Response response = given().get("/hello");
		
		response.then().statusCode(401);
	}
	
	@Test
	public void testHelloWithAuth() {
		Response response = given().
				header(AUTHORIZATION, "Bearer " + this.token).
				get("/hello");
		
		response.then().statusCode(200);
		response.then().assertThat().body(equalTo("Hello, world!xxx"));
	}
	
	@Test
	public void testJsonWithAuth() throws IOException {
		Response response = given().
				header(AUTHORIZATION, "Bearer " + this.token).
				header("Content-Type", MediaType.APPLICATION_JSON).
				get("/hello/json");
		
		response.then().statusCode(200);
		response.then().assertThat().body(matchesJsonSchemaInClasspath("schema.json"));
	}
	
	private void login() {
		this.token = given().
				headers("Accept", MediaType.TEXT_PLAIN, "Content-Type", MediaType.APPLICATION_FORM_URLENCODED).
				auth().form("cyf", "test", new FormAuthConfig("loginForm", "login", "password")).
			when().
				post("/auth/login").asString();
	}
}
