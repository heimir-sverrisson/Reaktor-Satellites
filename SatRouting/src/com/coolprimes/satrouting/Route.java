package com.coolprimes.satrouting;
/**
 * Created  by Heimir Sverrisson on 06/05/2016.
 *
 * This class is just a pair of Coordinates for the start and end
 */
public class Route {
    private Coordinates start;
    private Coordinates end;

    public Route(Coordinates start, Coordinates end){
        this.start = start;
        this.end = end;
    }
    public Coordinates getStart() {
        return start;
    }
    public Coordinates getEnd() {
        return end;
    }
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("start: ");
        str.append(start);
        str.append(", end: ");
        str.append(end);
        return str.toString();
    }

}