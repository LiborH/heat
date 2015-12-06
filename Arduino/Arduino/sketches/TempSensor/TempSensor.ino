/**
 * The MySensors Arduino library handles the wireless radio link and protocol
 * between your home built sensors/actuators and HA controller of choice.
 * The sensors forms a self healing radio network with optional repeaters. Each
 * repeater and gateway builds a routing tables in EEPROM which keeps track of the
 * network topology allowing messages to be routed to nodes.
 *
 * Created by Henrik Ekblad <henrik.ekblad@mysensors.org>
 * Copyright (C) 2013-2015 Sensnology AB
 * Full contributor list: https://github.com/mysensors/Arduino/graphs/contributors
 *
 * Documentation: http://www.mysensors.org
 * Support Forum: http://forum.mysensors.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation.
 *
 *******************************
 *
 * DESCRIPTION
 *
 * Example sketch showing how to send in DS1820B OneWire temperature readings back to the controller
 * http://www.mysensors.org/build/temp
 */

#include <MySensor.h>  
#include <SPI.h>
#include <DallasTemperature.h>
#include <OneWire.h>

#define ONE_WIRE_BUS 3 // Pin where dallase sensor is connected 
#define READS 5
unsigned long SLEEP_BETWEEN_READS = 500; // Sleep time between reads (in milliseconds)
unsigned long SLEEP_BETWEEN_TRANSMITS = 5000; // Sleep time between reads (in milliseconds)
OneWire oneWire(ONE_WIRE_BUS); // Setup a oneWire instance to communicate with any OneWire devices (not just Maxim/Dallas temperature ICs)
DallasTemperature sensors(&oneWire); // Pass the oneWire reference to Dallas Temperature. 
MySensor gw;
float sample[READS];
float lastTemperature;
int numSensors=0;
boolean receivedConfig = false;
boolean metric = true; 
// Initialize temperature message
MyMessage msg(0,V_TEMP);

void setup()  
{ 
  Serial.begin(115200);
  
  // Startup up the OneWire library
  sensors.begin();
  // requestTemperatures() will not block current thread
  sensors.setWaitForConversion(false);

  // Startup and initialize MySensors library. Set callback for incoming messages. 
  gw.begin();

  // Send the sketch version information to the gateway and Controller
  gw.sendSketchInfo("Temperature Sensor", "2.0");

  // Fetch the number of attached temperature sensors  
  numSensors = sensors.getDeviceCount();

  // Present all sensor to controller
  gw.present(0, S_TEMP);  
}


void loop()     
{     
  // Process incoming messages (like config from server)
  gw.process(); 

  // To improve precision and smooth out frequently changing measurements, take multiple samples and send the average as current temperature 
  for (int it=0; it < READS; it++) {

    // Fetch temperatures from Dallas sensors
    sensors.requestTemperatures();

    // query conversion time and sleep until conversion completed
    int16_t conversionTime = sensors.millisToWaitForConversion(sensors.getResolution());
    // sleep() call can be replaced by wait() call if node need to process incoming messages (or if node is repeater)
    gw.sleep(conversionTime);

    // Fetch and round temperature to one decimal
    float temperature = static_cast<float>(static_cast<int>((gw.getConfig().isMetric?sensors.getTempCByIndex(0):sensors.getTempFByIndex(0)) * 10.)) / 10.;

    Serial.print("Read");
    Serial.print(it);
    Serial.print(":");
    Serial.println(temperature);

    sample[it] = temperature;
    gw.sleep(SLEEP_BETWEEN_READS);
  }


  int totalRead = 0;
  float temperature = 0;
  for (int i=0; i<READS; i++) {
    // if there was no error
    if (sample[i] != -127.00 && sample[i] != 85.00) {
      temperature += sample[i];
      totalRead++; 
    }
  }

  if (totalRead > 0) {
    float avgTemp = round(temperature / totalRead * 10.) / 10.; 
    Serial.print("Avg Temp: ");
    Serial.println(avgTemp);
    if (avgTemp != lastTemperature) {
      // Send in the new temperature
      Serial.println("Sending Update to Controller");
      gw.send(msg.setSensor(0).set(avgTemp,1));
      // Save new temperatures for next compare
      lastTemperature=avgTemp;
    }
  }
  
  gw.sleep(SLEEP_BETWEEN_TRANSMITS);
}
