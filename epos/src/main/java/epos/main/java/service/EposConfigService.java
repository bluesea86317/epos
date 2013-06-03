package epos.main.java.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EposConfigService {

	private String configPath;
	private static Properties properties;
	
	public String getProperty(String key){
		if(properties == null){
			properties = new Properties();
			try {
				ClassLoader cl = Thread.currentThread().getContextClassLoader();
				InputStream in=cl.getResourceAsStream(configPath);
				properties.load(in);	
				in.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return properties.getProperty(key);
	}

	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}
}
