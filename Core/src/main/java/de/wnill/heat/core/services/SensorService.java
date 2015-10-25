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
      instance.reload();
    }
    return instance;
  }

  /**
   * Overwrites currently loaded sensors with those from store.
   */
  public void reload() {
    sensors = PersistenceService.getInstance().loadSensors();
  }

  /**
   * Registers a sensor if not already existing yet.
   * 
   * @param id the sensor's id
   * @return false if errors occur
   */
  public boolean registerSensor(String id) {

    for (Sensor sensor : sensors) {
      if (sensor.getId().equals(id)) {
        return true;
      }
    }

    boolean success = PersistenceService.getInstance().store(new Sensor(id));
    if (success) {
      sensors.add(new Sensor(id));
    }
    return success;
  }

  public List<Sensor> getAll() {
    return sensors;
  }

  /**
   * Returns a single Sensor for given id.
   * 
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

  /**
   * Records a sensor reading.
   * 
   * @param reading the reading
   * @return false if errors occur
   */
  public boolean addReading(Reading reading) {
    return PersistenceService.getInstance().store(reading);
  }
}
