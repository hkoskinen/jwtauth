package com.gravenium.jwtauth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public AppUser getUserByUsername(String username) {
		AppUser user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ?", 
				new Object[] { username }, new BeanPropertyRowMapper<>(AppUser.class));
		return user;
	}
	
	public int addUser(AppUser user) {
		return jdbcTemplate.update("INSERT INTO users (username, password, email) VALUES (?, ?, ?)",
				user.getUsername(), passwordEncoder.encode(user.getPassword()), user.getEmail());
	}
}
