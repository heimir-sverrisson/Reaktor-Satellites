package com.coolprimes.satrouting;

/**
 * Created: Heimir Sverrisson on 07/05/2016.
 *
 * This code uses the Dijkstra shortest path algorithm to
 * determine the best path from start to end.
 * The fact that two satellites cannot see each other if the
 * great circle distance between them i greater than their
 * reach added together is used to find what nodes are neighbors.
 *
 */
import java.util.ArrayList;

public class Dijkstra {
    private static final String LOG_TAG = "Dijkstra";
    private ArrayList<Node> nodes;

    public Dijkstra(Satellite[] satellites, Coordinates start, Coordinates end) {
        nodes = new ArrayList<Node>();
        nodes.add(new Node(-1, start, 0.0d, true, false));  // Start node
        nodes.add(new Node(-2, end, 0.0d, false, true));    // End node
        for(Satellite s : satellites){
            nodes.add(new Node(s.getId(), s.getCoordinates(), s.getReach(), false, false)); // Intermediate nodes
        }
    }

    public ArrayList<Node> shortestPath(){
        ArrayList<Node> unvisited = new ArrayList<Node>();
        Node currentNode = null;
        // Create the set of unvisited nodes and set current node
        for(Node n : nodes){
            if(n.isStart()) {
                currentNode = n;
                currentNode.setDistance(0.0d);
            }
            unvisited.add(n);
        }
        while(unvisited.size() > 0 && currentNode != null){
            currentNode = getClosest(unvisited);
            if(currentNode.isEnd())
                break;
            unvisited.remove(currentNode); // current node has been visited
            for(Node n : unvisited){
                double tentativeDistance = tentative(currentNode, n);
                if (isNeighbor(currentNode, n) && n.getDistance() > tentativeDistance) {
                    n.setDistance(tentativeDistance);
                }
            }
        } // while unvisited
        return walkPath();
    }

    /**
     * Walk the path from end to start but return it in correct order
     * @return shortest path from start to end
     */
    private ArrayList<Node> walkPath(){
        ArrayList<Node> shortList = new ArrayList<Node>();
        ArrayList<Node> unwalked = new ArrayList<Node>();
        Node currentNode = null;
        for(Node n : nodes){
            if(n.isEnd()) {
                currentNode = n;
            }
            unwalked.add(n);
        }
        while(!currentNode.isStart()){
            Node next = null;
            double distance = Double.POSITIVE_INFINITY;
            unwalked.remove(currentNode);
            for(Node n : unwalked){
               if(isNeighbor(currentNode,n)){
                   if(n.getDistance() < distance){
                       next = n;
                       distance = n.getDistance();
                   }
               }
            }
            shortList.add(currentNode);
            currentNode = next;
        }
        shortList.add(currentNode); // Adding the start node
        return reverse(shortList);
    }

    // Simple recursive function to reverse a list
    private ArrayList<Node> reverse(ArrayList<Node> list){
        if(list.size() > 1){
            Node node = list.remove(0);
            reverse(list);
            list.add(node);
        }
        return list;
    }


    private boolean isNeighbor(Node n, Node m){
        if(n == m){
            return false;
        }
        double distance = Compute.greatCircleDistance(n.getCoordinates(), m.getCoordinates());
        if(n.getReach() + m.getReach() < distance){
            return false;    // node is not reachable
        }
        return true;
    }

    // Return the closest node in the list based on distance
    // if the list is empty or all nodes are infinity return null
    private Node getClosest(ArrayList<Node> nodes){
        Node closest = null;
        double distance = Double.POSITIVE_INFINITY;
        for(Node n : nodes){
            if(n.getDistance() < distance){
                distance = n.getDistance();
                closest = n;
            }
        }
        return closest;
    }
    // Compute the tentative distance between nodes if they are not reachable
    // return infinity
    private double tentative(Node n, Node m){
        double distance = Compute.greatCircleDistance(n.getCoordinates(), m.getCoordinates());
        if(!isNeighbor(n, m)){
            return Double.POSITIVE_INFINITY;    // node is not reachable
        }
        return n.getDistance() + distance;
    }
}
