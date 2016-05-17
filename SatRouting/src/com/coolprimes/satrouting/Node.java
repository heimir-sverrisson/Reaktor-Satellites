package com.coolprimes.satrouting;
/**
 * Created: Heimir Sverrisson on 07/05/2016.
 *
 * This class encapsulates start, end and satellites
 * into a homogeneous Node for the Dijkstra algorithm.
 */
public class Node{
    private int id;
    private double distance;        // In terms of the Dijkstra algorithm
    private Coordinates coordinates;
    private double reach;           // The actual reach of a satellite in great circle km
    private boolean isStart;
    private boolean isEnd;

    Node(int id, Coordinates coordinates, double reach, boolean isStart, boolean isEnd){
        this.id = id;
        this.isStart = isStart;
        this.isEnd = isEnd;
        this.distance = Double.POSITIVE_INFINITY;
        this.coordinates = coordinates;
        this.reach = reach;
    }
    public boolean isStart() {
        return isStart;
    }
    public boolean isEnd() {
        return isEnd;
    }
    public int getId(){
        return this.id;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public double getReach() {
        return reach;
    }
    public Coordinates getCoordinates(){
        return this.coordinates;
    }
}

