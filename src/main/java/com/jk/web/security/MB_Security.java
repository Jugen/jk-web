package com.jk.web.security;

import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.jk.util.factory.JKFactory;
import com.jk.web.controllers.JKManagedBeanWithOrmSupport;

@ManagedBean(name = "mbSecurity")
@ViewScoped
public class MB_Security extends JKManagedBeanWithOrmSupport<User> {
	String password;
	String newPassword;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String changePassword() {
		String userName = getUserName();
		UserService user = JKFactory.instance(UserService.class);
		user.updateUserPassword(userName, password, newPassword);
		return "/index.xhtml";
	}

	public boolean hasRole(String role) {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals(role)) {
				return true;
			}
		}
		return false;
	}
}
