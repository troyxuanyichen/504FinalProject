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


public class ScheduleByStop {

    public ScheduleByStop() {
        
    }
    
    private String stop_id;
    private String stop_name;
    public ArrayList<Mode> mode;

    public String getStop_id() {
        return stop_id;
    }

    public void setStop_id(String stop_id) {
        this.stop_id = stop_id;
    }

    public String getStop_name() {
        return stop_name;
    }

    public void setStop_name(String stop_name) {
        this.stop_name = stop_name;
    }

    public ArrayList<Mode> getMode() {
        return mode;
    }
}
