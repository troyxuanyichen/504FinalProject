/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yanchen.asg3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

/**
 *
 * @author markieff
 */
public class Graph {
    //private int[][] connectMatrix;
    ArrayList<Stop> nodePool = new ArrayList<Stop>();
    Hashtable<String, Integer> stopID_nodeID = new Hashtable<String, Integer>();
    
    public int size(){
        return nodePool.size();
    }
    public boolean isEmpty(){
        return nodePool.size() == 0;
    }
    public void AddNode(Stop stop){
        nodePool.add(stop);
    }
    public void Dedup(){    
        HashSet n = new HashSet(this.nodePool);
        this.nodePool.clear();
        this.nodePool.addAll(n);
    }
    /*public void CreateMatrix(){
        connectMatrix = new int[this.nodePool.size()][this.nodePool.size()];
    }
    public void FullMatrix(){
        for (int i = 0; i < this.nodePool.size(); i++){
            this.nodePool.get(i).nodeId = i;
            stopID_nodeID.put(this.nodePool.get(i).insideStopInfo.stopId, i);
        }
        for (int j = 0; j < this.arcPool.size(); j++){
            this.arcPool.get(j).startNodeId 
                    = stopID_nodeID.get(this.arcPool.get(j).insideArcInfo.startStop.stopId);
            this.arcPool.get(j).endNodeId 
                    = stopID_nodeID.get(this.arcPool.get(j).insideArcInfo.endStop.stopId);
            connect_matrix[this.arcPool.get(j).startNodeId][this.arcPool.get(j).endNodeId] = 100;
        }
    }*/
    
}

