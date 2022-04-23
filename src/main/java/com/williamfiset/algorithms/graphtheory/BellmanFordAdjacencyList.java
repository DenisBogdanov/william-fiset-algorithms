/**
 * An implementation of the Bellman-Ford algorithm. The algorithm finds the shortest path between a
 * starting node and all other nodes in the graph. The algorithm also detects negative cycles.
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BellmanFordAdjacencyList {

  // A directed edge with a cost
  public static class Edge {
    double cost;
    int from, to;

    public Edge(int from, int to, double cost) {
      this.to = to;
      this.from = from;
      this.cost = cost;
    }
  }

  // Create a graph with V vertices
  @SuppressWarnings("unchecked")
  public static List<Edge>[] createGraph(final int V) {
    List<Edge>[] graph = new List[V];
    for (int i = 0; i < V; i++) graph[i] = new ArrayList<>();
    return graph;
  }

  // Helper function to add an edge to the graph
  public static void addEdge(List<Edge>[] graph, int from, int to, double cost) {
    graph[from].add(new Edge(from, to, cost));
  }

  /**
   * An implementation of the Bellman-Ford algorithm. The algorithm finds the shortest path between
   * a starting node and all other nodes in the graph. The algorithm also detects negative cycles.
   * If a node is part of a negative cycle then the minimum cost for that node is set to
   * Double.NEGATIVE_INFINITY.
   *
   * @param graph    - An adjacency list containing directed edges forming the graph
   * @param vertices - The number of vertices in the graph.
   * @param start    - The id of the starting node
   */
  public static double[] bellmanFord(List<Edge>[] graph, int vertices, int start) {
    double[] distances = new double[vertices];
    Arrays.fill(distances, Double.POSITIVE_INFINITY);
    distances[start] = 0;

    for (int i = 0; i < vertices - 1; i++) {
      for (List<Edge> edges : graph) {
        for (Edge edge : edges) {
          if (distances[edge.from] + edge.cost < distances[edge.to]) {
            distances[edge.to] = distances[edge.from] + edge.cost;
          }
        }
      }
    }

    for (int i = 0; i < vertices - 1; i++) {
      for (List<Edge> edges : graph) {
        for (Edge edge : edges) {
          if (distances[edge.from] + edge.cost < distances[edge.to]) {
            distances[edge.to] = Double.NEGATIVE_INFINITY;
          }
        }
      }
    }

    return distances;
  }

  public static void main(String[] args) {

    int E = 10, V = 9, start = 0;
    List<Edge>[] graph = createGraph(V);
    addEdge(graph, 0, 1, 1);
    addEdge(graph, 1, 2, 1);
    addEdge(graph, 2, 4, 1);
    addEdge(graph, 4, 3, -3);
    addEdge(graph, 3, 2, 1);
    addEdge(graph, 1, 5, 4);
    addEdge(graph, 1, 6, 4);
    addEdge(graph, 5, 6, 5);
    addEdge(graph, 6, 7, 4);
    addEdge(graph, 5, 7, 3);
    double[] d = bellmanFord(graph, V, start);

    for (int i = 0; i < V; i++)
      System.out.printf("The cost to get from node %d to %d is %.2f\n", start, i, d[i]);

    // Output:
    // The cost to get from node 0 to 0 is 0.00
    // The cost to get from node 0 to 1 is 1.00
    // The cost to get from node 0 to 2 is -Infinity
    // The cost to get from node 0 to 3 is -Infinity
    // The cost to get from node 0 to 4 is -Infinity
    // The cost to get from node 0 to 5 is 5.00
    // The cost to get from node 0 to 6 is 5.00
    // The cost to get from node 0 to 7 is 8.00
    // The cost to get from node 0 to 8 is Infinity

  }
}
