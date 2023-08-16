/**
 * An implementation of Tarjan's Strongly Connected Components algorithm using an adjacency list.
 *
 * <p>Verified against:
 *
 * <ul>
 *   <li>https://open.kattis.com/problems/equivalences
 *   <li>https://open.kattis.com/problems/runningmom
 *   <li>https://www.hackerearth.com/practice/algorithms/graphs/strongly-connected-components/tutorial
 * </ul>
 *
 * <p>Time complexity: O(V+E)
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 */
package com.williamfiset.algorithms.graphtheory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.min;

public class TarjanSccSolverAdjacencyList {

  private final int n;
  private final List<List<Integer>> adjList;

  private int id;
  private int sccCount;
  private boolean[] visited;
  private int[] ids;
  private int[] low;
  private int[] sccs;
  private Deque<Integer> stack;

  private static final int UNVISITED = -1;

  public TarjanSccSolverAdjacencyList(List<List<Integer>> adjList) {
    if (adjList == null) throw new IllegalArgumentException("Graph cannot be null.");
    n = adjList.size();
    this.adjList = adjList;
  }

  public void solve() {
    ids = new int[n];
    low = new int[n];
    sccs = new int[n];
    visited = new boolean[n];
    stack = new ArrayDeque<>();
    Arrays.fill(ids, UNVISITED);

    for (int i = 0; i < n; i++) {
      if (ids[i] == UNVISITED) {
        dfs(i);
      }
    }
  }

  public static void main(String[] arg) {
    int n = 8;
    List<List<Integer>> graph = createGraph(n);

    addEdge(graph, 6, 0);
    addEdge(graph, 6, 2);
    addEdge(graph, 3, 4);
    addEdge(graph, 6, 4);
    addEdge(graph, 2, 0);
    addEdge(graph, 0, 1);
    addEdge(graph, 4, 5);
    addEdge(graph, 5, 6);
    addEdge(graph, 3, 7);
    addEdge(graph, 7, 5);
    addEdge(graph, 1, 2);
    addEdge(graph, 7, 3);
    addEdge(graph, 5, 0);

    TarjanSccSolverAdjacencyList solver = new TarjanSccSolverAdjacencyList(graph);

    int[] sccs = solver.sccs;
    Map<Integer, List<Integer>> multimap = new HashMap<>();
    for (int i = 0; i < n; i++) {
      if (!multimap.containsKey(sccs[i])) multimap.put(sccs[i], new ArrayList<>());
      multimap.get(sccs[i]).add(i);
    }

    // Prints:
    // Number of Strongly Connected Components: 3
    // Nodes: [0, 1, 2] form a Strongly Connected Component.
    // Nodes: [3, 7] form a Strongly Connected Component.
    // Nodes: [4, 5, 6] form a Strongly Connected Component.
    System.out.printf("Number of Strongly Connected Components: %d\n", solver.sccCount);
    for (List<Integer> scc : multimap.values()) {
      System.out.println("Nodes: " + scc + " form a Strongly Connected Component.");
    }
  }

  // Initializes adjacency list with n nodes.
  public static List<List<Integer>> createGraph(int n) {
    List<List<Integer>> graph = new ArrayList<>(n);
    for (int i = 0; i < n; i++) graph.add(new ArrayList<>());
    return graph;
  }

  // Adds a directed edge from node 'from' to node 'to'
  public static void addEdge(List<List<Integer>> graph, int from, int to) {
    graph.get(from).add(to);
  }

  /* Example usage: */

  private void dfs(int at) {
    ids[at] = low[at] = id++;
    stack.push(at);
    visited[at] = true;

    for (int to : adjList.get(at)) {
      if (ids[to] == UNVISITED) {
        dfs(to);
      }

      if (visited[to]) {
        low[at] = min(low[at], low[to]);
      }
    }

    // on recursive callback, if we're at the root node (start of SCC)
    // empty the seen stack until back to root
    if (ids[at] == low[at]) {
      for (int node = stack.pop(); ; node = stack.pop()) {
        visited[node] = false;
        sccs[node] = sccCount;
        if (node == at) break;
      }
      sccCount++;
    }
  }
}
