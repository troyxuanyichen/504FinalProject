/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yanchen.asg3;


import MBTA.Direction;
import MBTA.Mode;
import MBTA.RouteSpecial;
import MBTA.ScheduleByStop;
import MBTA.TripSpecial;
import MBTAResponse.MBTARequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 *
 * @author troy
 */
public class TripPlan {

    private ScheduleByStop scheduleByStop = new ScheduleByStop();
    private MBTARequest schedultByStopRequest;
    private Date estimateNextStopTime;
    private ArrayList<ArrayList<Stop>> plans = new ArrayList<>();
    private ArrayList<Stop> bestPlan = new ArrayList<>();
//    private Double currentTime;                                                 //store the time token by a trip
    private Double shotestTime;
    private ArrayList<TripSpecial> tripList = new ArrayList<>();
    
    private ArrayList<String> stopIdList = new ArrayList<>();
    private ArrayList<Arc> arcList = new ArrayList<>();
    private ArrayList<String> tripIdList = new ArrayList<>();
    private ArrayList<String> arrTimeList = new ArrayList<>();//store arrival time
    public TripPlan() {
    }    
    public TripPlan(ArrayList<ArrayList<Stop>> plans) {
        this.plans = plans;
    }
    
    //inheret from heu
    private LatLonDisCal cal = new LatLonDisCal();
    private Stop result = new Stop();
    private double bestTime;
    private int show = 0;
    private double speedFactor = 1.863;
    private ArrayList<Stop> path = new ArrayList<Stop>();
    
    private Comparator<Stop> FnOrder =  new Comparator<Stop>(){ 

        @Override
        public int compare(Stop o1, Stop o2) {
            double numA = o1.GetG()+o1.GetH();
            double numB = o2.GetG()+o2.GetH();
            if (numA < numB){
                return -1;
            }else if (numA > numB){
                return 1;
            }else{
                return 0;
            }
        }
        
    };
    private PriorityQueue<Stop> frontier =  new PriorityQueue<Stop>(FnOrder);
    private ArrayList<Stop> test = new ArrayList<Stop>();
    private ArrayList<Stop> test2 = new ArrayList<Stop>();
    
//    public void findBestTripPlan(Stop stop, Stop des) throws IOException{            
//////            while (stopIt.hasNext()){
//////                stopTemp = stopIt.next();
//////                schedultByStopRequest.MBTAConnect2("schedulebystop", stopTemp.GetStopID());
//////                schedultByStopRequest.mapScheduleByStop();
////                //get arc information
////                
//////                tripList = schedultByStopRequest.getScheduleByStop()
//////                String depTimeString = schedultByStopRequest.getScheduleByStop().getMode().get(0).getRouteSpecial().get(0).getDirection().get(0).getTrip().get(0).getSch_dep_dt();
//////                Date depTimeDate = new Date(Long.parseLong(depTimeString) * 1000);    
//////                scheduleByStop = schedultByStopRequest.getScheduleByStop();
//////            }            
//////            if(currentTime < shotestTime){
//////                shotestTime = currentTime;
//////                bestPlan = planTemp;
//////            }
//////        }        
//        Stop key = new Stop();//
//        stop.SetG(0);//set departure point f()
//        stop.SetH(cal.LatLonDis(stop, des)*1.863);//set destination point g()
//        frontier.add(stop);
//        while(frontier.size()!= 0){//check if the priority queue is empty
//            key = frontier.poll();//get and remove the first element from queue
//            //test.add(key);
//            if (key.GetStopID().equals(des.GetStopID())){
//                show = 1;
//                result = key;
//                bestTime = CalTime(result);
//                return;
//            }
//            key.SetExplored(true);
//            for (int i = 0; i < key.connectedArc.size(); i++){
//                Arc arc = key.connectedArc.get(i);
//                Stop nextStop = key.connectedArc.get(i).GetEndStop();
//                if (arc.GetRouteID().equals("Walk"))
//                    speedFactor = 12.42;
//                else
//                    speedFactor = 1.863;
//                double initG = nextStop.GetG();
//                double laterG = (key.gValue+(cal.LatLonDis(key, nextStop)*speedFactor));
//                if (initG > laterG){                                            //change previous stop
//                    nextStop.SetPrev(key);
//                    nextStop.SetG(laterG);
//                    nextStop.SetH(cal.LatLonDis(nextStop, des)*1.863);
//                    nextStop.SetExplored(false);
//                    if (!frontier.contains(nextStop)){
//                        frontier.add(nextStop);
//                        //test2.add(nextStop);
//                    }
//                }
//            }
//        }
//    }
    
