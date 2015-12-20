/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yanchen.asg3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author markieff
 */
public class MBTAConnection {
        JSONObject Connection(String requestURL) throws IOException{
            //connect to URL
            CloseableHttpClient myClient = HttpClients.createDefault();
        
            HttpGet myGetRequest = new HttpGet(requestURL);
            HttpEntity myEntity = null;
        
            CloseableHttpResponse myResponse = null;
            try {
                myResponse = myClient.execute(myGetRequest);
                myEntity = myResponse.getEntity();
                String res = EntityUtils.toString(myEntity);
                JSONObject myJsonResponse = new JSONObject(res);
                return myJsonResponse;

            } catch (IOException ex) {
                Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null,ex);
            } finally {
                myResponse.close();
            }
            return null;
        } 
        ArrayList GetRoutesInfo() throws IOException{
            String mbtaBaseURL = "http://realtime.mbta.com";
            String endPointName = "/developer/api/v2/routes?";
            String apiKey = "api_key=BeHGpGMsK0m3JbzU_YtRpQ";
            String format = "&format=json";
            String requestURL =
                    mbtaBaseURL + endPointName + apiKey + format;

            JSONObject inputInfo = this.Connection(requestURL);
            ArrayList<Route> routeInfo = new ArrayList<Route>();
            Route route = new Route();
            JSONArray mode = inputInfo.getJSONArray("mode");
            JSONObject insideMode = new JSONObject();
            for (int i = 0; i < mode.length(); i++){
            insideMode = mode.getJSONObject(i);
            route.SetRouteType(insideMode.getString("route_type"));
            route.SetRouteMode(insideMode.getString("mode_name"));
            JSONArray routeTrack = insideMode.getJSONArray("route");
                for (int j = 0; j < routeTrack.length();j++){
                    Route rtCp = new Route();
                    rtCp.Copy(route);
                    //To avoid duplicate in ArrayList
                    JSONObject insideRoute = routeTrack.getJSONObject(j);
                    rtCp.SetRouteID(insideRoute.getString("route_id"));
                    rtCp.SetRouteName(insideRoute.getString("route_name"));
                    routeInfo.add(rtCp);
                }
            
            }
            return routeInfo;
        }
        void GetStopsOfRoute(Route route) throws IOException{
            String mbtaBaseURL = "http://realtime.mbta.com";
            String endPointName = "/developer/api/v2/stopsbyroute?";
            String apiKey = "api_key=BeHGpGMsK0m3JbzU_YtRpQ";
            String routeBegin = "&route=";
            String routeId = route.GetRouteID();
            String format = "&format=json";
            String requestURL =
                    mbtaBaseURL + endPointName + apiKey + routeBegin + routeId + format;
            JSONObject routeInfo = this.Connection(requestURL);
            JSONArray direction = routeInfo.getJSONArray("direction");
            JSONObject insideDirection = new JSONObject();
            for (int i = 0; i < direction.length(); i++){
                insideDirection = direction.getJSONObject(i);
                if (insideDirection.getString("direction_id").equals("1")) {
                    JSONArray stopTrackIn = insideDirection.getJSONArray("stop");
                    for (int j = 0; j < stopTrackIn.length();j++){
                        Stop stop = new Stop();
                        //To avoid duplicate in ArrayList
                        JSONObject insideStop = stopTrackIn.getJSONObject(j);
                        stop.SetStopOrder(insideStop.getString("stop_order"));
                        stop.SetStopID(insideStop.getString("stop_id"));
                        stop.SetStopName(insideStop.getString("stop_name"));
                        stop.SetStopLat(insideStop.getString("stop_lat"));
                        stop.SetStopLon(insideStop.getString("stop_lon"));
                        stop.SetRouteId(routeId);
                        stop.SetRouteName(route.GetRouteName());
                        route.AddBelongedInStops(stop);
                    }
                } else if (insideDirection.getString("direction_id").equals("0")){
                    JSONArray stopTrackOut = insideDirection.getJSONArray("stop");
                    for (int j = 0; j < stopTrackOut.length();j++){
                        Stop stop = new Stop();
                        //To avoid duplicate in ArrayList
                        JSONObject insideStop = stopTrackOut.getJSONObject(j);
                        stop.SetStopOrder(insideStop.getString("stop_order"));
                        stop.SetStopID(insideStop.getString("stop_id"));
                        stop.SetStopName(insideStop.getString("stop_name"));
                        stop.SetStopLat(insideStop.getString("stop_lat"));
                        stop.SetStopLon(insideStop.getString("stop_lon"));
                        stop.SetRouteId(routeId);
                        stop.SetRouteName(route.GetRouteName());
                        route.AddBelongedOutStops(stop);
                    }
                }
            }
        }
        ArrayList StoreStops() throws IOException{
            ArrayList<Route> allRoutes = new ArrayList<Route>();
            allRoutes = this.GetRoutesInfo();
            for (int i = 0; i < allRoutes.size(); i++){
                this.GetStopsOfRoute(allRoutes.get(i));
            }
            return allRoutes;
        }
        ArrayList StoreArcs() throws IOException{
            LatLonDisCal cal = new LatLonDisCal();
            ArrayList<Route> allRoutes = new ArrayList<Route>();
            allRoutes = this.StoreStops();          
            for (int i = 0; i < allRoutes.size(); i++){
                ArrayList<Stop> allStopsOut = new ArrayList<Stop>();
                allStopsOut = allRoutes.get(i).GetBelongedOutStops();
                for (int j = 0; j < allStopsOut.size()-1; j++){
                    Arc arc = new Arc();
                    arc.SetInOut("In");
                    arc.SetStartStop(allStopsOut.get(j));
                    arc.SetEndStop(allStopsOut.get(j+1));
                    arc.SetRouteID(allRoutes.get(i).GetRouteID());
                    arc.SetRouteName(allRoutes.get(i).GetRouteName());
                    arc.SetDistance(cal.LatLonDis(allStopsOut.get(j),allStopsOut.get(j+1)));
                    allStopsOut.get(j).connectedArc.add(arc);
                    allRoutes.get(i).AddBelongedOutArcs(arc);
                }
                ArrayList<Stop> allStopsIn = new ArrayList<Stop>();
                allStopsIn = allRoutes.get(i).GetBelongedInStops();
                for (int j = 0; j < allStopsIn.size()-1; j++){
                    Arc arc = new Arc();
                    arc.SetInOut("Out");
                    arc.SetStartStop(allStopsIn.get(j));
                    arc.SetEndStop(allStopsIn.get(j+1));
                    arc.SetRouteID(allRoutes.get(i).GetRouteID());
                    arc.SetRouteName(allRoutes.get(i).GetRouteName());
                    arc.SetDistance(cal.LatLonDis(allStopsIn.get(j),allStopsIn.get(j+1)));
                    allStopsIn.get(j).connectedArc.add(arc);
                    allRoutes.get(i).AddBelongedInArcs(arc);
                }
              
            }
            return allRoutes;
        }

}