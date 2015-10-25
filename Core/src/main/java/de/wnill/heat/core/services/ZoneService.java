package de.wnill.heat.core.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.wnill.heat.util.dto.Zone;
import de.wnill.heat.util.persistence.PersistenceService;

public class ZoneService {

  /** Cache of all Zones. Maps ZoneID --> Zone */
  private HashMap<String, Zone> zones = new HashMap<String, Zone>();

  private static ZoneService instance = null;

  /**
   * Returns the singleton instance.
   * 
   * @return instance
   */
  public static ZoneService getInstance() {
    if (instance == null) {
      instance = new ZoneService();
      instance.reload();
    }
    return instance;
  }


  /**
   * Provides a list of available zones.
   * 
   * @return list of Zones
   */
  public List<Zone> getZones() {
    return new LinkedList<Zone>(zones.values());
  }

  /**
   * Returns a single Zone for given id.
   * 
   * @param id the id to check
   * @return a Zone
   */
  public Zone getById(String id) {
    return zones.get(id);
  }


  /**
   * Overwrites currently loaded zones with those from store.
   */
  public void reload() {
    ArrayList<Zone> zoneList = PersistenceService.getInstance().loadZones();

    for (Zone zone : zoneList) {
      zones.put(zone.getId(), zone);
    }
  }

  /**
   * Registers a zone if not already existing yet.
   * 
   * @param id the zone's id
   * @return false if errors occur
   */
  public boolean registerZone(String id) {

    if (zones.containsKey(id)) {
      return true;
    }

    Zone zone = new Zone();
    zone.setId(id);

    boolean success = PersistenceService.getInstance().store(zone);
    if (success) {
      zones.put(id, zone);
    }

    return success;
  }


  /**
   * Persists the assignment of a Sensor to a Zone.
   * 
   * @param id the Zone ID
   * @param sensorId the Sensor ID
   * @return false in case of errors
   */
  public boolean assignSensor(String id, String sensorId) {

    if (!zones.containsKey(id) || sensorId == null) {
      return false;
    }

    Zone zone = zones.get(id);
    Set<String> sensorIds = zone.getSensors();
    if (sensorIds == null) {
      sensorIds = new HashSet<String>();
      zone.setSensors(sensorIds);
    }
    sensorIds.add(sensorId);
    PersistenceService.getInstance().store(zone);

    return true;
  }

  /**
   * Removes a Sensor assignment to given Zone.
   * 
   * @param id the Zone ID
   * @param sensorId the Sensor ID
   * @return false if errors occurred
   */
  public boolean removeSensor(String id, String sensorId) {
    if (!zones.containsKey(id)
        || (zones.containsKey(id) && !zones.get(id).getSensors().contains(sensorId))
        || sensorId == null) {
      return false;
    }

    zones.get(id).getSensors().remove(sensorId);
    return true;
  }

  /**
   * Persists the assignment of an Actuator to a Zone.
   * 
   * @param id the Sensor ID
   * @param actuatorId the Actuator ID
   * @return false in case of errors
   */
  public boolean assignActuator(String id, String actuatorId) {

    if (!zones.containsKey(id) || actuatorId == null) {
      return false;
    }

    Zone zone = zones.get(id);
    Set<String> actuatorIds = zone.getActuators();
    if (actuatorIds == null) {
      actuatorIds = new HashSet<String>();
      zone.setActuators(actuatorIds);
    }
    actuatorIds.add(actuatorId);
    PersistenceService.getInstance().store(zone);

    return true;
  }

  /**
   * Removes an Actuator assignment to given Zone.
   * 
   * @param id the Zone ID
   * @param actuatorId the Actuator ID
   * @return false if errors occurred
   */
  public boolean removeActuator(String id, String actuatorId) {
    if (!zones.containsKey(id)
        || (zones.containsKey(id) && !zones.get(id).getActuators().contains(actuatorId))
        || actuatorId == null) {
      return false;
    }

    zones.get(id).getActuators().remove(actuatorId);
    return true;
  }

}
