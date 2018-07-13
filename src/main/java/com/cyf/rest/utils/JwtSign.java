package com.cyf.rest.utils;

import java.util.Base64;

import com.auth0.jwt.algorithms.Algorithm;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;

public enum JwtSign {
	Secret("secret_cyf_123456");

	private final String secret;

	JwtSign(final String secret) {
		this.secret = secret;
	}

	public Algorithm asBase64() {
		return HMAC256(Base64.getEncoder().encode(secret.getBytes()));
	}

	public Algorithm asPlain() {
		return HMAC256(secret.getBytes());
	}
}