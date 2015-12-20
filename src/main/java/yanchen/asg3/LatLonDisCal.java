/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yanchen.asg3;

/**
 *
 * @author markieff
 */
public class LatLonDisCal {
    
    	public double LatLonDis(Stop st1, Stop st2) {
            double lon1 = Double.parseDouble(st1.GetStopLon());
            double lat1 = Double.parseDouble(st1.GetStopLat());
            double lon2 = Double.parseDouble(st2.GetStopLon());
            double lat2 = Double.parseDouble(st2.GetStopLat());
            double a, b, R;
            R = 6378137; 
            lat1 = lat1 * Math.PI / 180.0;
	    lat2 = lat2 * Math.PI / 180.0;
	    a = lat1 - lat2;
	    b = (lon1 - lon2) * Math.PI / 180.0;
	    double d;
	    double sa2, sb2;
            sa2 = Math.sin(a / 2.0);
	    sb2 = Math.sin(b / 2.0);
	    d = 2
			* R
			* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
					* Math.cos(lat2) * sb2 * sb2));
	    return d/1000;
	}
}
