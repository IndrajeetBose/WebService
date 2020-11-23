package com.org.restAssuredTests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.org.ApiUtils.TestBase;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestAssuredTestGET_POST extends TestBase{
	
	String webUrl;
	String serviceUrl;
	String path = getFilePath();
	
	@BeforeTest
	public void setup() {
		webUrl = propertyReader.readTestData("URL");
		serviceUrl = propertyReader.readTestData("serviceUrl");
	}
	
	public String getFilePath() {
		File f = new File("");
		String absFilePath = f.getAbsolutePath();
		String filePath = absFilePath.replace("\\\\+", "/");
		return filePath;
	}
	
	@Test(priority = 1)
	public void GET_API_Test() {
		
		// Add the base URL
		RestAssured.baseURI = webUrl;
		
		// Request object
		RequestSpecification request = RestAssured.given();
		
		// Response Object
		Response response = request.request(Method.GET, serviceUrl);
		
		// Get Status Code
		int statusCode = response.getStatusCode();
		System.out.println("Status Code-----> "+statusCode);
	}
	
	@Test(priority = 2)
	public void POST_API_Test() throws IOException {
		
		RestAssured.baseURI = webUrl;
		
		RequestSpecification request = RestAssured.given();
		
		// Adding Header to POST request
		request.header("Content-Type", "application/json");
		
		// Reading JSON payload file
		String filePath = path +"//JSON//POST.json";
		String stringJson = new String(Files.readAllBytes(Paths.get(filePath)));
		JSONObject jsonPayload = new JSONObject(stringJson);
		request.body(jsonPayload.toString());
		
		// Response Object
		Response response = request.request(Method.POST,serviceUrl);
		
		// Status Code
		int statusCode = response.getStatusCode();
		System.out.println("Post Status Code----> "+statusCode);
		
		// Response Body
		System.out.println("Response Body RestAssured-----> "+response.getBody().asString());
		
		String name = jsonPayload.optString("name");
		System.out.println("Name is---> " + name);
	}

}
