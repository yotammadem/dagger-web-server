package com.yotam.webserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.inject.Singleton;

@Singleton
public class ServerConfiguration {

	
	public static final String WWW_HOME = "wwwHome";
	public static final String PORT = "port";
	
	private Properties properties;
	private File configFile;

	public ServerConfiguration(String configFileName) throws IOException {
		File configFile = new File(configFileName);
		this.properties = new Properties();
		FileInputStream inp = new FileInputStream(configFile);
		properties.load(inp);
		inp.close();
		this.configFile = configFile;
	}

	public int getPort(){
		return Integer.valueOf(getStringProperty(PORT));
	}
	
	public String getWWWHome() {
		return getStringProperty(WWW_HOME);
	}

	private String getStringProperty(String propName) {
		String value = properties.getProperty(propName);
		if (value == null){
			throw new Error("Cannot find property: "+propName+" in the server's configuration file: "+configFile.getAbsolutePath());
		}
		return value;
	}

	
}
