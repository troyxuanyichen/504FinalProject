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
public class TripSpecial {

    public TripSpecial() {
    }
    
    private String trip_id;
    private String trip_name;
    private String sch_arr_dt;
    private String sch_dep_dt;

    public String getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(String trip_id) {
        this.trip_id = trip_id;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

    public String getSch_arr_dt() {
        return sch_arr_dt;
    }

    public void setSch_arr_dt(String sch_arr_dt) {
        this.sch_arr_dt = sch_arr_dt;
    }

    public String getSch_dep_dt() {
        return sch_dep_dt;
    }

    public void setSch_dep_dt(String sch_dep_dt) {
        this.sch_dep_dt = sch_dep_dt;
    }

}