    public void getRealTime() throws IOException{
        stopIdList = new ArrayList<>();
        arcList = new ArrayList<>();
        tripIdList = new ArrayList<>();       
        RouteSpecial routeSpecialTemp = new RouteSpecial();
        TripSpecial tripSpecialTemp = new TripSpecial();
        ArrayList<Arc> arcListTemp = new ArrayList<>();
        Arc arcTemp = new Arc();
        Stop stopTemp = new Stop();
        String tripIdTemp = null;
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date currentDate = new Date();
        Date depTime = currentDate;
        Date MBTADepTime;
        Date MBTAArrTime = currentDate;
        Date walkDepTime;
        Date walkArrTime = currentDate;
        Double walkTime = 0.0;
        for (int i = 0; i < path.size(); i ++){//build stopIdList
            stopIdList.add(path.get(i).GetStopID());
        }
        for (int i = 0; i < path.size()-1; i ++){//build arcList
            stopTemp = path.get(i);
            arcListTemp = stopTemp.GetConnectedArc();
            String desStopId = stopIdList.get(i+1);//name of the next stop
            for (int j = 0; j < arcListTemp.size() - 1; j ++){
                arcTemp = arcListTemp.get(j);
                if(arcTemp.GetEndStop().GetStopID().equals(desStopId)){
                    arcList.add(arcTemp);
                    break;
                }
            }
        }        
        for (int i = 0; i < path.size()-1; i ++){            
//            walkArrTime = depTime;
            if (arcList.get(i).GetRouteID().equals("Walk") == false){//calculate MBTA time
                if ((i > 0)
                        && arcList.get(i).GetRouteID().equals(arcList.get(i-1).GetRouteID())//stay in the same MBTA
                        && (arcList.get(i-1).GetRouteID().equals("Walk") == false)){//in MBTA in the previous arc
                    tripSpecialTemp = searchTrip(stopIdList.get(i+1), 
                            arcList.get(i).GetRouteID(), tripIdTemp);//don't get of the MBTA until the next stop, check the arrval schedule using the last tripIdTemp
                    MBTAArrTime = new Date(Long.parseLong(tripSpecialTemp.getSch_arr_dt())*1000);
                    arrTimeList.add(MBTAArrTime.toString());//add the arrval time
                    depTime = MBTAArrTime;//set the depTime of next stop the arrval time of next stop
                } else {//transfer MBTA
                    routeSpecialTemp = searchRoute(stopIdList.get(i), 
                            arcList.get(i).GetRouteID());
                    for (int j = 0; j < routeSpecialTemp.getDirection().get(0).getTrip().size(); j ++){                    
                        String MBTADepTimeTemp = routeSpecialTemp.getDirection().get(0).getTrip().get(j).getSch_dep_dt();
                        MBTADepTime = new Date(Long.parseLong(MBTADepTimeTemp) * 1000);
                        if (depTime.before(MBTADepTime)){//able to catch the MBTA
                            tripIdTemp = routeSpecialTemp.getDirection().get(0).getTrip().get(j).getTrip_id();
                            break;
                        }
                    }                
                    tripSpecialTemp = searchTrip(stopIdList.get(i+1), arcList.get(i).GetRouteID(), tripIdTemp);                
                    MBTAArrTime = new Date(Long.parseLong(tripSpecialTemp.getSch_arr_dt())*1000);
                    arrTimeList.add(MBTAArrTime.toString());//add the arrval time
                    depTime = MBTAArrTime;//set the depTime of next stop the arrval time of next stop
                }
            } else{//walk
                walkTime = arcList.get(i).GetDistance() * 12.42;
                
                Calendar calendarTime = null;
                calendarTime.setTime(depTime);
                calendarTime.add(calendarTime.MINUTE, walkTime.intValue());
                walkArrTime = calendarTime.getTime();
                arrTimeList.add(walkArrTime.toString());
                depTime = walkArrTime;
            }
        }
    }    
        
