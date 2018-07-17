/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urirfid.views;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import urirfid.models.Location;
/**
 *
 * @author Enayet
 */
public class MapView extends Canvas{
    public Location currentLocation;
    
    public void paint(Graphics graphics) {  
  
        Toolkit toolkit=Toolkit.getDefaultToolkit();  
        Image img = toolkit.getImage("resource/pawtucketMap.jpg");
        float w = img.getWidth(null);
        float h = img.getHeight(null);
        graphics.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), 0, 0, (int)w, (int)h, this);
    	setBackground(Color.WHITE);

        if(this.currentLocation != null){
            graphics.setFont(new Font("default", Font.BOLD, 20));
            graphics.drawString(currentLocation.title, (int)(currentLocation.x*(getWidth()/w) - currentLocation.title.length()*5*(getWidth()/w)), (int)(currentLocation.y*(getHeight()/h)));
            //graphics.drawString(currentLocation.title, (int)(currentLocation.x*(getWidth()/w)), (int)(currentLocation.y*(getHeight()/h)));
            setForeground(Color.RED);
            graphics.fillOval((int)(currentLocation.x*(getWidth()/w)), (int)(currentLocation.y*(getHeight()/h)), 30, 30);
        }
    }
    
    public void updateCurrentLocation(Location l){
        this.currentLocation = l;
        this.invalidate();
        this.repaint();
    }
}
