package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ReadProperty {

	public static String getPropertyValue(String key) {
		FileReader reader = null;
		try {
			reader = new FileReader(".//TestData//Configuration.properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
	      
	    Properties p=new Properties();  
	    try {
			p.load(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}  
	      
	    return p.getProperty(key);  
	}

	public static void setPropertyValue(String key, String value) {
		PropertiesConfiguration properties;
		try {
			properties = new PropertiesConfiguration(".//TestData//Configuration.properties");
			properties.setProperty(key, value);
			properties.save();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
}
