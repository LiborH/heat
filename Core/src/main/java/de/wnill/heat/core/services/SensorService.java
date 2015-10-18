package de.wnill.heat.core.services;

import java.util.ArrayList;
import java.util.List;

import de.wnill.heat.core.dto.Sensor;

public class SensorService {

  private ArrayList<Sensor> sensors = new ArrayList<Sensor>();

  private static SensorService instance = null;

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

  public Sensor getById(String id) {
    for (Sensor sensor : sensors) {
      if (sensor.getId().equals(id)) {
        return sensor;
      }
    }
    return null;
  }
}
