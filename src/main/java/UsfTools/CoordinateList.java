/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UsfTools;

import com.vaadin.tapio.googlemaps.client.LatLon;
import java.util.ArrayList;
import java.util.Iterator;
import yanchen.asg3.Stop;

/**
 *
 * @author troy
 */
public class CoordinateList {    
    
    private ArrayList<LatLon> coordinateList = new ArrayList<>();
    private ArrayList<Stop> copy = new ArrayList<>();
    private ArrayList<String> stopNameList = new ArrayList<>();
    
    public CoordinateList(ArrayList<Stop> copy) {
        this.copy = copy;
    }
    
    public ArrayList<LatLon> Transfer(){
        coordinateList = new ArrayList<>();
        Iterator<Stop> stopIt = copy.iterator();
        Stop stopTemp = new Stop();        
        String stopNameTemp = null;
        while(stopIt.hasNext()){
            stopTemp = stopIt.next();
            LatLon latLonTemp = new LatLon(Double.parseDouble(stopTemp.GetStopLat()),
                                           Double.parseDouble(stopTemp.GetStopLon()));
            coordinateList.add(latLonTemp);
            stopNameTemp = stopTemp.GetStopName();
            stopNameList.add(stopNameTemp);
        }
        return coordinateList;
    }

    public ArrayList<LatLon> getCoordinateList() {
        return coordinateList;
    }

    public ArrayList<Stop> getCopy() {
        return copy;
    }

    public ArrayList<String> getStopNameList() {
        return stopNameList;
    }
}
