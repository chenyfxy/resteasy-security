package com.cyf.rest.interceptor;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cyf.rest.utils.JwtSign;
import com.cyf.rest.utils.JwtToken;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.ofInstant;
import static java.time.ZoneId.systemDefault;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

@Provider
@Priority(Priorities.AUTHENTICATION)
public final class JwtRequestFilter implements ContainerRequestFilter {
	private static final ServerResponse ACCESS_DENIED = new ServerResponse(
			"Access denied for this resource", 401, new Headers<Object>());

	public void filter(ContainerRequestContext request) {
		ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) request.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
		Method method = methodInvoker.getMethod();
		
		//Access allowed for all
		if (method.isAnnotationPresent(PermitAll.class)) {
			return;
		}
		final String authorization = request.getHeaderString(AUTHORIZATION);

		if (authorization == null) {
			request.abortWith(ACCESS_DENIED);
			return;
		}

		// Extract the token from the HTTP Authorization header
		final String token = authorization.substring("Bearer".length()).trim();

		DecodedJWT jwt = JWT.decode(token);

		final LocalDateTime expiresAt = ofInstant(jwt.getExpiresAt()
				.toInstant(), systemDefault());
		final LocalDateTime refreshExpire = ofInstant(
				jwt.getClaim("refreshExpire").asDate().toInstant(),
				systemDefault());

		if (now().isAfter(expiresAt) && now().isBefore(refreshExpire)) {
			final String newJwt = new JwtToken("Cyf", jwt.getSubject(), now()
					.plusMinutes(2)).asEncoded(JwtSign.Secret);

			request.getHeaders().putSingle(AUTHORIZATION, "Bearer " + newJwt);
		} else {
			System.out.println("auth:" + jwt.getClaim(PublicClaims.AUDIENCE).asString());
			System.out.println("refreshExpire:" + jwt.getClaim("refreshExpire").asDate());
		}
	}
}