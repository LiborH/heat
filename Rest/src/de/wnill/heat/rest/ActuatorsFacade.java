package de.wnill.heat.rest;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import de.wnill.heat.core.services.ActuatorService;

@Path("/actuators")
public class ActuatorsFacade {

  /**
   * Returns the state of an Actuator specified by an Id.
   * 
   * @param id to lookup an Actuator
   * @return Actuator instance
   */
  @Path("{id}")
  @GET
  @Produces("application/json")
  public Response showActuator(@PathParam("id") String id) {
    JSONObject jsonObject = new JSONObject(ActuatorService.getInstance().getById(id));
    return Response.status(200).entity(jsonObject.toString()).build();
  }

  /**
   * Returns an array of all actuators.
   * 
   * @return Actuator array
   */
  @GET
  @Produces("application/json")
  public Response showAllActuators() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("actuators", ActuatorService.getInstance().getAll());
    return Response.status(200).entity(jsonObject.toString()).build();
  }

  /**
   * Registers a Sensor with given Id.
   * 
   * @param id the sensor id.
   * @return 201 if everything went fine
   */
  @Path("{id}")
  @PUT
  @Produces("application/json")
  public Response registerActuator(@PathParam("id") String id) {
    boolean success = ActuatorService.getInstance().registerActuator(id);
    if (success) {
      return Response.status(201).build();
    } else {
      return Response.status(500).build();
    }
  }
}
