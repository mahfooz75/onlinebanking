package com.iris.security;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.iris.domain.User;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProvider {

	// Generate the token
	public String generatetoken(Authentication auth) {
		User user = (User) auth.getPrincipal();
		String userName = user.getUsername();

		String token = Jwts.builder().setSubject(userName)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, SecurityConstants.TOKEN_SECRET).compact();
		return token;
	}

	// Validate the token
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(SecurityConstants.TOKEN_SECRET).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			System.out.println("Invalid JWT Signature");
		} catch (MalformedJwtException e) {
			System.out.println("Invalid JWT Token");
		} catch (ExpiredJwtException e) {
			System.out.println("Expired JWT Token");
		} catch (UnsupportedJwtException e) {
			System.out.println("Unsupported JWT Token");
		} catch (IllegalArgumentException e) {
			System.out.println("JWT claim string is empty");
		}
		return false;
	}

	// Get user Id from token
	public String getUserIdFromJWT(String token) {
		String user=Jwts.parser().setSigningKey(SecurityConstants.TOKEN_SECRET).parseClaimsJws(token).getBody().getSubject();
		return user;
	}
}
