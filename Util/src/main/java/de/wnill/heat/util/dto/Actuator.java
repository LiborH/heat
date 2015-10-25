package de.wnill.heat.util.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Actuators")
public class Actuator {

  private String id;

  /** position 100 means heating at maximum, 0 means it is turned off completely. */
  private int position;

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

  public int getPosition() {
    return position;
  }

  /**
   * Updates the current position.
   * @param position the position between 0 (off) and 100 (maximum)
   */
  public void setPosition(int position) {

    if (position < 0 || position > 100) {
      throw new IllegalArgumentException("Value outside the valid range of 0 to 100");
    }

    this.position = position;
  }

  @Override
  public String toString() {
    return "Actuator [id=" + id + ", position=" + position + "]";
  }
  
  

}
