/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MBTA;

import java.util.ArrayList;

/**
 *
 * @author troy
 */
public class RouteSpecial {

    public RouteSpecial() {
    }

    private String route_id;
    private String route_name;
    public ArrayList<Direction> direction;

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public ArrayList<Direction> getDirection() {
        return direction;
    }
}
