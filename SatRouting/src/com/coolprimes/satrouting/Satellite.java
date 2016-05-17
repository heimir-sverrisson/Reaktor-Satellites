package com.coolprimes.satrouting;
/**
 * Created: Heimir Sverrisson on 06/05/2016.
 *
 * This class has the information we need for a satellite,
 * including its reach in great-circle km.
 *
 */
public class Satellite {
    private int id;
    private Coordinates coordinates;
    private double altitude;    // km
    private double reach;       // Great circle reach in km

    public Satellite(int id, double longitude, double latitude, double altitude){
        this.id = id;
        this.coordinates = new Coordinates(longitude, latitude);
        this.altitude = altitude;
        this.reach = Compute.satelliteReach(altitude);
    }
    public int getId(){
        return id;
    }
    public double getAltitude() {
        return altitude;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
    public double getReach() {
        return reach;
    }
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(id);
        str.append("(");
        str.append(coordinates);
        str.append(", "); str.append(String.format("%.3f",altitude));
        str.append(")");
        return str.toString();
    }
}
