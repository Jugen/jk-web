package com.jk.faces.mb;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.jk.util.JK;
import com.jk.util.JKDateTimeUtil;

@ManagedBean(name="util")
@ApplicationScoped
public class MB_Util {

	public int reloadRandom() {
		//TODO: make this return same value in production envi.
		return JK.randomNumber();
	}
	
	public int currentYear() {
		return JKDateTimeUtil.getCurrentYear();
	}
}
