/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yanchen.asg3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author markieff
 */
public class DFS {
    ArrayList PathCol = new ArrayList();
    ArrayList<Stop> traPath = new ArrayList<Stop>();
    ArrayList<Stop> cp = new ArrayList<Stop>();
    ArrayList<Arc> traArc = new ArrayList<Arc>();
    ArrayList<Arc> cpA = new ArrayList<Arc>();
    int show = 0;
    double bestTime;
    
    public void Search(Stop stop){
        traPath.add(stop);
        stop.visited = true;
        DFSSearch(stop);
    }
    
    public void DFSSearch(Stop stop){
        
        if (stop.GetStopID().equals("70235")){
            show = 1;
            if (CalTime(traPath) < CalTime(cp)){
                cp = (ArrayList<Stop>) traPath.clone();
                bestTime = CalTime(traPath);
            }
            return;
        }
        for (int i = 0; i < stop.connectedArc.size(); i++){
            Stop nextStop = stop.connectedArc.get(i).GetEndStop();
            if (nextStop.visited == false)
                traPath.add(nextStop);
                nextStop.visited = true;
                DFSSearch(nextStop);
                traPath.remove(traPath.size()-1);
                nextStop.visited = false;
        }
        return;
    }

    public double CalTime(ArrayList<Stop> path){

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
}
