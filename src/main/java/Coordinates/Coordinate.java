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
    String Lat;
    String Lon;
    
    public Coordinate(){}
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
}

