package com.gravenium.jwtauth.user;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class AppUser {

	private long id;
	private String username;
	private String password;
	private String email;
	private Timestamp created;
	private List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("USER"));
	
	public AppUser() {
		
	}
	
	public AppUser(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public AppUser(long id, String username, String password, String email, Timestamp created) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.created = created;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
}
