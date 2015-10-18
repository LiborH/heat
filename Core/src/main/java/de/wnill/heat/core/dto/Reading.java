package de.wnill.heat.core.dto;

import java.util.Date;

public class Reading {

  private Date time;
  
  private float temperature;
  
  private float light;
  
  private boolean motion;
  
  
  public Reading() {}

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
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
    return "Reading [temperature=" + temperature + ", light=" + light + ", motion=" + motion + "]";
  }


}
