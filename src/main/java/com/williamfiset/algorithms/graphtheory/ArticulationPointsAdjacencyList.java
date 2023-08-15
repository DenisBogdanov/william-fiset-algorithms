/**
 * Finds all articulation points on an undirected graph.
 *
 * <p>Tested against HackerEarth online judge at:
 * https://www.hackerearth.com/practice/algorithms/graphs/articulation-points-and-bridges/tutorial
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class ArticulationPointsAdjacencyList {
  private final int n;
  private int id;
  private int rootNodeOutgoingEdgeCount;
  private int[] lowLinks;
  private int[] ids;
  private boolean[] visited;
  private boolean[] isArticulationPointArr;

  private final List<List<Integer>> adjList;

  public ArticulationPointsAdjacencyList(List<List<Integer>> adjList) {
    this.adjList = adjList;
    this.n = adjList.size();
  }

  public boolean[] findArticulationPoints() {
    id = 0;
    lowLinks = new int[n];
    ids = new int[n];
    visited = new boolean[n];
    isArticulationPointArr = new boolean[n];

    for (int i = 0; i < n; i++) {
      if (!visited[i]) {
        rootNodeOutgoingEdgeCount = 0;
        dfs(i, i, -1);
        isArticulationPointArr[i] = (rootNodeOutgoingEdgeCount > 1);
      }
    }

    return isArticulationPointArr;
  }

  private void dfs(int root, int currNode, int parent) {
    if (parent == root) rootNodeOutgoingEdgeCount++;

    visited[currNode] = true;
    lowLinks[currNode] = ids[currNode] = id++;

    List<Integer> edges = adjList.get(currNode);
    for (Integer neighbour : edges) {
      if (neighbour == parent) continue;
      if (!visited[neighbour]) {
        dfs(root, neighbour, currNode);
        lowLinks[currNode] = min(lowLinks[currNode], lowLinks[neighbour]);
        if (ids[currNode] <= lowLinks[neighbour]) {
          isArticulationPointArr[currNode] = true;
        }
      } else {
        lowLinks[currNode] = min(lowLinks[currNode], ids[neighbour]);
      }
    }
  }

  /* Graph helpers */

  // Initialize a graph with 'n' nodes.
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Add an undirected edge to a graph.
  public static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
    graph.get(to).add(from);
  }

  /* Example usage: */

  public static void main(String[] args) {
    testExample2();
  }

  private static void testExample1() {
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

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph);
    boolean[] isArticulationPoint = solver.findArticulationPoints();

    // Prints:
    // Node 2 is an articulation
    // Node 3 is an articulation
    // Node 5 is an articulation
    for (int i = 0; i < n; i++)
      if (isArticulationPoint[i]) System.out.printf("Node %d is an articulation\n", i);
  }

  // Tests a graph with 3 nodes in a line: A - B - C
  // Only node 'B' should be an articulation point.
  private static void testExample2() {
    int n = 3;
    List<List<Integer>> graph = createGraph(n);

    addEdge(graph, 0, 1);
    addEdge(graph, 1, 2);

    ArticulationPointsAdjacencyList solver = new ArticulationPointsAdjacencyList(graph);
    boolean[] isArticulationPoint = solver.findArticulationPoints();

    // Prints:
    // Node 1 is an articulation
    for (int i = 0; i < n; i++)
      if (isArticulationPoint[i]) System.out.printf("Node %d is an articulation\n", i);
  }
}
