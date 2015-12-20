/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MBTAResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import MBTA.StopsByLocation;
import MBTA.ScheduleByStop;
import MBTA.RoutesByStop;
/**
 *
 * @author troy
 */
public class MBTARequest {

    public MBTARequest() {
    }
     
    public StopsByLocation stopsByLocation;
    public ScheduleByStop scheduleByStop;
    public RoutesByStop routesByStop;
 
    private String stopsByLocationRes;
    private String scheduleByStopRes;
    private String routesByStopRes;
    private String lat;
    private String lon;
    
    
    public StopsByLocation getStopsByLocation() {
        return stopsByLocation;
    }
    public ScheduleByStop getScheduleByStop() {
        return scheduleByStop;
    }
    public RoutesByStop getRoutesByStop() {
        return routesByStop;
    }    
    //stopsbylocation    
    public void MBTAConnect1(
            String query,                                                
            String parameter1,
            String parameter2
    ) throws IOException{
        String mbtaBaseURL = "http://realtime.mbta.com/developer/api/v2/";
        String apiKey = "api_key=BeHGpGMsK0m3JbzU_YtRpQ";
        String format = "json";
        String requestURL = mbtaBaseURL + query + "?" + apiKey + "&lat=" + parameter1 + "&lon=" + parameter2 + "&format=" + format;               
        CloseableHttpClient myClient = HttpClients.createDefault();             //CloseableHttpClient
        HttpGet myGetRequest = new HttpGet(requestURL);
        HttpEntity myEntity = null;
        CloseableHttpResponse myResponse = null; 
        String res = null;
        try{
            myResponse = myClient.execute(myGetRequest);  //cannot be placed outside of try
            myEntity = myResponse.getEntity();
            res = EntityUtils.toString(myEntity);
        }catch(IOException ex){
            Logger.getLogger(MBTARequest.class.getName()).log(Level.SEVERE, null, ex);  //.class
        }finally{
            myResponse.close();
        }                                            
        stopsByLocationRes = res;
    }
    //schedulebystop    
    public void MBTAConnect2(
            String query,
            String parameter1
    ) throws IOException{
        String mbtaBaseURL = "http://realtime.mbta.com/developer/api/v2/";
        String apiKey = "api_key=BeHGpGMsK0m3JbzU_YtRpQ";
        String format = "json";
        String requestURL = mbtaBaseURL + query + "?" + apiKey + 
                            "&stop=" + parameter1 + "&format=" + format;
        CloseableHttpClient myClient = HttpClients.createDefault();             //CloseableHttpClient
        HttpGet myGetRequest = new HttpGet(requestURL);
        HttpEntity myEntity = null;
        CloseableHttpResponse myResponse = null; 
        String res = null;
        try{
            myResponse = myClient.execute(myGetRequest);  //cannot be placed outside of try
            myEntity = myResponse.getEntity();
            res = EntityUtils.toString(myEntity);
        }catch(IOException ex){
            Logger.getLogger(MBTARequest.class.getName()).log(Level.SEVERE, null, ex);  //.class
        }finally{
            myResponse.close();
        }                                  
        scheduleByStopRes = res;
    }
    //routesbystop    
    public void MBTAConnect3(
            String query,
            String parameter1
    ) throws IOException{
        String mbtaBaseURL = "http://realtime.mbta.com/developer/api/v2/";
        String apiKey = "api_key=BeHGpGMsK0m3JbzU_YtRpQ";
        String format = "json";
        String requestURL = mbtaBaseURL + query + "?" + apiKey + 
                            "&stop=" + parameter1 + "&format=" + format;
        System.out.println(requestURL);
        CloseableHttpClient myClient = HttpClients.createDefault();             //CloseableHttpClient
        HttpGet myGetRequest = new HttpGet(requestURL);
        HttpEntity myEntity = null;
        CloseableHttpResponse myResponse = null; 
        String res = null;
        try{
            myResponse = myClient.execute(myGetRequest);  //cannot be placed outside of try
            myEntity = myResponse.getEntity();
            res = EntityUtils.toString(myEntity);
        }catch(IOException ex){
            Logger.getLogger(MBTARequest.class.getName()).log(Level.SEVERE, null, ex);  //.class
        }finally{
            myResponse.close();
        }                                  
        System.out.println(res);
        routesByStopRes = res;
    }   
    
    
    /*
        mapper
    */
    public void mapStopsByLocation() {
        ObjectMapper mapper = new ObjectMapper();
        String response = stopsByLocationRes;
        try{
            stopsByLocation = mapper.readValue(response, StopsByLocation.class);
        }catch(IOException ex){
                  Logger.getLogger(MBTARequest.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public void mapScheduleByStop() {
        ObjectMapper mapper = new ObjectMapper();
        String response = scheduleByStopRes;
        try {
            scheduleByStop = mapper.readValue(response, ScheduleByStop.class);
        } catch (IOException ex) {
            Logger.getLogger(MBTARequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       
    public void mapRoutesByStop(){
        ObjectMapper mapper = new ObjectMapper();
        String response = routesByStopRes;
        try {
            routesByStop = mapper.readValue(response, RoutesByStop.class);
        } catch (IOException ex) {
            Logger.getLogger(MBTARequest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) throws IOException{
        MBTARequest newRequest = new MBTARequest();
        String temp = null;
//        newRequest.MBTAConnect1("stopsbylocation", "42.352913", "-71.064648");
//        newRequest.mapStopsByLocation();
//        Integer i, size = newRequest.getStopsByLocation().getStopSize();              //display
//        String temp = null;
//        for (i = 0; i < size; i ++){
//            temp = newRequest.getStopsByLocation().getStop().get(i).printStopSpecial();
//            System.out.println(temp);
//        }     
        
//        newRequest.MBTAConnect2("schedulebystop", "173");
//        newRequest.mapScheduleByStop();
//        temp = newRequest.getScheduleByStop().getMode().get(0).getRouteSpecial().get(0).getDirection().get(0).getTrip().get(0).getTrip_name();
        newRequest.MBTAConnect3("routesbystop", "70065");
        newRequest.mapRoutesByStop();
        temp = newRequest.getRoutesByStop().getMode().get(0).getRouteSpecial().get(0).getRoute_name();
        System.out.println(temp);
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getStopsByLocationRes() {
        return stopsByLocationRes;
    }

    public String getScheduleByStopRes() {
        return scheduleByStopRes;
    }

    public String getRoutesByStopRes() {
        return routesByStopRes;
    }

}
