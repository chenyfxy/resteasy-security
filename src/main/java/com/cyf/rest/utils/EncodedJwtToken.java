package com.cyf.rest.utils;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import static java.time.LocalDateTime.now;
import static java.time.ZoneId.systemDefault;

import com.auth0.jwt.JWT;

public final class EncodedJwtToken {
	private final JwtSign sign;

	public EncodedJwtToken(final JwtSign sign) {
		this.sign = sign;
	}

	public String issue(final String issuer, final String principal,
			final LocalDateTime refreshExp) {
		return JWT
				.create()
				.withAudience(principal)
				.withIssuer(issuer)
				.withIssuedAt(
						Date.from(now().atZone(systemDefault()).toInstant()))
				.withExpiresAt(
						Date.from(now().plusMinutes(1).atZone(systemDefault())
								.toInstant()))
				.withSubject("Resteasy jwt")
				.withClaim(
						"refreshToken",
						Base64.getEncoder().encodeToString(
								UUID.randomUUID().toString().getBytes()))
				.withClaim(
						"refreshExpire",
						Date.from(refreshExp.atZone(systemDefault())
								.toInstant())).sign(sign.asBase64());
	}
}