package com.iris.security;

public class SecurityConstants {
	/*
	 * public static final String SIGN_UP_URLS = "/api/users/**"; public static
	 * final String H2_URL="h2-console/**"; public static final String
	 * SECRET="SecretKeyToGenJWTs"; public static final String
	 * TOKEN_PREFIX="Bearer "; public static final String
	 * HEADER_STRING="Authorization"; public static final long
	 * EXPIRATION_TIME=300_000; // 300 SECONDS
	 */	
	private SecurityConstants() {
		
	}
	
	public static final long EXPIRATION_TIME = 864000000; // 10 Days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users";
	public static final String TOKEN_SECRET = "jf9i4jgu83nfl0";
	public static final String H2_URL="h2-console/**";
}
