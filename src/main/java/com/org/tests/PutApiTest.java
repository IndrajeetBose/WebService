package com.org.tests;

import java.io.IOException;
import java.io.ObjectInputFilter.Status;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.org.ApiUtils.TestBase;
import com.org.restClient.RestClient;

public class PutApiTest extends TestBase{
	
	String webUrl;
	String serviceUrl;
	String completeUrl;
	RestClient restClient;
	CloseableHttpResponse rawResponse;
	
	@BeforeTest
	public void setup() {
		webUrl = propertyReader.readTestData("URL");
		serviceUrl = propertyReader.readTestData("PUT_ServiceUrl");
		completeUrl = webUrl + serviceUrl;
	}
	
	@Test
	public void putHttpWithHeader() throws IOException {
		restClient = new RestClient();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("Content-Type", "application/json");
		rawResponse = restClient.putHttpWithHeader(completeUrl, hm);
		
		int statusCode = rawResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code-----> "+statusCode);
		String expectedStatusCode = propertyReader.readTestData("StatusCode200");
		Assert.assertEquals(Integer.parseInt(expectedStatusCode), statusCode);
		System.out.println("Status Code validated");
		
		String responseBody = EntityUtils.toString(rawResponse.getEntity(), "UTF-8");
		JSONObject responseBodyJSON = new JSONObject(responseBody);
		System.out.println("Response Body------> "+responseBodyJSON);
		
		//Validate name JSON object
		String jsonObjectName = responseBodyJSON.optString("name");
		Assert.assertEquals("morpheus", jsonObjectName);
		System.out.println("JSON Object validated");
		
		Header[] arr = rawResponse.getAllHeaders();
		
		HashMap<String, String> hm1 = new HashMap<String, String>();
		for(Header header : arr) {
			hm1.put(header.getName(), header.getValue());
		}
		
		System.out.println("Headers--------> "+hm1);
		
	}

}
