
// *** Config here
const int IA_PIN = 5;
const int IB_PIN = 6;
const int READ_PIN = A0;
// How many samples are read to calculate the (average) current
const int SAMPLES = 5;
// in ms
const int SLEEP_BETWEEN_SAMPLES = 100;
const double STALL_THRESHOLD = 0.03;

unsigned long interval = 500;

// *** Variables
int mVperAmp = 185;
int rawValue= 0;
int acsOffset = 2500; 
double voltage = 0;
double amps = 0;
float sample[SAMPLES];
double avgAmp = 0;
// deviation from 0V measured when motor is idle
double normalize = .0;

byte speed = 255;
unsigned long lastCheck;

String direction = "";
char command;
 
void setup() 
{ 
 Serial.begin(9600);
  pinMode(IA_PIN, OUTPUT);
  pinMode(IB_PIN, OUTPUT);
  lastCheck = millis();

  // First, normalize the current reading to 0
  normalize = readCurrent();
}
 
void loop()
{ 
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

   if (millis() - lastCheck > interval) {
     Serial.println(current, 2);
     lastCheck = millis();
   }   
}

void calibrate() {


  unsigned long start = 0;
  unsigned long stop = 0;
  unsigned long avgOpening = 0;
  unsigned long avgClosing = 0;
  double current = .0;
    
  // start with a completely open valve
  openValve();
  while(current <= STALL_THRESHOLD) {
    current = readCurrent();
    Serial.println(current);
    delay(200);
  }

  // cooldown
  delay(1000);
  
  for (int i = 0; i < 3; i++) {
    
    Serial.println("Start calibration run");
    double current = .0;
    
    // Start measuring the time!
    start = millis();
    closeValve();
    while(current <= STALL_THRESHOLD) {
      current = readCurrent();
      Serial.println(current);
      delay(200);
    }
    stop = millis();
    Serial.println("Valve closed. Opening");
    avgClosing += (stop - start);

    // Cool down
    delay(1000);

    current = .0;

    start = millis();
    openValve();
    while(current <= STALL_THRESHOLD) {
      current = readCurrent();
      Serial.println(current);
      delay(200);
    }
    stop = millis();
    Serial.println("Valve open");
    avgOpening += (stop - start);

    // Cool down
    delay(1000);
        
  }

  avgClosing = avgClosing / 3;
  avgOpening = avgOpening / 3;

  Serial.print("Average Closing in ms: ");
  Serial.println(avgClosing);

    Serial.print("Average Opening in ms: ");
  Serial.println(avgOpening);

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
