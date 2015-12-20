/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yanchen.asg3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.PriorityQueue;

/**
 *
 * @author markieff
 */
public class Heu {
    LatLonDisCal cal = new LatLonDisCal();
    Stop result = new Stop();
    double bestTime;
    int show = 0;
    double speedFactor = 1.863;
    ArrayList<Stop> path = new ArrayList<Stop>();
    ArrayList<Stop> trans = new ArrayList<Stop>();
    int test3 = 0;
    Comparator<Stop> FnOrder =  new Comparator<Stop>(){ 

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
    PriorityQueue<Stop> frontier =  new PriorityQueue<Stop>(FnOrder);
    ArrayList<Stop> test = new ArrayList<Stop>();
    ArrayList<Stop> test2 = new ArrayList<Stop>();
        //frontier sorted by f(n) value;
    public void Search(Stop stop, Stop des, int times){
        Stop key = new Stop();
        stop.prev = null;
        stop.SetG(0);
        stop.SetH(cal.LatLonDis(stop, des)*1.863);
        frontier.add(stop);
        while(frontier.size()!= 0){
            key = frontier.poll();//get and remove the first element from queue
            key.SetExplored(true);
            if (CalTrans(key) > times){
                test3 = CalTrans(key);
                show++;
                continue;
            }
            //test.add(key);
            if (key.GetStopID().equals(des.GetStopID())){
                result = key;
                bestTime = CalTime(result);
                return;
            }
            
            for (int i = 0; i < key.connectedArc.size(); i++){
                Arc arc = key.connectedArc.get(i);
                Stop nextStop = key.connectedArc.get(i).GetEndStop();
                if (arc.GetRouteID().equals("Walk"))
                    speedFactor = 12.42;
                else
                    speedFactor = 1.863;
                double initG = nextStop.GetG();
                double laterG = (key.gValue+(cal.LatLonDis(key, nextStop)*speedFactor));
                if (initG > laterG){
                    nextStop.SetPrev(key);
                    nextStop.SetG(laterG);
                    nextStop.SetH(cal.LatLonDis(nextStop, des)*1.863);
                    nextStop.SetExplored(false);
                    if (!frontier.contains(nextStop)){
                        frontier.add(nextStop);
                        //test2.add(nextStop);
                    }
                }
            }
        }
        return;
    }
    public double CalTime(Stop stop){
        ArrayList<Stop> mid = new ArrayList<Stop>();
        
        while(stop != null){
            mid.add(stop);
            stop = stop.prev;
        }
        for (int j = 0; j < mid.size(); j++){
            path.add(mid.get(mid.size()-1-j));
        }
        double tTime = 0;// in min
        if (path.size() == 0)
            return (float) 100000;
        if (path.size() == 1)
            return (float) 0;
        for (int i = 0; i < path.size()-1; i++){
            for (int j = 0; j < path.get(i).connectedArc.size(); j++){
                Arc arc = path.get(i).connectedArc.get(j);
                if (arc.GetEndStop().GetStopID()
                        .equals(path.get(i+1).GetStopID())){
                    if (arc.GetRouteID().equals("Walk"))
                        tTime += (arc.GetDistance()*0.621/3)*60;
                    else
                        tTime += (arc.GetDistance()*0.621/20)*60;
                }
            }
        }
        return tTime;
    }
    public int CalTrans(Stop stop){
        ArrayList<Stop> midt = new ArrayList<Stop>();
        String currentId = null;
        while(stop != null){
            midt.add(stop);
            stop = stop.prev;
        }
        trans.clear();
        for (int j = 0; j < midt.size(); j++){
            trans.add(midt.get(midt.size()-1-j));
        }
        int transT = -1;
        if (trans.size() == 0)
            return 0;
        if (trans.size() == 1)
            return 0;
        for (int i = 0; i < trans.size()-1; i++){
            for (int j = 0; j < trans.get(i).connectedArc.size(); j++){
                Arc arc = trans.get(i).connectedArc.get(j);
                if (arc.GetEndStop().GetStopID()
                        .equals(trans.get(i+1).GetStopID())){
                    if (!arc.GetRouteID().equals(currentId)){
                        transT++;
                        currentId = arc.GetRouteID();
                    }
                }
            }
        }
        return transT;
    }

    
}
