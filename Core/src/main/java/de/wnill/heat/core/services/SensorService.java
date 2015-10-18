package de.wnill.heat.core.services;

import java.util.ArrayList;
import java.util.List;

import de.wnill.heat.util.dto.Reading;
import de.wnill.heat.util.dto.Sensor;
import de.wnill.heat.util.persistence.PersistenceService;

public class SensorService {

  private ArrayList<Sensor> sensors = new ArrayList<Sensor>();

  private static SensorService instance = null;

  /**
   * Returns the singleton instance.
   * 
   * @return instance
   */
  public static SensorService getInstance() {
    if (instance == null) {
      instance = new SensorService();
    }
    return instance;
  }

  public void addSensor(String id) {
    sensors.add(new Sensor(id));
  }

  public List<Sensor> getAll() {
    return sensors;
  }

  /**
   * Returns a single Sensor for given id.
   * @param id the id to check
   * @return a Sensor
   */
  public Sensor getById(String id) {
    for (Sensor sensor : sensors) {
      if (sensor.getId().equals(id)) {
        return sensor;
      }
    }
    return null;
  }

  public void addReading(Reading reading) {
    PersistenceService.getInstance().store(reading);
    
  }
}
