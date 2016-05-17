package com.coolprimes.satrouting;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created: Heimir Sverrisson on 5/8/16.
 */

public class Cesium {
    private String fileName;
    private String satellitePathFormat =
            "var satellitePath = viewer.entities.add({\n" +
            "    name : 'Path of the satellite call',\n" +
            "    polyline : {\n" +
            "        positions : Cesium.Cartesian3.fromDegreesArrayHeights([\n" +
            " %s" +
            "        ]),\n" +
            "        width : 10,\n" +
            "        followSurface : false,\n" +
            "        material : new Cesium.PolylineArrowMaterialProperty(Cesium.Color.PURPLE)\n" +
            "    }\n" +
            "});\n" +
            "var material_option = new Cesium.GridMaterialProperty({ \n" +
                    "  color: Cesium.Color.WHITE, \n" +
                    "  cellAlpha: 0.1, \n" +
                    "  lineCount: new Cesium.Cartesian2(2,2), \n" +
                    "  lineThickness: new Cesium.Cartesian2(1.0,1.0), \n" +
                    "  lineOffset: new Cesium.Cartesian2(0.0, 0.0)\n" +
                    "});\n";
    private String satelliteFormat =
            "viewer.entities.add({\n" +
                    "    name: 'Satellite %d',\n" +
                    "    position: Cesium.Cartesian3.fromDegrees(%f, %f, %f),\n" +
                    "    box: {\n" +
                    "      dimensions: new Cesium.Cartesian3(200000.0, 200000.0, 200000.0)\n" +
                    "    }\n" +
                    "});\n";
    private String satelliteReachFormat =
            "viewer.entities.add({\n" +
                    "  position: Cesium.Cartesian3.fromDegrees(%f, %f),\n" +
                    "  ellipse : {\n" +
                    "    material : material_option,\n" +
                    "    semiMajorAxis: %f,\n" +
                    "    semiMinorAxis: %f\n" +
                    "  }\n" +
                    "});\n";
    private String viewEntities =
            "viewer.zoomTo(viewer.entities);";

    public Cesium(String fileName){
        this.fileName = fileName;
    }

    private void printJS(String path, String satVariables, String view){
        try{
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            writer.print(path);
            writer.print(satVariables);
            writer.print(view);
            writer.close();
        } catch(IOException e){
            e.printStackTrace();
        }

    }

    public void generateJS(Satellite[] satellites, ArrayList<Node> shortestPath, Route route) {
        StringBuilder str = new StringBuilder();
        Coordinates start = route.getStart();
        Coordinates end = route.getEnd();
        str.append(String.format("%f, %f, %f,\n", start.getLongitude(), start.getLatitude(), 0.0d)); // Start
        for(Node n : shortestPath){
            if(n.isStart() || n.isEnd()){
                continue; // Skip start and end nodes
            }
            int id = n.getId();
            Coordinates coord = satellites[id].getCoordinates();
            double altitude = 1000.0d * satellites[id].getAltitude(); // altitude in meters, not km
            str.append(String.format("%f, %f, %f,\n", coord.getLongitude(), coord.getLatitude(), altitude));
        }
        str.append(String.format("%f, %f, %f\n", end.getLongitude(), end.getLatitude(), 0.0d)); // End
        String path = String.format(satellitePathFormat, str.toString());
        str = new StringBuilder();
        for(Satellite s : satellites){
            Coordinates coord = s.getCoordinates();
            double longitude = coord.getLongitude();
            double latitude = coord.getLatitude();
            double altitude = 1000.0d * s.getAltitude();
            double reach = 1000.0d * s.getReach();
            str.append(String.format(satelliteFormat, s.getId(), longitude, latitude, altitude));
            str.append(String.format(satelliteReachFormat, longitude, latitude, reach, reach));
        }
        String satVariables = str.toString();
        printJS(path, satVariables, viewEntities);
    }
}
