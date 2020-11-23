package com.org.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.org.ApiUtils.TestBase;
import com.org.restClient.RestClient;

public class PostApiTest extends TestBase {
	
	String webUrl;
	String serviceUrl;
	String completeUrl;
	String statusCode201;
	RestClient restClient;
	CloseableHttpResponse rawResponse;
	
	@BeforeTest
	public void setup() {
		webUrl = propertyReader.readTestData("URL");
		serviceUrl = propertyReader.readTestData("serviceUrl");
		completeUrl = webUrl+serviceUrl;
	}
	
	@Test
	public void postHttpTestWithHeaders() throws ClientProtocolException, IOException {
		restClient = new RestClient();
		
		HashMap<String, String> hm = new HashMap<String,String>();
		hm.put("Content-Type", "application/json");
		rawResponse = restClient.postHttpWithHeader(completeUrl, hm);
		
		// Validate Status Code
		int statusCode = rawResponse.getStatusLine().getStatusCode();
		System.out.println("Status Code------> " +statusCode);
		statusCode201 = propertyReader.readTestData("StatusCode201");
		Assert.assertEquals(Integer.parseInt(statusCode201), statusCode);
		System.out.println("Status Code validated");
		
		// Response Body
		String responseBody = EntityUtils.toString(rawResponse.getEntity(), "UTF-8");
		JSONObject responseJSON = new JSONObject(responseBody);
		String[] names = responseJSON.getNames(responseJSON);
		for(int i=0; i<names.length; i++) {
			System.out.println("Name "+i+" "+names[i]);
		}
		String name1 = responseJSON.getString("name");
		System.out.println("Response Body Name--------> "+name1);
		System.out.println("Response Body-----> " + responseJSON);
		
		Header[] arr = rawResponse.getAllHeaders();
		HashMap<String, String> hm2 = new HashMap<String, String>();
		for(Header header : arr) {
			hm2.put(header.getName(), header.getValue());
		}
		
		System.out.println("Response Headers------> " + hm2);
	}

}
