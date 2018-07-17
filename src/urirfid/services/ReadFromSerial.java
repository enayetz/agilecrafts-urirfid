/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urirfid.services;

import com.fazecast.jSerialComm.SerialPort;
import java.io.InputStream;
import java.util.Scanner;
import java.util.StringTokenizer;
import urirfid.models.Location;

/**
 *
 * @author Enayet
 */
public class ReadFromSerial {

    LocationInfromationListaner locationInfromationListaner;

    public ReadFromSerial(LocationInfromationListaner lil) {
        this.locationInfromationListaner = lil;
    }

    public void startService() {

        SerialPort comPort = SerialPort.getCommPorts()[0];
        comPort.openPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
        comPort.setBaudRate(115200);
        InputStream in = comPort.getInputStream();
        String s = "";
        try {
            while (comPort.openPort()) {
                char c = ((char) in.read());
                System.out.print(c);
                if (c == '{') {
                    s = "";
                } else if (c == '}') {
                    StringTokenizer st = new StringTokenizer(s, ",");
                    
                    String id = st.nextToken();
                    System.out.println(id);
                    
                    if (st.hasMoreTokens()) {
                        String rssi = st.nextToken();
                        System.out.println(rssi);
                        
                    }
                            
                    Location l = new Location(id);
                    this.locationInfromationListaner.updateLocation(l);
                    System.out.println(s);
                } else {
                    s = s + c;
                }

            }
//		      System.out.print((char)in.read());
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        comPort.closePort();
    }

}
