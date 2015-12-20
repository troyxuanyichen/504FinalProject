package yanchen.asg3;

import Coordinates.Coordinate;
import Coordinates.CoordinatesGroup;
import MBTA.ScheduleByStop;
import MBTA.StopsByLocation;
import MBTAResponse.MBTARequest;
import UsfTools.CoordinateList;
import UsfTools.FileIO;
import UsfTools.OutputLabelRoutePlan;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.events.MapClickListener;
import com.vaadin.tapio.googlemaps.client.events.MarkerClickListener;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolyline;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;




/**
 *
 */
@Theme("mytheme")
@Widgetset("yanchen.asg3.MyAppWidgetset")
public class MyUI extends UI {
    private GoogleMap googleMap;
    private final String apiKey = "";
    /*
        Flags
    */
    private Integer transferNumFlag;
    private Integer tripPlanFlag;
    private Integer routePlanFlag;
    private Integer dfsFlag;
    private Integer hgsFlag;
    private Integer mapFlag;
    private Integer depLocationFlag;
    private Integer desLocationFlag;
    /*
        hint information
    */
    static String hintInfo = "Please input max transfer number & search plan first, "
            + "then click 'Store Map' button,"
            + "and you are ready to search!";
    
    /*
        Data storage
    */
    private StopsByLocation stopsByLocation = new StopsByLocation();           
    private CoordinatesGroup coordinatesList = new CoordinatesGroup();          //store the coordinate group
    private Coordinate coordinate = new Coordinate();                           //store the coordinate of the last click
    private Coordinate depCoordinate = new Coordinate();
    private Coordinate desCoordinate = new Coordinate();
    private FileIO fileIO;
    private static String comFilePath = "/WEB-INF/haha.txt";
    private ArrayList<Stop> copy = new ArrayList<>();                           //store the search result
    private OutputLabelRoutePlan outputLabelRoutePlan = new OutputLabelRoutePlan();
    private GraphCreator creator = new GraphCreator();
    private CoordinateList getStopCoordinate;
    private ArrayList<LatLon> points = new ArrayList<LatLon>();
    //private TripPlan tripPlan= new TripPlan();
    Graph graph = new Graph();
    DFS dfs = new DFS();
    Heu heu = new Heu();
    Stop test = new Stop();
    Stop start = new Stop();
    Stop des = new Stop();
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        CssLayout rootLayout = new CssLayout();
        rootLayout.setSizeFull();
        setContent(rootLayout);

        TabSheet tabs = new TabSheet();
        tabs.setSizeFull();
        rootLayout.addComponent(tabs);

        HorizontalLayout mapContent = new HorizontalLayout();
        mapContent.setSizeFull();
        
        HorizontalLayout newTab1 = new HorizontalLayout();
        newTab1.setSizeFull();
        
        HorizontalLayout newTab2 = new HorizontalLayout();
        newTab2.setSizeFull();
        
        tabs.addTab(mapContent, "The google map");                              //set the map tab
//        tabs.addTab(newTab1, "The other tab");
        tabs.addTab(newTab2,hintInfo);
        VerticalLayout buttonLayoutRow1 = new VerticalLayout();                 //add the button
        buttonLayoutRow1.setWidth("300px");
        
        googleMap = new GoogleMap(null, null, null);
        googleMap.setCenter(new LatLon(42.352913, -71.064648));                 //set the original center
        googleMap.setZoom(13);                                                  //set original zoom
        googleMap.setSizeFull();
        googleMap.setMinZoom(4);
        googleMap.setMaxZoom(30);
        
        Panel console = new Panel();
        console.setWidth("300px");
        console.setHeight("300px");
        final CssLayout consoleLayout = new CssLayout();
        console.setContent(consoleLayout);
        buttonLayoutRow1.addComponent(console);
        buttonLayoutRow1.setSpacing(true);
        
        mapContent.addComponent(buttonLayoutRow1);                              //add the button layout row1
        mapContent.addComponent(googleMap);                                     //add the map
        mapContent.setExpandRatio(googleMap, 1.0f);
        
