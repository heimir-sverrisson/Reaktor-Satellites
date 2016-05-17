package com.coolprimes.satrouting;
/**
 * Created: Heimir Sverrisson on 07/05/2016.
 *
 * A simple collection on longitude and latitude (in that order)
 */
public class Coordinates {
    private double longitude;
    private double latitude;

    public Coordinates(double longitude, double latitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("(");
        str.append(String.format("%.3f",longitude));
        str.append(", ");
        str.append(String.format("%.3f",latitude));
        str.append(")");
        return str.toString();
    }
}
