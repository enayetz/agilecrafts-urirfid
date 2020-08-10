/*
  Identify specific tag
  Date: April 25, 2018
  Last Edited: July 10, 2018
  Muhammad Enayetur Rahman

*/

#include <SoftwareSerial.h> //Used for transmitting to the device

SoftwareSerial softSerial(2, 3); //RX, TX

//Library for controlling the M6E Nano module
#include "SparkFun_UHF_RFID_Reader.h"
//Create instance
RFID nano;

void setup()
{
  Serial.begin(115200);
  //Configure nano to run at 38400bps
  if (setupNano(38400) == false)
  {
    Serial.println("Module failed to respond. Please check wiring.");
    while (1); //Freeze!
  }
  nano.setRegion(REGION_NORTHAMERICA); //Set to North America
  nano.setReadPower(1500); //5.00 dBm. Higher values may cause USB port to brown out
  //Max Read TX Power is 27.00 dBm and may cause temperature-limit throttling
}




void loop()
{
  byte response;
  byte getUID[8]; //UIDs can range from 0 to 20 bytes. Start with 8.
  byte uidLength = sizeof(getUID);
//  byte location1_UID[8] = {0x01, 0x2C, 0xFD, 0x00, 0x09, 0xEB, 0x11, 0x20};
//  byte location2_UID[8] = {0x01, 0x2F, 0xFD, 0x00, 0x09, 0xEB, 0x0C, 0xE5};
//  byte location3_UID[8] = {0x01, 0x33, 0xFD, 0x00, 0x09, 0xEB, 0x0C, 0xE2};

    byte loc1_UID[8] = {0x01, 0x39, 0xFD, 0x00, 0x09, 0xEB, 0x0D, 0xC2};
    byte loc2_UID[8] = {0x01, 0x3C, 0xFD, 0x00, 0x09, 0xEB, 0x0D, 0xBC};

    
  //Read unique ID of the tag
  response = nano.readUID(getUID, uidLength);
  int rssi = nano.getTagRSSI(); //Get the RSSI for this tag read
  
  if (response == RESPONSE_SUCCESS)
  {    
        
//        char a[10];
//        a[0] = '{';
//        a[9] = '}';
//        memcpy(a+1, getUID, 8);
//        Serial.println(a);


            Serial.print("{");
            for(byte x = 0 ; x < uidLength ; x++)
            {
              if(getUID[x] < 0x10) Serial.print("0");
              Serial.print(getUID[x], HEX);
            }
              Serial.print(",");
              Serial.print(rssi);
            Serial.println("}");   
   }
  else
    Serial.println(F("{no_tag}"));

    delay(1000);
}


//compare function:

boolean tagCompare(byte location_UID[], byte getUID[], byte uidLength)
{
    //compare with the defined value
    for(byte j = 0; j < uidLength; j++)
    {
        if(location_UID[j] != getUID[j])
        {
            return false;
        }
     }

    return true;  
}



//Gracefully handles a reader that is already configured and already reading continuously
//Because Stream does not have a .begin() we have to do this outside the library
boolean setupNano(long baudRate)
{
  nano.begin(softSerial); //Tell the library to communicate over software serial port

  //Test to see if we are already connected to a module
  //This would be the case if the Arduino has been reprogrammed and the module has stayed powered
  softSerial.begin(baudRate); //For this test, assume module is already at our desired baud rate
  while(!softSerial); //Wait for port to open

  //About 200ms from power on the module will send its firmware version at 115200. We need to ignore this.
  while(softSerial.available()) softSerial.read();
  
  nano.getVersion();

  if (nano.msg[0] == ERROR_WRONG_OPCODE_RESPONSE)
  {
    //This happens if the baud rate is correct but the module is doing a ccontinuous read
    nano.stopReading();

    Serial.println(F("Module continuously reading. Asking it to stop..."));

    delay(1500);
  }
  else
  {
    //The module did not respond so assume it's just been powered on and communicating at 115200bps
    softSerial.begin(115200); //Start software serial at 115200

    nano.setBaud(baudRate); //Tell the module to go to the chosen baud rate. Ignore the response msg

    softSerial.begin(baudRate); //Start the software serial port, this time at user's chosen baud rate
  }

  //Test the connection
  nano.getVersion();
  if (nano.msg[0] != ALL_GOOD) return (false); //Something is not right

  //The M6E has these settings no matter what
  nano.setTagProtocol(); //Set protocol to GEN2

  nano.setAntennaPort(); //Set TX/RX antenna ports to 1

  return (true); //We are ready to rock
}
