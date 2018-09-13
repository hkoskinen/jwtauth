package com.gravenium.jwtauth.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gravenium.jwtauth.token.TokenHelper;
import com.gravenium.jwtauth.user.AppUser;
import com.gravenium.jwtauth.user.UserService;

@RestController
public class MainController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@CrossOrigin
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	@CrossOrigin
	@PostMapping("/register")
	public String register(@RequestBody Map<String, String> params) {
		return "register";
	}
	
	@CrossOrigin
	@PostMapping("/auth/login")
	public String log(@RequestBody Map<String, String> params) {
		String username = params.get("username");
		String password = params.get("password");
		if (username == null || password == null) return "Username and password are needed";
		
		AppUser user = userService.findUserByUsername(username);
		if (user == null) return "Username not exists";
		
		if (!passwordEncoder.matches(password, user.getPassword())) return "Username and password don't match";
		
		TokenHelper helper = new TokenHelper();
		String jwt = helper.generate(user);
		return jwt;
	}
	
	@CrossOrigin
	@PostMapping("/refresh")
	public String refresh(@RequestBody Map<String, String> params) {
		String username = params.get("username");
		AppUser user = userService.findUserByUsername(username);
		if (user == null) return "Username not exists";
		
		TokenHelper helper = new TokenHelper();
		String jwt = helper.generate(user);
		return jwt;
	}
	
}
