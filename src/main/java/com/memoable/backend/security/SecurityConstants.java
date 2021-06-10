package com.memoable.backend.security;

/**
 * Constants for backend security
 * @author Jere Salmensaari
 */
public class SecurityConstants {
	
	/**
	 * Random secret string for encrypting JWT tokens
	 */
	public static final String SECRET = "ea4b1a1ab354aedc3a3aeca1a412313c";

	/**
	 * Token prefix for headers
	 */
	public static final String TOKEN_PREFIX = "Bearer ";

	/**
	 * Authorization header key
	 */
	public static final String HEADER_STRING = "Authorization";

	/**
	 * Sign up url
	 */
	public static final String SIGN_UP_URL = "/api/user";

	/**
	 * Sign in url
	 */
	public static final String SIGN_IN_URL = "/api/login";
}
