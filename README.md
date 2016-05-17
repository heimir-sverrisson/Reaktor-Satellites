# Reaktor-Satellites
A solution to the orbital challenge by [Reaktor] (http://reaktor.com/orbital-challenge)
It uses simple geometry to calculate the maximum reach of a satellite based on its altitude.
Then it calculates the great-circle distance between two points to find out if the the
distance is greater than their combined reach.
Using these simple properties the shortest path is calculated using [Dijkstra's algorithm]
(https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm).
The whole thing can then be visualized using [Cesium](https://cesiumjs.org) with the 
Javascript genereated by the program.
