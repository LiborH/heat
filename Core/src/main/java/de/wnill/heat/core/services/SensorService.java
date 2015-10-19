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

  /**
   * Registers a sensor if not already existing yet.
   * @param id the sensor's id
   */
  public void registerSensor(String id) {
    
    for (Sensor sensor : sensors) {
      if (sensor.getId().equals(id)) {
        return;
      }
    }
    
    Sensor existing = PersistenceService.getInstance().loadEntity(id, Sensor.class);
    if (existing == null) {
      PersistenceService.getInstance().store(new Sensor(id));
    }
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
    
    // If not cached, check if it exists in DB
    Sensor sensor = PersistenceService.getInstance().loadEntity(id, Sensor.class);
    return sensor;
  }

  public void addReading(Reading reading) {
    PersistenceService.getInstance().store(reading);
  }
}
