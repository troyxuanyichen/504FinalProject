/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yanchen.asg3;

/**
 *
 * @author markieff
 */
public class Arc {
    Stop startStop;
    Stop endStop;
    String routeId;
    String routeName;
    String inOut;
    Double distance;//km
    
    
    public Stop GetStartStop(){
        return this.startStop;
    }
    public Stop GetEndStop(){
        return this.endStop;
    }
    public String GetRouteID(){
        return this.routeId;
    }
    public String GetRouteName(){
        return this.routeName;
    }
    public String GetInOut(){
        return this.inOut;
    }
    public Double GetDistance(){
        return this.distance;
    }
    public void SetStartStop(Stop stop){
        this.startStop = stop;
    }
    public void SetEndStop(Stop stop){
        this.endStop = stop;
    }
    public void SetRouteID(String route_id){
        this.routeId = route_id;
    }
    public void SetRouteName(String route_name){
        this.routeName = route_name;
    }
    public void SetInOut(String in_out){
        this.inOut = in_out;
    }
    public void SetDistance(double distance){
        this.distance = distance;
    }
}