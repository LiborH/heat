package de.wnill.heat.rest;

import javax.ws.rs.DELETE;
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
  public Response showAllZones() {
    JSONObject result = new JSONObject();
    result.put("zones", (ZoneService.getInstance().getZones()));
    return Response.status(200).entity(result.toString()).build();
  }

  /**
   * Returns the state of a Zone specified by an Id.
   * 
   * @param id to lookup a Zone
   * @return Zone
   */
  @Path("{id}")
  @GET
  @Produces("application/json")
  public Response showZone(@PathParam("id") String id) {
    JSONObject jsonObject = new JSONObject(ZoneService.getInstance().getById(id));
    return Response.status(200).entity(jsonObject.toString()).build();
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


  /**
   * Assigns a Sensor to a Zone.
   * 
   * @param id the Zone's ID
   * @param sensorId the sensor's ID
   * @return 200 if everything went fine
   */
  @Path("{id}/sensors/{sensorId}")
  @PUT
  @Produces("application/json")
  public Response addSensorToZone(@PathParam("id") String id,
      @PathParam("sensorId") String sensorId) {
    if (ZoneService.getInstance().assignSensor(id, sensorId)) {
      return Response.status(201).build();
    } else {
      return Response.status(500).build();
    }
  }

  /**
   * Removes a Sensor from a Zone.
   * 
   * @param id the Zone's ID
   * @param sensorId the sensor's ID
   * @return 200 if everything went fine
   */
  @Path("{id}/sensors/{sensorId}")
  @DELETE
  @Produces("application/json")
  public Response removeSensorFromZone(@PathParam("id") String id,
      @PathParam("sensorId") String sensorId) {
    if (ZoneService.getInstance().removeSensor(id, sensorId)) {
      return Response.status(200).build();
    } else {
      return Response.status(500).build();
    }
  }



  /**
   * Assigns an Actuator to a Zone.
   * 
   * @param id the Zone's ID
   * @param actuatorId the actuator's ID
   * @return 200 if everything went fine
   */
  @Path("{id}/actuators/{actuatorId}")
  @PUT
  @Produces("application/json")
  public Response addActuatorToZone(@PathParam("id") String id,
      @PathParam("actuatorId") String actuatorId) {
    if (ZoneService.getInstance().assignActuator(id, actuatorId)) {
      return Response.status(201).build();
    } else {
      return Response.status(500).build();
    }
  }

  /**
   * Removes an Actuator from a Zone.
   * 
   * @param id the Zone's ID
   * @param actuatorId the actuator's ID
   * @return 200 if everything went fine
   */
  @Path("{id}/actuators/{actuatorId}")
  @DELETE
  @Produces("application/json")
  public Response removeActuatorFromZone(@PathParam("id") String id,
      @PathParam("actuatorId") String actuatorId) {
    if (ZoneService.getInstance().removeActuator(id, actuatorId)) {
      return Response.status(200).build();
    } else {
      return Response.status(500).build();
    }
  }

}
