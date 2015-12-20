/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yanchen.asg3;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author markieff
 */
public class GraphCreator {
    Graph graph = new Graph();
    MBTAConnection mbta = new MBTAConnection();
    ArrayList<Route> routes = new ArrayList<Route>();
    
    public Graph Build() throws IOException{
        routes = mbta.StoreArcs();
        for (int i = 0; i < routes.size(); i++){
            for (int a = 0; a < routes.get(i).inStopsIn.size(); a++){
                graph.AddNode(routes.get(i).inStopsIn.get(a));
            }
            for (int a = 0; a < routes.get(i).outStopsIn.size(); a++){
                graph.AddNode(routes.get(i).outStopsIn.get(a));
            }
            /*for (int a = 0; a < routes.get(i).inArcsIn.size(); a++){
                graph.AddArc(routes.get(i).inArcsIn.get(a),
                        routes.get(i).inArcsIn.get(a).GetRouteID());
            }
            for (int a = 0; a < routes.get(i).outArcsIn.size(); a++){
                graph.AddArc(routes.get(i).outArcsIn.get(a),
                        routes.get(i).outArcsIn.get(a).GetRouteID());
            }*/
        }
        graph.Dedup();
        graph = this.AddWalkArc(graph);
        graph.Dedup();
        //graph.CreateMatrix();
        //graph.FullMatrix();
        return graph;        
    }
    public Graph AddWalkArc(Graph graph){
        double lon1;
        double lat1;
        double lon2;
        double lat2;
        double dis;
        LatLonDisCal cal = new LatLonDisCal();
        for (int i = 0; i < graph.size(); i++){
            for (int j = 0; j < graph.size(); j++){
                Stop st1 = graph.nodePool.get(i);
                Stop st2 = graph.nodePool.get(j);
                dis = cal.LatLonDis(st1, st2);
                if ((dis < 0.5) && (!st1.GetRouteID().equals(st2.GetRouteID()))){
                    Arc arc = new Arc();
                    arc.SetStartStop(st1);
                    arc.SetEndStop(st2);
                    arc.SetDistance(dis);
                    arc.SetRouteID("Walk");
                    arc.SetRouteName("Walk");
                    //graph.AddArc(arc, "Walk");
                    st1.AddArc(arc);
                }
            }
        }
        return graph;
    }
}

