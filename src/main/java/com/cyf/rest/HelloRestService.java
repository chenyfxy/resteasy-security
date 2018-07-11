package com.cyf.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloRestService {
	@GET
    public String get() {
        return "Hello, world!xxx";
    }
}
