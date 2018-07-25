package com.cyf.rest;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;

@Path("/hello")
public class HelloRestService {
	@GET
    public String get() {
        return "Hello, world!xxx";
    }
	
	@GET
	@Path("/json")
	@Consumes(MediaType.APPLICATION_JSON)
    public String json() throws IOException {
		@SuppressWarnings("deprecation")
		String schema = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("schema.json"));

		return schema;
    }
}
