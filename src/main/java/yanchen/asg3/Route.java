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
public class Route {
    String routeType;
    String modeName;
    String routeId;
    String routeName;
    ArrayList<Stop> inStopsIn = new ArrayList<Stop>();
    ArrayList<Stop> outStopsIn = new ArrayList<Stop>();
    ArrayList<Arc> inArcsIn = new ArrayList<Arc>();
    ArrayList<Arc> outArcsIn = new ArrayList<Arc>();
    
    public void Copy(Route route){
        this.routeType = route.routeType;
        this.modeName = route.modeName;
        this.routeId = route.routeId;
        this.routeName = route.routeName;
    }
    public String GetRouteType(){
        return this.routeType;
    }
    public String GetRouteMode(){
        return this.modeName;
    }
    public String GetRouteID(){
        return this.routeId;
    }
    public String GetRouteName(){
        return this.routeName;
    }
    public ArrayList GetBelongedInStops(){
        return inStopsIn;
    }
    public ArrayList GetBelongedInArcs(){
        return inArcsIn;
    }
    public ArrayList GetBelongedOutStops(){
        return outStopsIn;
    }
    public ArrayList GetBelongedOutArcs(){
        return outArcsIn;
    }
    public void SetRouteType(String route_type){
        this.routeType = route_type;
    }
    public void SetRouteMode(String mode_name){
        this.modeName = mode_name;
    }
    public void SetRouteID(String route_id){
        this.routeId = route_id;
    }
    public void SetRouteName(String route_name){
        this.routeName = route_name;
    }
    public void AddBelongedInStops(Stop stop){
        this.inStopsIn.add(stop);
    }
    public void AddBelongedInArcs(Arc arc){
        this.inArcsIn.add(arc);
    }
    public void AddBelongedOutStops(Stop stop){
        this.outStopsIn.add(stop);
    }
    public void AddBelongedOutArcs(Arc arc){
        this.outArcsIn.add(arc);
    }
}
