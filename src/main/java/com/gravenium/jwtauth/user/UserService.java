package com.gravenium.jwtauth.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public int save(AppUser user) {
		return userRepository.addUser(user);
	}
	
	public AppUser findUserByUsername(String username) {
		return userRepository.getUserByUsername(username);
	}
}
