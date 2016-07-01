/*
 * Copyright 2002-2016 Jalal Kiswani.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

/**
 * The Class MBMessages.
 *
 * @author Jalal Kiswani
 */
@ManagedBean(name="msg")
@ApplicationScoped
public class MBMessages {

	private static MBMessages defaultInstance;
	private Properties prop = new Properties();

	/**
	 * Inits the.
	 */
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

	/**
	 * Gets the.
	 *
	 * @param key
	 *            the key
	 * @return the string
	 */
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

	/**
	 * Gets the single instance of MBMessages.
	 *
	 * @return single instance of MBMessages
	 */
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

	/**
	 * Gets the label.
	 *
	 * @param key
	 *            the key
	 * @return the label
	 */
	// ////////////////////////////////////////////////////
	public static String getLabel(Object key) {
		return getInstance().get(key);
	}
}
