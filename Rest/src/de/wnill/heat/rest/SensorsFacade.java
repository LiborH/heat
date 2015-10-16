package de.wnill.heat.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import de.wnill.heat.core.services.SensorService;

@Path("/sensors")
public class SensorsFacade {

	@GET
	@Produces("application/json")
	public Response showSensors() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sensorIds", SensorService.getInstance().getAll());
		return Response.status(200).entity(jsonObject.toString()).build();
	}
	
	@Path("{id}")
	@POST
	@Produces("application/json")
	public Response addSensor(@PathParam("id") String id) {
		SensorService.getInstance().addSensor(id);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		return Response.status(200).entity(jsonObject.toString()).build();

	}

}
