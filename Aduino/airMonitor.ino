//co2 : A0 pin
//co : A1 pin
//o2 : A2 pin
//dust : 8pin

int pin = 8;
unsigned long duration;
unsigned long starttime;
unsigned long sampletime_ms = 300000; //sample 30s;
unsigned long lowpulseoccupancy = 0;
float posPerCF = 0;
float ugm3per = 0;
float ugm3 = 0;
float ratio = 0;
float concentration = 0;
//long unsigned a;
float sensorValue;
float sensorVoltage;
float Value_O2;

void setup(){
  Serial.begin(9600);
  
  //checking dust 
  pinMode(8, INPUT);
  starttime = millis(); //get the current time
  
  //checking CO2
  float co2Value = analogRead(A0); //read A0 pin
  //Serial.print("CO2 value : ");
  Serial.println(co2Value); 
  
  //checking CO
  float coValue = analogRead(A1); //read A1 pin
  //Serial.print("CO value : ");
  Serial.println(coValue);
  
  //checking O2
  sensorValue = analogRead(A2);
  sensorVoltage = (sensorValue/1024)*5.0;
  sensorVoltage = sensorVoltage/201*10000;
  Value_O2 = sensorVoltage/7.43;
  
  //Serial.print("Concentration of O2 is ");
  Serial.println(Value_O2, 1);
  //Serial.println("%");
  //checking dust 
  duration = pulseIn(pin, LOW);
  lowpulseoccupancy = lowpulseoccupancy + duration;
  
  if((millis()-starttime) < sampletime_ms) 
  {
    ratio = lowpulseoccupancy / (sampletime_ms * 10.0); 
    concentration = 1.1 * pow(ratio, 3) - 3.8 * pow(ratio, 2) + 520 * ratio + 0.62;
    posPerCF = concentration * 100;
    ugm3 = posPerCF / 7000; //change unit
    ugm3per = ugm3 * 1000;
    
    //Serial.print("dust : ");
    Serial.println(ugm3per);
    lowpulseoccupancy = 0;
    starttime = millis();
  }
  
}

void loop(){
  
}

