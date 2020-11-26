package com.org.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.org.ApiUtils.TestBase;
import com.org.restClient.RestClient;

public class DeleteApiTest extends TestBase {

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
	public void deleteHttpWithHeader() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("Content-Type", "application/json");
		
		rawResponse = restClient.deleteHttpWithHeader(completeUrl, hm);
		
		// Validate Status
		int statusCode = rawResponse.getStatusLine().getStatusCode();
		String expectedStatusCode = propertyReader.readTestData("StatusCode204");
		Assert.assertEquals(statusCode, Integer.parseInt(expectedStatusCode));
		System.out.println("Status Code Validated------> "+statusCode);
		System.out.println("Status Code Validated------> "+statusCode);
		
	}

}
