package MBTA;

import java.util.ArrayList;

/**
 *
 * @author troy
 */
public class StopsByLocation implements java.io.Serializable
{
    public ArrayList<StopSpecial> stop;
//    String lat;
//    String lon;
    public StopsByLocation(){};
    
    public ArrayList<StopSpecial> getStop(){
        return stop;
    }
        
//    public void setLat(String lat){
//        this.lat = lat;
//    }
//    
//    public void setLon(String lon){
//        this.lon = lon;
//    }
//
//    public String getLat(){
//        return lat;
//    }
//    
//    public String getLon(){
//        return lon;
//    }
    public Integer getStopSize(){                   //count stops
        return stop.size();
    }
}
