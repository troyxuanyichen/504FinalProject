/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UsfTools;

import com.vaadin.ui.Label;
import java.util.ArrayList;
import java.util.Iterator;
import yanchen.asg3.Arc;
import yanchen.asg3.Stop;

/**
 *
 * @author troy
 */
public class OutputLabelRoutePlan {
    
    private ArrayList<ArrayList<Stop>> heuristicResult = new ArrayList<>();
    private ArrayList<Stop> dfsResult = new ArrayList<>();
    
    public OutputLabelRoutePlan() {
    }
    
    public String getRoutePlanLabelContent(ArrayList<Stop> dfsResult){
        this.dfsResult = dfsResult;
        ArrayList<String> stopIdList = new ArrayList<>();
        Iterator<Stop> stopIt = dfsResult.iterator();
        Iterator<Arc> arcIt ;
        Arc arcTemp = null;
        Stop stopTemp = null;
        String stringTemp = null;
        String result = "";                                                     //if set to null, 'null' will be displayed
        Integer i = 0;
        ArrayList<String> displayStrings = new ArrayList<>();
        Label resultLabel = null;
        
        while(stopIt.hasNext()){
            stopTemp = stopIt.next();
            stopIdList.add(stopTemp.GetStopID());            
        }
        stopIt = dfsResult.iterator();
        
        for(i = 0; i < (stopIdList.size() - 1); i ++){
            stopTemp = dfsResult.get(i);                                     
            stringTemp = dfsResult.get(i+1).GetStopID();                             //store the id of the next stop
            result = result + stopTemp.GetStopName() + " > " + dfsResult.get(i+1).GetStopName() + " by ";
            arcIt = stopTemp.GetConnectedArc().iterator();
            while(arcIt.hasNext()){
                arcTemp =  arcIt.next();
                if (arcTemp.GetEndStop().GetStopID().equals(stringTemp)){       //find the arc whose end stop is next stop
                    result = result + arcTemp.GetRouteID() + "\n";
                    break; 
                }                    
            }
        }
    return result;
    }
    
//    public String getTripPlanLabelConten(ArrayList<ArrayList<Stop>> dfsResult){
//        this.heuristicResult = dfsResult;
//        ArrayList<String> stopIdList = new ArrayList<>();
//        Iterator<Stop> stopIt = dfsResult.iterator();
//        Iterator<Arc> arcIt ;
//        Arc arcTemp = null;
//        Stop stopTemp = null;
//        String stringTemp = null;
//        String result = null;
//        Integer i = 0;
//        ArrayList<String> displayStrings = new ArrayList<>();
//        Label resultLabel = null;
//        
//        while(stopIt.hasNext()){
//            stopTemp = stopIt.next();
//            stopIdList.add(stopTemp.GetStopID());            
//        }
//        stopIt = dfsResult.iterator();
//        
//        for(i = 0; i < (stopIdList.size() - 1); i ++){
//            stopTemp = dfsResult.get(i);                                     
//            stringTemp = dfsResult.get(i+1).GetStopID();                             //store the id of the next stop
//            result = result + "From " + stopTemp.GetStopName() + " to " + dfsResult.get(i+1).GetStopName() + " via ";
//            arcIt = stopTemp.GetConnectedArc().iterator();
//            while(arcIt.hasNext()){
//                arcTemp =  arcIt.next();
//                if (arcTemp.GetEndStop().GetStopID().equals(stringTemp)){       //find the arc whose end stop is next stop
//                    result = result + arcTemp.GetRouteID() + "\n";
//                    break; 
//                }                    
//            }
//        }
//    return result;
//    }    

    public ArrayList<ArrayList<Stop>> getHeuristicResult() {
        return heuristicResult;
    }

    public void setHeuristicResult(ArrayList<ArrayList<Stop>> heuristicResult) {
        this.heuristicResult = heuristicResult;
    }

    public ArrayList<Stop> getDfsResult() {
        return dfsResult;
    }

    public void setDfsResult(ArrayList<Stop> dfsResult) {
        this.dfsResult = dfsResult;
    }
    
}
