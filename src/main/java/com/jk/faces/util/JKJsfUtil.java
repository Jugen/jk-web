package com.jk.faces.util;

import com.jk.faces.config.JKNamespace;

public class JKJsfUtil {

	public static String getLocalNameFromQName(String qName) {
		String[] split = qName.split(":");
		return split.length == 2 ? split[1] : split[0];
	}

	public static String getNamespaceLetterFromQName(String qName) {
		String[] split = qName.split(":");
		if (split.length == 1) {
			return null;
		}
		return split[0];

	}

}
