package de.wnill.heat.core.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.wnill.heat.util.dto.Actuator;
import de.wnill.heat.util.dto.Job;
import de.wnill.heat.util.dto.Zone;
import de.wnill.heat.util.persistence.PersistenceService;

public class ZoneService {

  private ArrayList<Zone> zones = new ArrayList<Zone>();

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
    return zones;
  }


  public void reload() {
    zones = PersistenceService.getInstance().loadZones();
  }

  /**
   * Registers a zone if not already existing yet.
   * 
   * @param id the zone's id
   * @return false if errors occur
   */
  public boolean registerZone(String id) {

    for (Zone zone : zones) {
      if (zone.getId().equals(id)) {
        return true;
      }
    }

    Zone zone = new Zone();
    zone.setId(id);

    boolean success = PersistenceService.getInstance().store(zone);

    if (success) {
      zones.add(zone);
    }

    return success;
  }
}
