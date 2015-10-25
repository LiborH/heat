package de.wnill.heat.util.dto;

import java.util.Set;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Zones")
public class Zone {

  private String id;

  private Set<String> sensors;

  private Set<String> actuators;
  
  private String description;

  public Zone() {

  }

  @DynamoDBHashKey
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Set<String> getSensors() {
    return sensors;
  }

  public void setSensors(Set<String> sensors) {
    this.sensors = sensors;
  }

  public Set<String> getActuators() {
    return actuators;
  }

  public void setActuators(Set<String> actuators) {
    this.actuators = actuators;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return "Zone [id=" + id + ", sensors=" + sensors + ", actuators=" + actuators + ", description="
        + description + "]";
  }



}
