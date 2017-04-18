package br.com.alexandre.customer.api;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/customer")
public interface CustomerResource {
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Customer find();
}