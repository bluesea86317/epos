package epos.main.java.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class EposConfigService {
	
	private Properties properties;
	
	public EposConfigService(String propFileDir) {
		File file = new File(propFileDir);
		if (file.isFile() && propFileDir.endsWith(".properties")) {
			FileInputStream in = null;
			try {
				in = new FileInputStream(file);
				this.properties.load(in);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				
			}
		}
	}
	
	public String getProperty(String key) {
		return getProperties().getProperty(key);
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}
