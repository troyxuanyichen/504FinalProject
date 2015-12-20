/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MBTA;

import MBTA.Stop;
import java.util.ArrayList;

public class Direction {

    public Direction() {
    }
    
    private String direction_id;
    private String direction_name;
    public ArrayList<TripSpecial> trip;

    public String getDirection_id() {
        return direction_id;
    }

    public void setDirection_id(String direction_id) {
        this.direction_id = direction_id;
    }

    public String getDirection_name() {
        return direction_name;
    }

    public void setDirection_name(String direction_name) {
        this.direction_name = direction_name;
    }

    public ArrayList<TripSpecial> getTrip() {
        return trip;
    }
}
