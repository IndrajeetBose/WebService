package com.org.restClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpDateGenerator;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.org.ApiUtils.PropertyReader;


public class RestClient {
	
	String filePath = getFilepath();

	public String getFilepath() {
		File f = new File("");
		String filePath = f.getAbsolutePath();
		String updatedPath = filePath.replace("\\\\+", "/");
		
		return updatedPath;
	}

	public CloseableHttpResponse getHttp(String url) throws ClientProtocolException, IOException {
		// Create connection
		CloseableHttpClient apiCon = HttpClients.createDefault();

		// Create a get request using HttpGet Class and pass URL as parameter
		HttpGet getRequest = new HttpGet(url);

		// Execute your request and get a response and store it in CloseableHttpResponse
		// object
		CloseableHttpResponse rawResponse = apiCon.execute(getRequest);

		return rawResponse;

	}

	public CloseableHttpResponse getHttpWithHeader(String url, HashMap<String, String> hm)
			throws ClientProtocolException, IOException {
		// Create connection
		CloseableHttpClient apiCon = HttpClients.createDefault();

		// Create a get request using HttpGet Class and pass URL as parameter
		HttpGet getRequest = new HttpGet(url);
		
		
		for (Map.Entry<String, String> value : hm.entrySet()) {
			getRequest.addHeader(value.getKey(), value.getValue());
		}

		// Execute your request and get a response and store it in CloseableHttpResponse
		// object
		CloseableHttpResponse rawResponse = apiCon.execute(getRequest);

		return rawResponse;

	}
	
	public CloseableHttpResponse postHttpWithHeader(String url, HashMap<String, String> hm) throws ClientProtocolException, IOException {
		CloseableHttpClient con = HttpClients.createDefault();
		HttpPost postRequest = new HttpPost(url);
		String jsonFilePath = filePath + "//JSON//POST.json" ;
		String jsonString = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
		System.out.println("Inside Method-----> "+jsonString);
		postRequest.setEntity(new StringEntity(jsonString.toString()));
		for(Map.Entry<String, String> value : hm.entrySet()) {
			postRequest.addHeader(value.getKey(), value.getValue());
		}
		CloseableHttpResponse rawResponse = con.execute(postRequest);
		return rawResponse;
	}
	
	public CloseableHttpResponse putHttpWithHeader(String url, HashMap<String,String> hm) throws IOException {
		CloseableHttpClient con = HttpClients.createDefault();
		HttpPut putRequest = new HttpPut(url);
		String path = filePath + "//JSON/PUT.json";
		String jsonString = new String(Files.readAllBytes(Paths.get(path)));
		System.out.println("PUT JSON------> "+jsonString);
		putRequest.setEntity(new StringEntity(jsonString));
		for(Map.Entry<String, String> value : hm.entrySet()) {
			putRequest.addHeader(value.getKey(), value.getValue());
		}
		CloseableHttpResponse rawResponse = con.execute(putRequest);
		return rawResponse;
	}
	
	public CloseableHttpResponse deleteHttpWithHeader(String url, HashMap<String, String> hm) throws ClientProtocolException, IOException {
		CloseableHttpClient con = HttpClients.createDefault();
		HttpDelete deleteRequest = new HttpDelete(url);
		for(Map.Entry<String, String> value : hm.entrySet()) {
			deleteRequest.addHeader(value.getKey(), value.getValue());
		}
		CloseableHttpResponse rawResponse = con.execute(deleteRequest);
		return rawResponse;
	}

}
