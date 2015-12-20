/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MBTA;

import MBTA.Mode;
import java.util.ArrayList;

public class RoutesByStop {

    public RoutesByStop() {
    }
    
    private String stop_id;
    private String stop_name;
    private ArrayList<Mode> mode;

    public String getStop_id() {
        return stop_id;
    }

    public String getStop_name() {
        return stop_name;
    }

    public ArrayList<Mode> getMode() {
        return mode;
    }
}
