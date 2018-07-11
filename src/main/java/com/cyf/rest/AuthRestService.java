package com.cyf.rest;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthRestService {
	@GET
	@Path("/access")
	@PermitAll
    public String getOk() {
        return "OK";
    }
	
    @PUT
    @Path("/users")
    @RolesAllowed("admin")
    public Response updateUserById() {
        return Response.status(200).build();
    }
}
