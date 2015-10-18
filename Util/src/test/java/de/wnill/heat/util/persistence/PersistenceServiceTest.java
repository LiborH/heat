package de.wnill.heat.util.persistence;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import de.wnill.heat.util.dto.Reading;

public class PersistenceServiceTest {

  @Test
  public void testStoreAndLoad() {

    final String id = "1";
    Date now = new Date(System.currentTimeMillis());
    String date = now.toString();
    final float temp = 20.5f;

    Reading reading = new Reading();

    reading.setTimestamp(date);
    reading.setSensorId(id);
    reading.setTemperature(temp);
    reading.setMotion(false);

    PersistenceService.getInstance().store(reading);

    Reading loaded = PersistenceService.getInstance().loadReading(id, date);

    assertEquals(temp, loaded.getTemperature(), 0.1f);
    assertEquals(false, loaded.isMotion());
    assertEquals(0, loaded.getLight(), 0f);
    assertEquals(id, loaded.getSensorId());
    assertEquals(date, loaded.getTimestamp());
  }

}
