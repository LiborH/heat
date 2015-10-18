package de.wnill.heat.util.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "ReadingsTest")
public class Reading {

  private String sensorId;

  private String timestamp;

  private float temperature;

  private float light;

  private boolean motion;


  public Reading() {}

  @DynamoDBHashKey
  public String getSensorId() {
    return sensorId;
  }

  public void setSensorId(String sensorId) {
    this.sensorId = sensorId;
  }

  @DynamoDBRangeKey
  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public float getTemperature() {
    return temperature;
  }

  public void setTemperature(float temperature) {
    this.temperature = temperature;
  }

  public float getLight() {
    return light;
  }

  public void setLight(float light) {
    this.light = light;
  }

  public boolean isMotion() {
    return motion;
  }

  public void setMotion(boolean motion) {
    this.motion = motion;
  }

  @Override
  public String toString() {
    return "Reading [sensorId=" + sensorId + ", timestamp=" + timestamp + ", temperature="
        + temperature + ", light=" + light + ", motion=" + motion + "]";
  }



}
