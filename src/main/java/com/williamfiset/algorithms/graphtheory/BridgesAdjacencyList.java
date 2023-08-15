/**
 * Finds all the bridges on an undirected graph.
 *
 * <p>Test against HackerEarth online judge at:
 * https://www.hackerearth.com/practice/algorithms/graphs/articulation-points-and-bridges/tutorial
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class BridgesAdjacencyList {
  private final int n;
  private int id;
  private int[] lowLinks;
  private int[] ids;
  private boolean[] visited;

  private final List<List<Integer>> adjList;

  public BridgesAdjacencyList(List<List<Integer>> adjList) {
    this.adjList = adjList;
    this.n = adjList.size();
  }

  public List<Integer> findBridges() {
    id = 0;
    lowLinks = new int[n];
    ids = new int[n];
    visited = new boolean[n];

    List<Integer> bridges = new ArrayList<>();
    for (int i = 0; i < n; i++) if (!visited[i]) dfs(i, -1, bridges);
    return bridges;
  }

  private void dfs(int currNode, int parent, List<Integer> bridges) {
    visited[currNode] = true;
    lowLinks[currNode] = ids[currNode] = ++id;

    for (Integer neighbour : adjList.get(currNode)) {
      if (neighbour == parent) continue;
      if (!visited[neighbour]) {
        dfs(neighbour, currNode, bridges);
        lowLinks[currNode] = min(lowLinks[currNode], lowLinks[neighbour]);
        if (ids[currNode] < lowLinks[neighbour]) {
          bridges.add(currNode);
          bridges.add(neighbour);
        }
      } else {
        lowLinks[currNode] = min(lowLinks[currNode], ids[neighbour]);
      }
    }
  }

  /* Example usage: */

  public static void main(String[] args) {

    int n = 9;
    List<List<Integer>> graph = createGraph(n);

    addEdge(graph, 0, 1);
    addEdge(graph, 0, 2);
    addEdge(graph, 1, 2);
    addEdge(graph, 2, 3);
    addEdge(graph, 3, 4);
    addEdge(graph, 2, 5);
    addEdge(graph, 5, 6);
    addEdge(graph, 6, 7);
    addEdge(graph, 7, 8);
    addEdge(graph, 8, 5);

    BridgesAdjacencyList solver = new BridgesAdjacencyList(graph);
    List<Integer> bridges = solver.findBridges();

    // Prints:
    // Bridge between nodes: 3 and 4
    // Bridge between nodes: 2 and 3
    // Bridge between nodes: 2 and 5
    for (int i = 0; i < bridges.size() / 2; i++) {
      int node1 = bridges.get(2 * i);
      int node2 = bridges.get(2 * i + 1);
      System.out.printf("Bridge between nodes: %d and %d\n", node1, node2);
    }
  }

  // Initialize graph with 'n' nodes.
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Add undirected edge to graph.
  public static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }
}
