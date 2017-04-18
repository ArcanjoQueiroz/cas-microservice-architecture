package br.com.alexandre.cxf.cas.client;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/game")
public interface GameResource {

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public void create(final Game game);
	
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Game findById(@PathParam("id") final String id);
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Game> findAll();
	
	@PUT
	@Consumes({ MediaType.APPLICATION_JSON })
	public void update(final Game game);
	
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") final String id);

}
