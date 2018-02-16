const int POT_PIN = A0;
int refreshRate = 0;
unsigned long nextTimeout = 0;

void setup() {
  pinMode(POT_PIN, INPUT);
  Serial.begin(9600);

}

void loop() {
  if (nextTimeout <= millis()) {
    int number = analogRead(POT_PIN);
    Serial.println('A');
    Serial.println(number);
    Serial.println(342);
    Serial.println(3);
    Serial.println(4);
    Serial.println(5);
    Serial.println(6);
    Serial.println('B');
    nextTimeout = startTimer(refreshRate);
  }
}

unsigned long startTimer(int timeout)
/*
   starts a timer
*/
{
  unsigned long timer = millis() + timeout;
  return timer;
}
