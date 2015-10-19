package de.wnill.heat.util.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Actuators")
public class Actuator {

  private String id;
  
  public Actuator() {}
  
  public Actuator(String id) {
    this.id = id;
  }

  @DynamoDBHashKey
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
  
}
