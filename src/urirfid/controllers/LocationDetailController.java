/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package urirfid.controllers;

import urirfid.models.Location;
import urirfid.services.LocationInfromationListaner;
import urirfid.views.LocationFrameView;

/**
 *
 * @author Enayet
 */
public class LocationDetailController implements LocationInfromationListaner{
    Location curretnLocation;
    LocationFrameView locationFrame;
    public LocationDetailController() {
        locationFrame = new LocationFrameView();
        locationFrame.setSize(1920, 1080); 
        locationFrame.setVisible(true);
    }
    
    @Override
    public void updateLocation(Location l){
        
        if (l == null) {
            return;
        }
        
        if (this.curretnLocation != null && this.curretnLocation.id.equals(l.id)) {
            return;
        }
        
        this.curretnLocation=l;
        
        locationFrame.updateCurrentLocation(this.curretnLocation);
    }
}
