package de.wnill.heat.core.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.wnill.heat.util.dto.Job;
import de.wnill.heat.util.dto.Sensor;

public class JobsService {

  private ArrayList<Sensor> sensors = new ArrayList<Sensor>();

  private static JobsService instance = null;

  /**
   * Returns the singleton instance.
   * 
   * @return instance
   */
  public static JobsService getInstance() {
    if (instance == null) {
      instance = new JobsService();
    }
    return instance;
  }

 
  /**
   * Provides a list of jobs that need to be executed by actuators.
   * @return list of Jobs
   */
  public List<Job> getJobs() {
    LinkedList<Job> result = new LinkedList<Job>();
    
    
    
    return result;
  }
  
}
