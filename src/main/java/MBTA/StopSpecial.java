
package MBTA;

public class StopSpecial implements java.io.Serializable
{
    private String stop_id;
    private String stop_name;
    private String parent_station;
    private String parent_station_name;
    private String stop_lat;
    private String stop_lon;
    private float distance;                          //float or Float!!!! Attention!!!
    public StopSpecial(){};
    
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

    public String getParent_station() {
        return parent_station;
    }

    public void setParent_station(String parent_station) {
        this.parent_station = parent_station;
    }

    public String getParent_station_name() {
        return parent_station_name;
    }

    public void setParent_station_name(String parent_station_name) {
        this.parent_station_name = parent_station_name;
    }

    public String getStop_lat() {
        return stop_lat;
    }

    public void setStop_lat(String stop_lat) {
        this.stop_lat = stop_lat;
    }

    public String getStop_lon() {
        return stop_lon;
    }

    public void setStop_lon(String stop_lon) {
        this.stop_lon = stop_lon;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }
    
    public String printStopSpecial(){
        String output = "stop_id : " + stop_id +
                        "stop_name : " + stop_name +
                        "parent_station : " + parent_station + 
                        "parent_station_name : " + parent_station_name + 
                        "stop_lat : " + stop_lat + 
                        "stop_lon : " + distance;
        return output;
    }
}