    public TripSpecial searchTrip(String stopId, String routeId, String tripId) throws IOException{
        MBTARequest schedultByStopRequest = new MBTARequest();
        schedultByStopRequest.MBTAConnect2("schedulebystop", stopId);
        Iterator<Mode> modeIt = schedultByStopRequest.getScheduleByStop().getMode().iterator();
        Mode modeTemp = new Mode();        
        Iterator<RouteSpecial> routeSpecialIt;
        RouteSpecial routeSpecialTemp = new RouteSpecial();
        Iterator<Direction> directionIt;
        Direction directionTemp = new Direction();
        Iterator<TripSpecial> tripSpecialIt;
        TripSpecial tripSpecialTemp = new TripSpecial();
        while (modeIt.hasNext()){
            modeTemp = modeIt.next();
            routeSpecialIt = modeTemp.getRouteSpecial().iterator();
            while (routeSpecialIt.hasNext()){
                routeSpecialTemp = routeSpecialIt.next();                
                if(routeSpecialTemp.getRoute_id().equals(routeId)){
                    directionIt = routeSpecialTemp.getDirection().iterator();
                    while (directionIt.hasNext()){
                        directionTemp = directionIt.next();
                        tripSpecialIt = directionTemp.getTrip().iterator();
                        while (tripSpecialIt.hasNext()){
                            tripSpecialTemp = tripSpecialIt.next();
                            if (tripSpecialTemp.getTrip_id().equals(tripId))
                                return tripSpecialTemp;
                        }
                    }
                }    
            }
        }
        return tripSpecialTemp;
    }
       
    public RouteSpecial searchRoute(String stopId, String routeId) throws IOException{
        MBTARequest schedultByStopRequest = new MBTARequest();
        schedultByStopRequest.MBTAConnect2("schedulebystop", stopId);
        Iterator<Mode> modeIt = schedultByStopRequest.getScheduleByStop().getMode().iterator();
        Mode modeTemp = new Mode();
        Iterator<RouteSpecial> routeSpecialIt;
        RouteSpecial routeSpecialTemp = new RouteSpecial();
        while (modeIt.hasNext()){
            modeTemp = modeIt.next();
            routeSpecialIt = modeTemp.getRouteSpecial().iterator();
            while (routeSpecialIt.hasNext()){
                routeSpecialTemp = routeSpecialIt.next();                
                if(routeSpecialTemp.getRoute_id().equals(routeId)){
                    return routeSpecialTemp;
                }
            }
        }        
        return routeSpecialTemp;
    }
            
//            while (stopIt.hasNext()){
//                stopTemp = stopIt.next();
//                schedultByStopRequest.MBTAConnect2("schedulebystop", stopTemp.GetStopID());
//                schedultByStopRequest.mapScheduleByStop();
                //get arc information
                
//                tripList = schedultByStopRequest.getScheduleByStop()
//                String depTimeString = schedultByStopRequest.getScheduleByStop().getMode().get(0).getRouteSpecial().get(0).getDirection().get(0).getTrip().get(0).getSch_dep_dt();
//                Date depTimeDate = new Date(Long.parseLong(depTimeString) * 1000);    
//                scheduleByStop = schedultByStopRequest.getScheduleByStop();
//            }            
//            if(currentTime < shotestTime){
//                shotestTime = currentTime;
//                bestPlan = planTemp;
//            }
//        }  
    
    public LatLonDisCal getCal() {
        return cal;
    }

    public Stop getResult() {
        return result;
    }

    public double getBestTime() {
        return bestTime;
    }

    public int getShow() {
        return show;
    }

    public double getSpeedFactor() {
        return speedFactor;
    }

    public ArrayList<Stop> getPath() {
        return path;
    }

    public Comparator<Stop> getFnOrder() {
        return FnOrder;
    }

    public ScheduleByStop getScheduleByStop() {
        return scheduleByStop;
    }

    public MBTARequest getSchedultByStopRequest() {
        return schedultByStopRequest;
    }

    public ArrayList<ArrayList<Stop>> getPlans() {
        return plans;
    }

    public ArrayList<Stop> getBestPlan() {
        return bestPlan;
    }

//    public Double getCurrentTime() {
//        return currentTime;
//    }

    public Double getShotestTime() {
        return shotestTime;
    }

    public ArrayList<TripSpecial> getTripList() {
        return tripList;
    }

    public PriorityQueue<Stop> getFrontier() {
        return frontier;
    }

    public ArrayList<Stop> getTest() {
        return test;
    }

    public ArrayList<Stop> getTest2() {
        return test2;
    }

//    public Date getArrivalTime() {
//        return arrivalTime;
//    }

    public Date getEstimateNextStopTime() {
        return estimateNextStopTime;
    }

    public ArrayList<String> getStopIdList() {
        return stopIdList;
    }

    public ArrayList<Arc> getArcList() {
        return arcList;
    }

    public ArrayList<String> getTripIdList() {
        return tripIdList;
    }
}