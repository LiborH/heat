package de.wnill.heat.rest;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import de.wnill.heat.core.services.SensorService;
import de.wnill.heat.util.dto.Reading;

@Path("/sensors")
public class SensorsFacade {

  /**
   * Returns an array of all registered sensor nodes, including their states.
   * 
   * @return Sensor array
   */
  @GET
  @Produces("application/json")
  public Response showAllSensors() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("sensors", SensorService.getInstance().getAll());
    return Response.status(200).entity(jsonObject.toString()).build();
  }


  /**
   * Returns the state of a Sensor specified by an Id.
   * 
   * @param id to lookup a Sensor
   * @return Sensor
   */
  @Path("{id}")
  @GET
  @Produces("application/json")
  public Response showSensor(@PathParam("id") String id) {
    JSONObject jsonObject = new JSONObject(SensorService.getInstance().getById(id));
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
  public Response registerSensor(@PathParam("id") String id) {
    if (SensorService.getInstance().registerSensor(id)) {
      return Response.status(201).build();
    } else {
      return Response.status(500).build();
    }
  }

  /**
   * Receives a reading of the sensor node. Note: Since sensor nodes do not track time on their own,
   * this method adds a timestamp to the reading.
   * 
   * @param id the sensor's id
   * @param reading the measured values
   * @return 201 if everything is okay
   */
  @Path("{id}/reading")
  @POST
  @Consumes("application/json")
  @Produces("application/json")
  public Response addReading(@PathParam("id") String id, Reading reading) {
    reading.setTimestamp(new Date().toString());
    reading.setSensorId(id);
    if (SensorService.getInstance().addReading(reading)) {
      return Response.status(201).build();
    } else {
      return Response.status(500).build();
    }
  }

}
