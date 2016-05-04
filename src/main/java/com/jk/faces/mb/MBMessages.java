package com.jk.faces.mb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

@ManagedBean(name="msg")
@ApplicationScoped
public class MBMessages {

	private static MBMessages defaultInstance;
	private Properties prop = new Properties();

	@PostConstruct
	// ////////////////////////////////////////////////////
	public void init() {
		try {
			InputStream instream = this.getClass().getResourceAsStream("/system_ar.properties");
			BufferedReader in = new BufferedReader(new InputStreamReader(instream, "utf8"));
			String line;
			while ((line = in.readLine()) != null) {
				String label[] = line.split("=");
				if (label.length >= 2) {
					prop.setProperty(label[0], line.substring(line.indexOf("=") + 1));
				}
			}
		} catch (IOException e) {
			System.err.println("Error while loading Lables : " + e);
			e.printStackTrace();
		}
	}

	// ////////////////////////////////////////////////////
	public String get(Object key) {
		if (key == null) {
			return "-";
		}
		String label = prop.getProperty(key.toString());
		if (label == null) {
			label = key.toString();
			System.err.println("Missing label : " + key);
			prop.put(key, key);
		}
		return label;
	}

	// ////////////////////////////////////////////////////
	public static MBMessages getInstance() {
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		MBMessages message;
		if (currentInstance != null) {
			message = (MBMessages) currentInstance.getExternalContext().getApplicationMap().get("msg");
		} else {
			System.err.println("Calling Messages.getInstance() outside Web scopes,return default instance");
			if (defaultInstance == null) {
				defaultInstance = new MBMessages();
			}
			message = defaultInstance;
		}
		return message;
	}

	// ////////////////////////////////////////////////////
	public static String getLabel(Object key) {
		return getInstance().get(key);
	}
}
