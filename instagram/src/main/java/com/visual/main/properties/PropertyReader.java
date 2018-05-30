package com.visual.main.properties;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
	
	public static String readProperty(String property){
		
		String value ="";
		try {
			FileReader reader = new FileReader("app.properties");
			Properties properties = new Properties();
			properties.load(reader);

			value = properties.getProperty(property);
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public static void changeProperty(String property,String value){
		FileInputStream in = null;
		try {
			in = new FileInputStream("app.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileOutputStream out = null;
		try {
			out = new FileOutputStream("app.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		props.setProperty(property, value);
		try {
			props.store(out, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
