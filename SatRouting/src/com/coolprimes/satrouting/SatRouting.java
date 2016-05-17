package com.coolprimes.satrouting;

import sun.util.logging.PlatformLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created: Heimir Sverrisson on 5/8/16.
 *
 * Main program to run the shortest path satellite routing
 *
 */

public class SatRouting {
    private static final Logger logger = Logger.getGlobal();
    private Specifications specifications;
    private Cesium cesium;

    public SatRouting(String fileName, String outfileName) throws IOException{
        this.specifications = new Specifications(fileName);
        this.cesium = new Cesium(outfileName);
    }

    public void run(){
        Route route = specifications.getRoute();
        StringBuilder str = new StringBuilder();
        str.append(specifications.getRoute());
        str.append("\n");
        str.append("Great circle distance: ");
        str.append(Compute.greatCircleDistance(route.getStart(), route.getEnd()));
        str.append("\n");
        Satellite[] satellites = specifications.getSatellites();
        Satellite[] visibleAtStart = Compute.visible(satellites, route.getStart());
        Satellite[] visibleAtEnd = Compute.visible(satellites, route.getEnd());
        str.append("Visible at start:\n");
        for(Satellite s : visibleAtStart){
            str.append(s); str.append("\n");
        }
        str.append("Visible at end:\n");
        for(Satellite s : visibleAtEnd){
            str.append(s); str.append("\n");
        }
        Dijkstra dijkstra = new Dijkstra(satellites, route.getStart(), route.getEnd());
        ArrayList<Node> shortestPath = dijkstra.shortestPath();
        str.append("The shortest path:\n");
        for(Node n : shortestPath){
            str.append(n.getId()); str.append(": ");
            str.append(n.getDistance()); str.append("\n");
        }
        str.append("Answer to web: ");
        for(Node n : shortestPath){
            if(n.isStart() || n.isEnd()){
                continue;
            }
            int id = n.getId();
            str.append(String.format("SAT%d,", id));
        }
        str.append("\nSeed: " + specifications.getSeed() + "\n");
        logger.log(Level.INFO, str.toString());
        cesium.generateJS(satellites, shortestPath, route);
    }

    public static void main(String[] args) throws IOException{
        SatRouting satRouting = new SatRouting(args[0], args[1]);
        satRouting.run();
    }
}