        /*
            data initiate
        */
        transferNumFlag = -1;
        tripPlanFlag = 0;
        routePlanFlag = 0;
        dfsFlag = 0;
        hgsFlag = 0;
        mapFlag = 0;
        depLocationFlag = 0;
        desLocationFlag = 0;
        /*        
        buttonLayoutRow1
        */        
        /*
            menu bar
        */
        MenuBar menuBar = new MenuBar();  
        buttonLayoutRow1.addComponent(menuBar);
        MenuBar.MenuItem planSelect = menuBar.addItem("Choose Plan", null, null);
        MenuBar.MenuItem transferNum = menuBar.addItem("Max transfer num", null, null);
        // Submenu Route Plan
        MenuBar.MenuItem routePlan = planSelect.addItem("Route Plan", null, null);
        routePlan.addItem("Depth First Search", null, new MenuBar.Command() {
            @Override
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    routePlanFlag = 1;
                    tripPlanFlag = 0;
                    dfsFlag = 1;
                    hgsFlag = 0;
                    Label dfsSelec = new Label("You choose Depth First Search of Route Plan.");
                    consoleLayout.addComponent(dfsSelec);
                }
        });
        routePlan.addItem("heuristic-guided search", null, new MenuBar.Command() {
            @Override
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    routePlanFlag = 1;
                    tripPlanFlag = 0;
                    dfsFlag = 0;
                    hgsFlag = 1;
                    Label hgsSelec = new Label("You choose heuristic-guided search of Route Plan.");
                    consoleLayout.addComponent(hgsSelec);
                }
        });        
        //Another submenu item Trip Plan        
        MenuBar.MenuItem tripPlan = planSelect.addItem("Trip Plan", null, null);
        tripPlan.setCommand(new MenuBar.Command() { 
            @Override
                public void menuSelected(MenuBar.MenuItem selectedItem) {
                    routePlanFlag = 0;
                    tripPlanFlag = 1;
                    dfsFlag = 0;
                    hgsFlag = 0;
                    Label triPlanSelec = new Label("You choose Trip Plan search.");
                    consoleLayout.addComponent(triPlanSelec);
                }
        });
        // submenu of transferNum
        MenuBar.MenuItem transferZero = transferNum.addItem("0", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                transferNumFlag = 0;
                consoleLayout.addComponent(new Label("You choose 0 max transfer number"));
            }
        });
        MenuBar.MenuItem transferOne = transferNum.addItem("1", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                transferNumFlag = 1;
                consoleLayout.addComponent(new Label("You choose 1 max transfer number"));
            }
        });
        MenuBar.MenuItem transferTwo = transferNum.addItem("2", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                transferNumFlag = 2;
                consoleLayout.addComponent(new Label("You choose 2 max transfer number"));
            }
        });
        MenuBar.MenuItem transferThree = transferNum.addItem("3", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                transferNumFlag = 3;
                consoleLayout.addComponent(new Label("You choose 3 max transfer number"));
            }
        });
        MenuBar.MenuItem transferFour = transferNum.addItem("4", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                transferNumFlag = 4;
                consoleLayout.addComponent(new Label("You choose 4 max transfer number"));
            }
        });
        MenuBar.MenuItem transferFive = transferNum.addItem(">= 5", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                transferNumFlag = 5;
                consoleLayout.addComponent(new Label("You choose more than 5 max transfer number"));
            }
        });
   
        /*
            Buttons
        */    
        //click and get
        Button addNewPointButton = new Button(             //add the point from the MBTA
                "click and show stops nearby",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                googleMap.clearMarkers();
                consoleLayout.removeAllComponents();                            //clear the console
                MBTARequest newRequest = new MBTARequest();
                Integer i;
                String tempLat = null;
                String tempLon = null;
                String labelName = null;
                coordinatesList = new CoordinatesGroup();
                coordinatesList.getCoordinatesGroup().clear();
                try {
                    newRequest.MBTAConnect1("stopsbylocation", coordinate.getLat(), coordinate.getLon());
                    googleMap.setCenter(new LatLon(Double.parseDouble(coordinate.getLat()), 
                            Double.parseDouble(coordinate.getLon())));
                    newRequest.mapStopsByLocation();                   
                    stopsByLocation = newRequest.getStopsByLocation();
                    Coordinate tc = new Coordinate(); 
                    for(i = 0; i < stopsByLocation.getStopSize(); i ++){       
                        if(stopsByLocation.getStop().get(i).getParent_station().isEmpty()){
                            tempLat = stopsByLocation.getStop().get(i).getStop_lat();
                            tempLon = stopsByLocation.getStop().get(i).getStop_lon();
                            labelName = stopsByLocation.getStop().get(i).getStop_name();
                            tc.setLat(tempLat);
                            tc.setLon(tempLon);
                            consoleLayout.addComponent(new Label("(" +  tc.getLat() 
                                    + "," + tc.getLon() + ")"));
                            coordinatesList.getCoordinatesGroup().add(tc);
                            consoleLayout.addComponent(new Label(Integer.toString(coordinatesList.getSize())));
                            consoleLayout.addComponent(new Label(coordinatesList.getCoordinatesGroup().get(0).getLat()));
                            googleMap.addMarker(labelName, new LatLon(Double.parseDouble(tempLat),
                                Double.parseDouble(tempLon)), false, null);
                        }
                    }
                    Iterator<Coordinate> coordinateIt = coordinatesList.getCoordinatesGroup().iterator();
                    while(coordinateIt.hasNext()){
                       tc =  coordinateIt.next();
                       consoleLayout.addComponent(new Label("(" +  tc.getLat() 
                                    + "," + tc.getLon() + ")"));
                    }
//                    consoleLayout.addComponent(new Label("true stop number " + count));
                } catch (IOException ex) {
                    Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    googleMap.setZoom(15);
            }
        });        
        //store map
        Button storeMap = new Button("Store Map",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {                
                if(mapFlag == 1)
                    consoleLayout.addComponent(new Label("Map already stored!"));
                else {
                    try {
                        graph = creator.Build();

                        start.SetStopID("Start");
                        start.SetStopName("Start");
                        start.SetStopLat(depCoordinate.getLat());
                        start.SetStopLon(depCoordinate.getLon());
                        start.SetRouteId("None");
                        for (int i = 0; i < graph.nodePool.size(); i++){
                            if (graph.nodePool.get(i).GetStopID().equals("70235")){
                                des = graph.nodePool.get(i);
                            }
                        }
                        graph.AddNode(start);
                        graph.AddNode(des);
                        graph = creator.AddWalkArc(graph);

                        //报错!!!!!!
                        mapFlag = 1;
                        consoleLayout.removeAllComponents();                            //clear the console
                        consoleLayout.addComponent(new Label("Map store success!"));
                    } catch (IOException ex) {
                        Logger.getLogger(MyUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        }});        
        //set departure location
        Button getDepLocation = new Button("Click to set departure",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                if(coordinate == null|| depCoordinate == null){
                    depLocationFlag = 0;
                    consoleLayout.addComponent(new Label("Please click first"));
                } else {
                depCoordinate = (Coordinate) coordinate.clone();
                googleMap.setCenter(new LatLon(Double.parseDouble(depCoordinate.getLat()), 
                        Double.parseDouble(depCoordinate.getLon())));
                googleMap.setZoom(16);
                consoleLayout.addComponent(new Label("You choose coordinate (" +
                        depCoordinate.getLat() + "," + depCoordinate.getLon() + 
                        ") as departure location"));
                depLocationFlag = 1;                
                }
            }
        }
        );
        //get destination location
        Button getDesLocation = new Button("Click to set destination",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {                
                if(coordinate == null || desCoordinate == null){
                    depLocationFlag = 0;
                    consoleLayout.addComponent(new Label("Please click first"));
                }else{
                desCoordinate = (Coordinate) coordinate.clone();;
                googleMap.setCenter(new LatLon(Double.parseDouble(desCoordinate.getLat()), 
                        Double.parseDouble(desCoordinate.getLon())));
                googleMap.setZoom(16);
                consoleLayout.addComponent(new Label("You choose coordinate (" +
                        desCoordinate.getLat() + "," + desCoordinate.getLon() + 
                        ") as destination location"));
                desLocationFlag = 1;
                }
            }
        });
        Button showDepDesLocation = new Button("Show both location",
                new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                consoleLayout.addComponent(new Label("You choose coordinate (" +
                        depCoordinate.getLat() + "," + depCoordinate.getLon() + 
                        ") as departure location"));
                
                consoleLayout.addComponent(new Label("You choose coordinate (" +
                        desCoordinate.getLat() + "," + desCoordinate.getLon() + 
                        ") as destination location"));
            }
        });
        //draw line
        Button addPolyLineButton = new Button("Draw line",
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent event) {
                        Iterator<Coordinate> coordinateIt = coordinatesList.getCoordinatesGroup().iterator();
                        Coordinate coordinateTemp = new Coordinate();

//                        Integer count = 0;
                        while(coordinateIt.hasNext()){
                            coordinateTemp = coordinateIt.next();                           
                            LatLon latLonTemp = new LatLon(Double.parseDouble(coordinateTemp.getLat()),
                                    Double.parseDouble(coordinateTemp.getLon()));
                            points.add(latLonTemp);
                            consoleLayout.addComponent(new Label(Double.toString(latLonTemp.getLat())));
                            consoleLayout.addComponent(new Label(Double.toString(latLonTemp.getLon())));
//                            System.out.println("(" +  coordinateTemp.getLat() 
//                                    + "," + coordinateTemp.getLon() + ")");
//                            count ++;
                        }
//                        consoleLayout.addComponent(new Label("true stop number " + count));
                        GoogleMapPolyline overlay = new GoogleMapPolyline(
                                points, "#d31717", 0.8, 10);
                        googleMap.addPolyline(overlay);
                        event.getButton().setEnabled(true);
                    }
                });              
        //search 
        Button searchPlan = new Button("Search!",
                new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                if((transferNumFlag == -1) ||
                   (tripPlanFlag == 0) &&
                   (routePlanFlag == 0) ||
                   (mapFlag ==0) ||
                   (depLocationFlag == 0) ||
                   (desLocationFlag == 0)){
                    Label erroeMessage = new Label("We are not ready for search\n"
                        +"please check the input:\n"
                        +"1. Choose the number of max transfer\n"
                        +"2. Choose a plan\n"
                        +"3. Store the map\n"
                        +"4. Set the departure location\n"
                        +"5. Set the destination location\n");
                    erroeMessage.setContentMode(com.vaadin.shared.ui.label.ContentMode.PREFORMATTED);   //change the content mode to PREFORMATTED
                    consoleLayout.addComponent(erroeMessage);
                } else if(routePlanFlag == 1) {
                    if(dfsFlag == 1){
                        consoleLayout.addComponent(new Label("Stack overflow!"));
//                        googleMap.clearMarkers();
//                        dfs.Search(start);
//                        copy = dfs.cp;
//                        outputLabelRoutePlan = new OutputLabelRoutePlan();
//                        Label tripDisplay = new Label(outputLabelRoutePlan.getRoutePlanLabelContent(copy));
//                        tripDisplay.setContentMode(com.vaadin.shared.ui.label.ContentMode.PREFORMATTED);
//                        consoleLayout.removeAllComponents();
//                        consoleLayout.addComponent(tripDisplay);
//                        getStopCoordinate = new CoordinateList(copy);
//                        points = getStopCoordinate.Transfer();                      //Transfer function clear the history
//                        GoogleMapPolyline overlay = new GoogleMapPolyline(          //draw line
//                                    points, "#d31717", 0.8, 10);
//                        googleMap.addPolyline(overlay);
//                        event.getButton().setEnabled(true);
//                        for(int i = 0; i < copy.size(); i ++){
//                            googleMap.addMarker(getStopCoordinate.getStopNameList().get(i), points.get(i), false, null);
//                        }
                    } else if (hgsFlag == 1){
                       googleMap.clearMarkers();
                        heu.Search(start, des, transferNumFlag);
                        copy = heu.path;
                        outputLabelRoutePlan = new OutputLabelRoutePlan();
                        Label tripDisplay = new Label(outputLabelRoutePlan.getRoutePlanLabelContent(copy));
                        tripDisplay.setContentMode(com.vaadin.shared.ui.label.ContentMode.PREFORMATTED);
                        consoleLayout.removeAllComponents();
                        consoleLayout.addComponent(tripDisplay);
                        getStopCoordinate = new CoordinateList(copy);
                        points = getStopCoordinate.Transfer();                      //Transfer function clear the history
                        GoogleMapPolyline overlay = new GoogleMapPolyline(          //draw line
                                    points, "#d31717", 0.8, 10);
                        googleMap.addPolyline(overlay);
                        event.getButton().setEnabled(true);
                        for(int i = 0; i < copy.size(); i ++){
                            googleMap.addMarker(getStopCoordinate.getStopNameList().get(i), points.get(i), false, null);
                        }
                    }
//                } else if(tripPlanFlag == 1){
                    //heuristic search
//                    tripPlan = new TripPlan (){
//                        
//                    }
                }
            }
        });
        //map reset
        Button resetMapButton = new Button("Reset the map", new Button.ClickListener(){
            @Override
            public void buttonClick(ClickEvent clickEvent){
                googleMap.clearMarkers();
                googleMap.setCenter(new LatLon(42.352913, -71.064648));  
                googleMap.setZoom(13);
                consoleLayout.removeAllComponents();
                tripPlanFlag = -1;
                routePlanFlag = 0;
                dfsFlag = 0;
                hgsFlag = 0;        
                depLocationFlag = 0;
                desLocationFlag = 0;
                depCoordinate = new Coordinate();
                desCoordinate = new Coordinate();
            }
        });
        
        
