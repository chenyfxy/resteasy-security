package com.cyf.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.cyf.rest.interceptor.AuthorizationRequestFilter;
import com.cyf.rest.interceptor.JwtRequestFilter;

public class RestApplication extends Application {

	Set<Object> singletons = new HashSet<Object>();
	
	public RestApplication() {
//		this.singletons.add(new AuthorizationRequestFilter());
		
		this.singletons.add(new JwtRequestFilter());
		
		this.singletons.add(new HelloRestService());
		this.singletons.add(new AuthRestService());
	}
	
	@Override
	public Set<Class<?>> getClasses() {
	    HashSet<Class<?>> set = new HashSet<Class<?>>();
	    return set;
	}

	@Override
	public Set<Object> getSingletons() {
	    return this.singletons;  
	}
}
