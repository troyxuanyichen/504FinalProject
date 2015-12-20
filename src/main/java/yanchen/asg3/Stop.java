/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yanchen.asg3;

import java.util.ArrayList;

/**
 *
 * @author markieff
 */
public class Stop {
    String stopOrder;
    String stopId;
    String stopName;
    String stopLat;
    String stopLon;
    String routeId;
    String routeName;
    Stop prev = null;
    boolean visited = false;
    double gValue = 10000;// dis to start point
    double hValue;// dis to des point
    boolean explored = false;
    ArrayList<Arc> connectedArc = new ArrayList<Arc>();
    
    public void Copy(Stop stop){
        this.stopOrder = stop.stopOrder;
        this.stopId = stop.stopId;
        this.stopName = stop.stopName;
        this.stopLat = stop.stopLat;
        this.stopLon = stop.stopLon;
        this.routeId = stop.routeId;
        this.routeName = stop.routeName;
        this.connectedArc = stop.connectedArc;
        
    }
    public String GetStopOrder(){
        return this.stopOrder;
    }
    public String GetStopID(){
        return this.stopId;
    }
    public String GetStopName(){
        return this.stopName;
    }
    public String GetStopLat(){
        return this.stopLat;
    }
    public String GetStopLon(){
        return this.stopLon;
    }
    public String GetRouteID(){
        return this.routeId;
    }
    public String GetRouteName(){
        return this.routeName;
    }
    public Stop GetPrev(){
        return this.prev;
    }
    public double GetG(){
        return this.gValue;
    }
    public double GetH(){
        return this.hValue;
    }
    public boolean GetExplored(){
        return this.explored;
    }
    public ArrayList GetConnectedArc(){
        return this.connectedArc;
    }
    public void SetStopOrder(String stop_order){
        this.stopOrder = stop_order;
    }
    public void SetStopID(String stop_id){
        this.stopId = stop_id;
    }
    public void SetStopName(String stop_name){
        this.stopName = stop_name;
    }
    public void SetStopLat(String stop_lat){
        this.stopLat = stop_lat;
    }
    public void SetStopLon(String stop_lon){
        this.stopLon = stop_lon;
    }
    public void SetRouteId(String route_id){
        this.routeId = route_id;
    }
    public void SetRouteName(String route_name){
        this.routeName = route_name;
    }
    public void SetPrev(Stop stop){
        this.prev = stop;
    }
    public void SetG(double g){
        this.gValue = g;
    }
    public void SetH(double h){
        this.hValue = h;
    }
    public void SetExplored(boolean x){
        this.explored = x;
    }
    public void AddArc(Arc arc){
        this.connectedArc.add(arc);
    }
    public int hashCode(){
        return 60;
    }
    @Override
    public boolean equals(Object obj){
        Stop n = (Stop)obj;
        
        return this.stopId.equals
                            (n.stopId)
                &&this.stopName.equals
                            (n.stopName);
        
    }

}
