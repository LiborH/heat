package de.wnill.heat.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;

import de.wnill.heat.core.services.JobsService;

@Path("/jobs")
public class JobsFacade {

  /**
   * Returns a list of jobs that have to be executed by the actuators.
   * @return JSONArray of Jobs
   */
  @GET
  @Produces("application/json")
  public Response showJobs() {
    JSONArray array = new JSONArray(JobsService.getInstance().getJobs()); 
    return Response.status(200).entity(array.toString()).build();
  }

}
