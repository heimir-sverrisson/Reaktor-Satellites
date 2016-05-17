package com.coolprimes.satrouting;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created: Heimir Sverrisson on 06/05/2016.
 *
 * Misc. computing routines needed in other places.
 *
 */
public class Compute {
    private static final double r = 6371.0d; // Radius of earth in km
    private static final double toRadians = Math.PI / 180.0d;

    /**
     * Compute the distance from its overhead point the satellite can reach
     * @param altitude in km
     * @return great-circle distance reachable
     */
    public static double satelliteReach(double altitude){
        double alpha = Math.atan2(Math.sqrt(2.0d * r * altitude + altitude * altitude), r);
        double alpha_deg = 180.0d * alpha/ Math.PI;
        double dist = r * alpha;
        return dist;
    }

    public static double greatCircleDistance(Coordinates a, Coordinates b){
        double aLong = a.getLongitude() * toRadians;
        double aLat = a.getLatitude() * toRadians;
        double bLong = b.getLongitude() * toRadians;
        double bLat = b.getLatitude() * toRadians;
        double cosAngle = Math.sin(aLat) * Math.sin(bLat) +
                Math.cos(aLat) * Math.cos(bLat) * Math.cos(bLong - aLong);
        if(cosAngle >= 1.0d){
            return 0.0d;
        }
        return Math.acos(cosAngle) * r;
    }

    /**
     * Return the set of satellites visible from a give long, lat
     * @return
     */
    public static Satellite[] visible(Satellite satellites[], Coordinates a){
        ArrayList<Satellite> visibleSet = new ArrayList<Satellite>();
        for(Satellite s : satellites){
            double distToSatellite = greatCircleDistance(a, s.getCoordinates());
            if(s.getReach() > distToSatellite){
                visibleSet.add(s);  // Add satellite if it's within reach
            }
        }
        Satellite[] areVisible = new Satellite[visibleSet.size()];
        return visibleSet.toArray(areVisible);
    }
}
