package com.coolprimes.satrouting;
/**
 * Created: Heimir Sverrisson on 06/05/2016.
 *
 * This class reads the specifications for the problem from
 * a file and can return details on satellites, routes, etc.
 */

import java.io.*;
import java.util.ArrayList;

public class Specifications {
    private Satellite[] satellites;
    private String seed;
    private Route route;
    private String [] lines;

    public Specifications(String fileName) throws IOException
    {
        this.lines = readFile(fileName);
        parse();
    }

    private String[] readFile(String fileName) throws IOException
    {
        ArrayList<String> lineList = new ArrayList<String>();
        FileInputStream fstream = new FileInputStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        String str;
        while((str = br.readLine()) != null){
            lineList.add(str);
        }
        String[] lines = new String[lineList.size()];
        return lineList.toArray(lines);
    }

    private void parse(){
        ArrayList<Satellite> satellites = new ArrayList<Satellite>();
        for(String str: lines){
            if(str.startsWith("SAT")){
                String[] satParts = str.split(",");
                int id = Integer.parseInt(satParts[0].substring(3));
                double latitude = Double.parseDouble(satParts[1]);
                double longitude = Double.parseDouble(satParts[2]);
                double altitude = Double.parseDouble(satParts[3]);
                satellites.add(new Satellite(id, longitude, latitude, altitude));
            } else if(str.startsWith("ROUTE")){
                String [] routeParts = str.split(",");
                double start_latitude = Double.parseDouble(routeParts[1]);
                double start_longitude = Double.parseDouble(routeParts[2]);
                double end_latitude = Double.parseDouble(routeParts[3]);
                double end_longitude = Double.parseDouble(routeParts[4]);
                this.route = new Route(new Coordinates(start_longitude, start_latitude),
                        new Coordinates(end_longitude, end_latitude));
            } else if(str.startsWith("#SEED")){
                String [] seedParts = str.split(" ");
                this.seed = seedParts[1];
            }
        }
        // Convert list to array and save
        Satellite[] sArray = new Satellite[satellites.size()];
        this.satellites = satellites.toArray(sArray);
    }

    public Satellite[] getSatellites() {
        return satellites;
    }
    public String getSeed() {
        return seed;
    }
    public Route getRoute() {
        return route;
    }
}
