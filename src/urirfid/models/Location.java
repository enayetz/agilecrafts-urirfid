/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urirfid.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Enayet
 */
public class Location {
    public String id="";
    public Integer x=0;
    public Integer y=0;
    
    public String title;
    //public String description;
    public String descriptionLocation;
    public String picLocation;
    public String audioLocation;
    
    public Location(String tagid){
        try {
            this.id = tagid;
            
            //read x,y value
            Scanner scanner = new Scanner(new File(getResourceLocation("xy.csv")));
            scanner.useDelimiter(",");
            this.x = scanner.nextInt();
            this.y = scanner.nextInt();//new Integer(scanner.next());
            System.out.println("x:"+this.x+",y:"+this.y);
            scanner.close();
            
            //read title
            scanner = new Scanner(new File(getResourceLocation("title.txt")));
            this.title = scanner.nextLine();
            System.out.println("Title:"+this.title);
            
            this.audioLocation = getResourceLocation("audio.mp3");
            this.descriptionLocation = getResourceLocation("description.rtf");
            this.picLocation = getResourceLocation("pic.jpg");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Location.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String getResourceLocation(String fileName){
        return System.getProperty("user.dir")+"/resource/"+this.id+"/"+fileName;
    }
}
