/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MBTA;

import java.util.ArrayList;

public class Mode {

    public Mode() {
    }

    private String route_type;
    private String mode_name;
    public ArrayList<RouteSpecial> route;
   
    public String getRoute_type() {
        return route_type;
    }

    public void setRoute_type(String route_type) {
        this.route_type = route_type;
    }

    public String getMode_name() {
        return mode_name;
    }

    public void setMode_name(String mode_name) {
        this.mode_name = mode_name;
    }

    public ArrayList<RouteSpecial> getRouteSpecial() {
        return route;
    }
}
