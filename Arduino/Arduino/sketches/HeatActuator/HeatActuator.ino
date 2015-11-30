#include <MySensor.h>
#include <SPI.h>

// ****** Config - adapt if necessary

#define SID 1
#define NID 2

const char* SKETCH = "HeatActuator";
const char* VERSION = "2015-11-30";

// Pins
const int IA_PIN = 5;
const int IB_PIN = 6;
const int READ_PIN = A0;

// Give the wireless some time to fetch messages
const unsigned long WAIT_FOR_MESSAGE_TIMEOUT = 1000;

// After this interval arduino will wake up and check if messages for this actuator have arrived
const unsigned long SLEEP_INTERVAL = 1000; 

// How many samples are read to calculate the (average) current.
const int SAMPLES = 5;

// in ms. Taking samples more frequently results in higher error rate.
const int SLEEP_BETWEEN_SAMPLES = 100;

// current threshold (in A) above which the motor is considered as stalled
const double STALL_THRESHOLD = 0.03;

// Interval between output of current
const unsigned long LOG_INTERVAL = 500;

// How often should the valve be closed and opened during calibration
const int CALIBRATION_RUNS = 3;

// ******* Constants - do not touch
const int mVperAmp = 185;
const int acsOffset = 2500; 
const byte speed = 255;
const int SLEEP_BETWEEN_STALL_CHECK = 200;

// ******* Variable initialization

// Models the state of the actuator. 100 = valve completely open, 0 = completely closed.
int position = 0;
unsigned long timeToClose = 0;
unsigned long timeToOpen = 0;
MySensor gw;
MyMessage msg(SID, V_TEMP);
int rawValue= 0;
double voltage = 0;
double amps = 0;
double avgAmp = 0;
// deviation from 0V measured when motor is idle
double normalize = .0;
unsigned long lastCheck;
String direction = "";
char command;
 
void setup() 
{ 
 Serial.begin(9600);

  gw.begin(incomingMessage, NID, false);
  gw.present(SID, S_HEATER);
  gw.sendSketchInfo(SKETCH, VERSION, false);
 
  pinMode(IA_PIN, OUTPUT);
  pinMode(IB_PIN, OUTPUT);
  lastCheck = millis();

  // First, normalize the current reading to 0 and then calibrate
  normalize = readCurrent();

  calibrate();
}
 
void loop()
{

  // Execute any jobs that comes via serial first
  if (Serial.available()) {
    direction = "";
    command = Serial.read();
    direction.concat(command);

    if (direction == "c") {
      closeValve();
    } else if (direction == "s") {
      stop();
    } else if (direction == "o") {
      openValve();
    } else if (direction == "i") {
      calibrate();
    }
  }

   double current = readCurrent();
   if (current > STALL_THRESHOLD) {
    Serial.println("STALL!");
    stop();
   }

   if (millis() - lastCheck > LOG_INTERVAL) {
     Serial.println(current, 2);
     lastCheck = millis();
   } 


  // Check for any pending jobs, execute them and go to sleep
  gw.request(SID, V_VAR1);
  gw.wait(WAIT_FOR_MESSAGE_TIMEOUT);
  gw.sleep(SLEEP_INTERVAL);  
}

void incomingMessage(const MyMessage &message) {
  if (message.type==V_VAR1) {
    int newPosition = message.getInt();
    Serial.print("Moving to position: ");
    Serial.println(newPosition);
    moveToPosition(newPosition);
  }
}

void calibrate() {
  unsigned long start = 0;
  unsigned long stop = 0;
  timeToOpen = 0;
  timeToClose = 0;
  double current = .0;
    
  // start with a completely open valve
  openValve();
  while(current <= STALL_THRESHOLD) {
    current = readCurrent();
    Serial.println(current);
    delay(SLEEP_BETWEEN_STALL_CHECK);
  }

  // cooldown
  delay(1000);
  
  for (int i = 0; i < CALIBRATION_RUNS; i++) {
    
    Serial.println("Start calibration run");
    
    // Measure time to close the valve
    start = millis();
    closeValve();
    waitUntilStall();
    stop = millis();
    Serial.println("Valve closed. Opening");
    timeToClose += (stop - start);

    // Cool down
    delay(1000);

    // Measure time to open the valve
    start = millis();
    openValve();
    waitUntilStall();
    stop = millis();
    Serial.println("Valve open");
    timeToOpen += (stop - start);

    // Cool down
    delay(1000);        
  }

  timeToClose = timeToClose / CALIBRATION_RUNS;
  timeToOpen = timeToOpen / CALIBRATION_RUNS;

  Serial.print("Average Closing in ms: ");
  Serial.println(timeToClose);

  Serial.print("Average Opening in ms: ");
  Serial.println(timeToOpen);

  // Leave the valve closed
  closeValve();
  waitUntilStall();
  position = 0;
}

void waitUntilStall() {
  double current = .0;
    while(current <= STALL_THRESHOLD) {
    current = readCurrent();
    Serial.println(current);
    delay(SLEEP_BETWEEN_STALL_CHECK);
  }
}


void moveToPosition(int newPos) {
  if (newPos > position) {
    unsigned long timeToPosition = (newPos - position) / (float)100 * timeToOpen;
    openValve();
    delay(timeToPosition);
    stop();
  } else if (newPos < position) {
    unsigned long timeToPosition = (position - newPos) / (float)100 * timeToClose;
    closeValve();
    delay(timeToPosition);
    stop();
  }
  position = newPos;
}


// Returns current in Amps
double readCurrent() {
  avgAmp = 0;

  for (int it=0; it < SAMPLES; it++) {
    rawValue = analogRead(READ_PIN);
    delay(SLEEP_BETWEEN_SAMPLES);
    voltage = (rawValue / 1023.0) * 5000; // Gets mV
    amps = ((voltage - acsOffset) / mVperAmp);
    avgAmp = avgAmp + amps;
  }

  double out = avgAmp / 10.;
  out -= normalize;  
  return out;
}


void closeValve() {
  analogWrite(IA_PIN, speed);
  analogWrite(IB_PIN, 0);
}

void openValve() {
  analogWrite(IA_PIN, 0);
  analogWrite(IB_PIN, speed);
}

void stop() {
  analogWrite(IA_PIN, 0);
  analogWrite(IB_PIN, 0);
}
