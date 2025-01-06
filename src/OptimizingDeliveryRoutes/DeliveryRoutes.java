// Problem: Optimizing delivery routes for a logistics company in New York city.
package OptimizingDeliveryRoutes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class DeliveryRoutes {
    static class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    // Create graph
    public static void createGraph(ArrayList<Edge> graph[]) {
        for (int i = 0; i < graph.length; i++) {
            graph[i] = new ArrayList<>();
        }

        // Add roads (edges) between locations (vertices)
        graph[0].add(new Edge(0, 1, 10)); // New York -> Los Angeles
        graph[0].add(new Edge(0, 2, 5));  // New York -> Chicago
        graph[0].add(new Edge(0, 3, 15)); // New York -> Miami

        graph[1].add(new Edge(1, 2, 4));  // Los Angeles -> Chicago
        graph[1].add(new Edge(1, 4, 7));  // Los Angeles -> Houston

        graph[2].add(new Edge(2, 4, 8));  // Chicago -> Houston
        graph[2].add(new Edge(2, 5, 10)); // Chicago -> San Francisco

        graph[3].add(new Edge(3, 4, 6));  // Miami -> Houston
        graph[3].add(new Edge(3, 5, 12)); // Miami -> San Francisco
    }

    public static class Pair implements Comparable<Pair> {
        int node;
        int distance;
        public Pair(int node, int distance) {
            this.node = node;
            this.distance = distance;
        }

        @Override
        public int compareTo(Pair p2) {
            return this.distance - p2.distance; // Ascending order sorting by distance
        }
    }

    public static void dijkstra(ArrayList<Edge>[] graph, int sourceVertex, int noOfVertices, HashMap<Integer, String> cityNames) {
        PriorityQueue<Pair> priorityQueue = new PriorityQueue<>();
        StringBuilder path = new StringBuilder(" ");

        int[] distance = new int[noOfVertices];

        for (int i = 0; i < noOfVertices; i++) {
            if (i != sourceVertex) {
                distance[i] = Integer.MAX_VALUE;
            }
        }

        boolean[] visited = new boolean[noOfVertices];   // keeps track of the visited vertices.

        // Add the source vertex to the priority queue
        priorityQueue.add(new Pair(sourceVertex, 0));

        while (!priorityQueue.isEmpty()) {
            Pair currentNode = priorityQueue.remove(); // Get the node with the smallest distance

            if (!visited[currentNode.node]) {
                visited[currentNode.node] = true;  // Mark the current node as visited
                path.append(cityNames.get(currentNode.node)).append(" ");  // Use city names instead of indices

                for (int i = 0; i < graph[currentNode.node].size(); i++) {
                    Edge e = graph[currentNode.node].get(i);
                    int u = e.source;
                    int v = e.destination;

                    // Relaxation
                    if (distance[u] + e.weight < distance[v]) {
                        distance[v] = distance[u] + e.weight;
                        priorityQueue.add(new Pair(v, distance[v]));
                    }
                }
            }
        }

        System.out.println("Series of routes from warehouse(new work) to each city which is best optimum path: " + path);
        System.out.print("Time required to deliver product from warehouse(new work) to each city: ");
        System.out.println();
        for (int i = 0; i < noOfVertices; i++) {
            System.out.print("Time taken from Warehouse (" + cityNames.get(sourceVertex) + ") to " + cityNames.get(i) + " = ");
            System.out.println(distance[i] + " minutes");
        }
    }

    public static void main(String[] args) {
        int noOfVertices = 6;

        // Creating an array of ArrayLists to represent the graph
        ArrayList<Edge> graph[] = new ArrayList[noOfVertices];
        createGraph(graph);

        // Map vertex indices to actual city names
        HashMap<Integer, String> cityNames = new HashMap<>();
        cityNames.put(0, "New York");       // source vertex / warehouse
        cityNames.put(1, "Los Angeles");
        cityNames.put(2, "Chicago");
        cityNames.put(3, "Miami");
        cityNames.put(4, "Houston");
        cityNames.put(5, "San Francisco");

        dijkstra(graph, 0, noOfVertices, cityNames);
    }
}
