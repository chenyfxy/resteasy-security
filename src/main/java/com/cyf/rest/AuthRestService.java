package com.cyf.rest;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.cyf.rest.utils.EncodedJwtToken;

import static java.time.LocalDateTime.now;
import static com.cyf.rest.utils.JwtSign.Secret;

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
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String jwt() {
      return "Hello jwt";
    }
    
    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("login") String login, @FormParam("password") String password) {
      final String token = new EncodedJwtToken(Secret).issue("Cyf", login, now().plusMinutes(4));
      return Response.ok(token)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
        .build();
  }
}
