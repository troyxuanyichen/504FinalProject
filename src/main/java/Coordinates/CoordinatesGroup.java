/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Coordinates;

import java.util.ArrayList;

/**
 *
 * @author troy
 */
public class CoordinatesGroup {
    private ArrayList<Coordinate> coordinatesGroup;
    
    public CoordinatesGroup() {
        coordinatesGroup = new ArrayList<>();
    }
    
    public Integer getSize(){               //count the number of coordinates
        return coordinatesGroup.size();
    }

    public ArrayList<Coordinate> getCoordinatesGroup() {
        return coordinatesGroup;
    }
}
