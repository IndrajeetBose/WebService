package com.org.ApiUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class PropertyReader {

	String path = getPath();

	public String getPath() {
		File f = new File("");
		String extractedFilePath = f.getAbsolutePath();
		String filePath = extractedFilePath.replaceAll("\\\\+", "/");
		return filePath;
	}

	public String readTestData(String key) {
		String value = "";
		try {
			Properties p = new Properties();
			File f = new File(path + "//Test Data//TestData.properties");
			if (f.exists()) {
				p.load(new FileInputStream(f));
				value = p.getProperty(key);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return value;
	}

	/*
	 * public static void main(String[] args) { PropertyReader p = new
	 * PropertyReader(); System.out.println(p.readTestData("serviceUrl")); }
	 */

}
