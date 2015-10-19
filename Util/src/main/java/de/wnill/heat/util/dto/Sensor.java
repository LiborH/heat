package de.wnill.heat.util.dto;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Sensors")
public class Sensor {

  private String id;
  
  private String description;
  
  private Date lastReading;

  public Sensor(String id) {
    this.id = id;
  }
  
  public Sensor() {}


  @DynamoDBHashKey
  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public Date getLastReading() {
    return lastReading;
  }


  public void setLastReading(Date lastReading) {
    this.lastReading = lastReading;
  }


  @Override
  public String toString() {
    return "Sensor [id=" + id + ", description=" + description + ", lastReading=" + lastReading
        + "]";
  }


}