//        buttonLayoutRow1.addComponent(addNewPointButton);
        buttonLayoutRow1.addComponent(storeMap);
        buttonLayoutRow1.addComponent(getDepLocation);
        buttonLayoutRow1.addComponent(getDesLocation);
        buttonLayoutRow1.addComponent(showDepDesLocation);
//        buttonLayoutRow1.addComponent(addPolyLineButton);
        buttonLayoutRow1.addComponent(searchPlan);
        buttonLayoutRow1.addComponent(resetMapButton);        
        /*
            non-button function
        */   
        //Marker click
        googleMap.addMarkerClickListener(new MarkerClickListener() {
            @Override
            public void markerClicked(GoogleMapMarker clickedMarker) {
                ScheduleByStop scheduleByStop = new ScheduleByStop();
                double tempLat = clickedMarker.getPosition().getLat();
                double tempLon = clickedMarker.getPosition().getLon();
                String tempName = clickedMarker.getCaption();
                Integer i = 0;
                Label markerClick = new Label("Marker \""
                        + clickedMarker.getCaption() + "\" at ("
                        + clickedMarker.getPosition().getLat() + ", "
                        + clickedMarker.getPosition().getLon() + ") clicked.");
                consoleLayout.addComponent(markerClick, 0);
            }
        });
        
        //map click get coordinate
        googleMap.addMapClickListener(new MapClickListener() {
            @Override
            public void mapClicked(LatLon position) {
                double currentLat = position.getLat();
                double currentLon = position.getLon();
                String tempLat = Double.toString(currentLat);
                String tempLon = Double.toString(currentLon);
                coordinate.setLat(tempLat);
                coordinate.setLon(tempLon);
                Label consoleEntry = new Label("Map click to ("
                        + position.getLat() + ", " + position.getLon() + ")");
                consoleLayout.addComponent(consoleEntry, 0);
            }
        }); 

    }
    
//    public Label outputLabel(){
//        ArrayList<String> stopIdList = new ArrayList<>();
//        Iterator<Stop> stopIt = copy.iterator();
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
//        stopIt = copy.iterator();
//        
//        for(i = 0; i < (stopIdList.size() - 1); i ++){
//            stopTemp = copy.get(i);                                     
//            stringTemp = copy.get(i+1).GetStopID();                             //store the id of the next stop
//            result = result + "From " + stopTemp.GetStopName() + " to " + copy.get(i+1).GetStopName() + " via ";
//            arcIt = stopTemp.GetConnectedArc().iterator();
//            while(arcIt.hasNext()){
//                arcTemp =  arcIt.next();
//                if (arcTemp.GetEndStop().GetStopID().equals(stringTemp)){       //find the arc whose end stop is next stop
//                    result = result + arcTemp.GetRouteID() + "\n";
//                    break; 
//                }                    
//            }
//        }
//        System.out.println(result);
//        resultLabel = new Label(result);
//        return resultLabel;
//    }
    
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
