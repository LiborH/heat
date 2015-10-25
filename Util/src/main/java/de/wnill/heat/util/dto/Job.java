package de.wnill.heat.util.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Jobs")
public class Job {

  private String actuatorId;

  private String timestamp;

  private int position;

  public Job() {}

  @DynamoDBHashKey
  public String getActuatorId() {
    return actuatorId;
  }

  public void setActuatorId(String actuatorId) {
    this.actuatorId = actuatorId;
  }

  @DynamoDBRangeKey
  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  @Override
  public String toString() {
    return "Job [actuatorId=" + actuatorId + ", timestamp=" + timestamp + ", position=" + position
        + "]";
  }


}
