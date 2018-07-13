package com.cyf.rest.utils;

import java.time.LocalDateTime;

public final class JwtToken {
	private final String issuer;
	private final String principal;
	private final LocalDateTime refreshExp;

	public JwtToken(String issuer, String principal, LocalDateTime refreshExp) {
		this.issuer = issuer;
		this.principal = principal;
		this.refreshExp = refreshExp;
	}

	public boolean refreshBefore(LocalDateTime at) {
		return refreshExp.isBefore(at);
	}

	public String asEncoded(JwtSign sign) {
		return new EncodedJwtToken(sign).issue(issuer, principal, refreshExp);
	}

	@Override
	public String toString() {
		return "JwtToken{" + "issuer='" + issuer + '\'' + ", principal='"
				+ principal + '\'' + ", refreshExp=" + refreshExp + '}';
	}
}