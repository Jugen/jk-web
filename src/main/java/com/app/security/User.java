package com.app.security;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jk.util.JK;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sec_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;

	@Column(name = "user_name")
	String username;

	@Column(name = "password")
	String password;

	@Column(name = "first_name")
	String firstName;

	@Column(name = "last_name")
	String lastName;

	@Column(name = "email")
	String email;

	@Column(name = "disabled")
	boolean disabled;

	@Column(name = "expired")
	boolean expired;

	@Column(name = "locked")
	boolean locked;

	@Column(name = "cred_expired")
	boolean credExpired;

	@JoinColumn(name = "role_id")
	Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if(role!=null) {
			return JK.toList(role);			
		}
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !credExpired;
	}

	@Override
	public boolean isEnabled() {
		return !disabled;
	}
}
