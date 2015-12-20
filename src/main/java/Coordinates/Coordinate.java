/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coordinates;

/**
 *
 * @author troy
 */
public class Coordinate {
    
    private String Lat;
    private String Lon;
    
    public Coordinate(){
    }
    
    public Coordinate(String Lat, String Lon) {
        this.Lat = Lat;
        this.Lon = Lon;
    }
    
    public void setLat(String lat){
        this.Lat = lat;
    }
    
    public void setLon(String lon){
        this.Lon = lon;
    }
    
    public String getLat(){
        return Lat;
    }
    
    public String getLon(){
        return Lon;
    }
    
    public Object clone() {
        //deep copy
        Coordinate coordinate = new Coordinate(Lat, Lon);
        return coordinate;
    }
}

