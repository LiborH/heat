package de.wnill.heat.rest;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import de.wnill.heat.core.services.ZoneService;

@Path("/zones")
public class ZoneFacade {

  /**
   * Returns a list of all available zones.
   * 
   * @return JSONArray of Jobs
   */
  @GET
  @Produces("application/json")
  public Response showZones() {
    JSONObject result = new JSONObject();
    result.put("zones", (ZoneService.getInstance().getZones()));
    return Response.status(200).entity(result.toString()).build();
  }


  /**
   * Registers a Zone with given Id.
   * 
   * @param id the zone id.
   * @return 201 if everything went fine
   */
  @Path("{id}")
  @PUT
  @Produces("application/json")
  public Response registerZone(@PathParam("id") String id) {
    if (ZoneService.getInstance().registerZone(id)) {
      return Response.status(201).build();
    } else {
      return Response.status(500).build();
    }
  }
}
