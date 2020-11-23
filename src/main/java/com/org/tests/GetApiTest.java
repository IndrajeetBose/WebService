package com.org.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.org.ApiUtils.PropertyReader;
import com.org.ApiUtils.TestBase;
import com.org.restClient.RestClient;

public class GetApiTest extends TestBase {

	// TestBase tb;
	RestClient restClient;

	String siteUrl;
	String serviceUrl;
	String completeServiceUrl = "";
	CloseableHttpResponse rawResponse;

	@BeforeTest
	public void setup() {
		System.out.println("Execution Started");
		siteUrl = propertyReader.readTestData("URL");
		System.out.println(siteUrl);
		serviceUrl = propertyReader.readTestData("serviceUrl");
		System.out.println(serviceUrl);
		completeServiceUrl = siteUrl + serviceUrl;
		System.out.println(completeServiceUrl);
	}

	@Test(priority = 1)
	public void getApiTestWithoutHeader() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		rawResponse = restClient.getHttp(completeServiceUrl);

		// Status Code
		int statusCode = rawResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code------> " + statusCode);
		
		String expectedStatusCode = propertyReader.readTestData("StatusCode200");
		Assert.assertEquals(Integer.parseInt(expectedStatusCode), statusCode);
		System.out.println("Status Code Verified");

		// Response Body
		String responseBody = EntityUtils.toString(rawResponse.getEntity(), "UTF-8");
		// Converting the String to JSON format using JSONObject Class by passing the
		// string object to its constructor
		JSONObject responseBodyJSON = new JSONObject(responseBody);
		System.out.println("String JSON Response----------> " + responseBodyJSON);
		System.out.println("Getting JSON values---------> " + responseBodyJSON.get("per_page"));

		JSONArray jarr = new JSONArray(responseBodyJSON.getJSONArray("data").toString());
		for (int i = 0; i < jarr.length(); i++) {
			JSONObject jo = jarr.getJSONObject(i);
			String lastName = (String) jo.get("first_name");
			System.out.println("JSON Array Last Name----> " + lastName);
		}
		
		//Validating first element of JSON Array
		JSONObject jo = jarr.getJSONObject(0);
		String lastName = jo.optString("last_name");
		String id = jo.optString("id");
		String avatar = jo.optString("avatar");
		String firstName = jo.optString("first_name");
		String email = jo.optString("email");
		
		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);
		System.out.println(email);
		
		String expectedLastName = propertyReader.readTestData("ExpectedLastName");
		String expectedID = propertyReader.readTestData("ExpectedID");
		String expectedAvatar = propertyReader.readTestData("ExpectedAvatar");
		String expectedFirstName = propertyReader.readTestData("ExpectedFirstName");
		String expectedEmail = propertyReader.readTestData("ExpectedEmail");
		
		Assert.assertEquals(expectedLastName, lastName);
		Assert.assertEquals(expectedID, id);
		Assert.assertEquals(expectedAvatar, avatar);
		Assert.assertEquals(expectedFirstName, firstName);
		Assert.assertEquals(expectedEmail, email);
		
		System.out.println("First Array of JSON Array validated");

		// Response Headers
		Header[] arr = rawResponse.getAllHeaders();

		// Printing all the header value in Name:Value format using HashMap
		// Header Class contains getName() and getValue Methods

		HashMap<String, String> hm = new HashMap<String, String>();

		// Iterating Header array elements
		for (Header header : arr) {
			hm.put(header.getName(), header.getValue());
		}
		System.out.println("JSON Response------> " + hm);
	}
	
	@Test(priority = 2)
	public void getApiTestWithHeader() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		
		//Passing Header
		HashMap<String,String> hm = new HashMap<String , String>();
		hm.put("Content-Type", "application/jason");
		
		rawResponse = restClient.getHttpWithHeader(completeServiceUrl, hm);

		// Status Code
		int statusCode = rawResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code------> " + statusCode);
		
		String expectedStatusCode = propertyReader.readTestData("StatusCode200");
		Assert.assertEquals(Integer.parseInt(expectedStatusCode), statusCode);
		System.out.println("Status Code Verified");

		// Response Body
		String responseBody = EntityUtils.toString(rawResponse.getEntity(), "UTF-8");
		// Converting the String to JSON format using JSONObject Class by passing the
		// string object to its constructor
		JSONObject responseBodyJSON = new JSONObject(responseBody);
		System.out.println("String JSON Response----------> " + responseBodyJSON);
		System.out.println("Getting JSON values---------> " + responseBodyJSON.get("per_page"));

		JSONArray jarr = new JSONArray(responseBodyJSON.getJSONArray("data").toString());
		for (int i = 0; i < jarr.length(); i++) {
			JSONObject jo = jarr.getJSONObject(i);
			String lastName = (String) jo.get("first_name");
			System.out.println("JSON Array Last Name----> " + lastName);
		}
		
		//Validating first element of JSON Array
		JSONObject jo = jarr.getJSONObject(0);
		String lastName = jo.optString("last_name");
		String id = jo.optString("id");
		String avatar = jo.optString("avatar");
		String firstName = jo.optString("first_name");
		String email = jo.optString("email");
		
		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);
		System.out.println(email);
		
		String expectedLastName = propertyReader.readTestData("ExpectedLastName");
		String expectedID = propertyReader.readTestData("ExpectedID");
		String expectedAvatar = propertyReader.readTestData("ExpectedAvatar");
		String expectedFirstName = propertyReader.readTestData("ExpectedFirstName");
		String expectedEmail = propertyReader.readTestData("ExpectedEmail");
		
		Assert.assertEquals(expectedLastName, lastName);
		Assert.assertEquals(expectedID, id);
		Assert.assertEquals(expectedAvatar, avatar);
		Assert.assertEquals(expectedFirstName, firstName);
		Assert.assertEquals(expectedEmail, email);
		
		System.out.println("First Array of JSON Array validated");

		// Response Headers
		Header[] arr = rawResponse.getAllHeaders();

		// Printing all the header value in Name:Value format using HashMap
		// Header Class contains getName() and getValue Methods

		HashMap<String, String> hm1 = new HashMap<String, String>();

		// Iterating Header array elements
		for (Header header : arr) {
			hm.put(header.getName(), header.getValue());
		}
		System.out.println("JSON Response------> " + hm);
	}

}
