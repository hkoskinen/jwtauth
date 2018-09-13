package com.gravenium.jwtauth.token;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gravenium.jwtauth.user.AppUser;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenHelper {

	private static Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);
	private final String signingKey = "ngk5434kdsfkj4390bfn901DM-D90F5p045M-AC0FvN.F8wV.N5V8V89l4NL18j489HeWBLF90oDfSD9D4bFDSL";
	
	public String generate(AppUser user) {
		String jwt = Jwts.builder()
				.setId(user.getUsername())
				.setSubject("user-authentication")
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + 900000))
				.signWith(SignatureAlgorithm.HS512, signingKey)
				.compact();
		return jwt;
	}
	
	public boolean isValid(String jwt) {
		try {
			Jwts.parser().setSigningKey(signingKey).parse(jwt);
			return true;
		} catch(ExpiredJwtException e) {
			logger.info("JWT token is expired");
		}
		catch(Exception e) {
			logger.info("Trying to validate JWT token failed");
		}
		
		return false;
	}
	
	public String getUsernameFromToken(String jwt) {
		try {
			String username = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(jwt).getBody().getId();
			return username;
		} catch(Exception e) {
			logger.info("Failed to get the username from the token");
		}
		return null;
	}
	
}
