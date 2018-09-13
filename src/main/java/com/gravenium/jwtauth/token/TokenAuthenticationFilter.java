package com.gravenium.jwtauth.token;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.gravenium.jwtauth.user.AppUser;
import com.gravenium.jwtauth.user.UserService;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
	
	private static Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);
	
	private UserService userService;
	private TokenHelper tokenHelper;
	
	public TokenAuthenticationFilter(UserService userService, TokenHelper tokenHelper) {
		this.userService = userService;
		this.tokenHelper = tokenHelper;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null) {
			if (authHeader.startsWith("Bearer ")) {
				String jwt = authHeader.substring(7);
				
				if (tokenHelper.isValid(jwt)) {
					
					String username = tokenHelper.getUsernameFromToken(jwt);
					if (username != null) {
						AppUser user = userService.findUserByUsername(username);

						// you'll need to pass authorities to the authentication object....that way spring security knows you are valid
						UsernamePasswordAuthenticationToken authentication = 
								new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), 
										Arrays.asList(new SimpleGrantedAuthority("USER")));
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
			}
		}
		filterChain.doFilter(request, response);
	}
}
