package manev.test.algorythms.cyclecheck;

import java.util.ArrayList;
import java.util.List;

public class Cycle {
    public static void main(String[] args) {
        int[][] graph1 = new int[][] {
                new int[] { 1 },
                new int[] { 2 },
                new int[] { 3 },
                new int[] { 4 },
                new int[] { }
        };

        int[][] graph2 = new int[][] {
                new int[] { 1, 2 },
                new int[] { 2 },
                new int[] { 0, 3 },
                new int[] {  }
        };

        int[][] graph3 = new int[][] {
                new int[] { 1, 3},
                new int[] { 2 },
                new int[] {  3 },
                new int[] { 1 }
        };

        System.out.println("Cycle exist? " + findCycleInDirectedGraph(graph3));
    }

    private static boolean findCycleInDirectedGraph(int[][] graph) {
        boolean[] visited = new boolean[graph.length];
        boolean[] inCurrentIteration = new boolean[graph.length];
        List<Integer> currentIteration = new ArrayList<>();

        for (int i = 0; i < graph.length; ++i) {
            boolean cycleFound = dfs(i, graph, visited, inCurrentIteration, currentIteration);
            if (cycleFound) {
                return true;
            }

        }

        return false;
    }

    private static boolean dfs(int currentVertex, int[][] graph, boolean[] visited, boolean[] inCurrentIteration, List<Integer> currentIteration) {

        if (inCurrentIteration[currentVertex]) {
            System.out.println("Cycle found: " + currentIteration.subList(currentIteration.indexOf(currentVertex), currentIteration.size()));
            return true;
        } else if (visited[currentVertex]) {
            return false;
        }

        visited[currentVertex] = true;
        inCurrentIteration[currentVertex] = true;
        currentIteration.add(currentVertex);

        //recursive calls

        int[] adj = graph[currentVertex];

        for (int nextAdj : adj) {
            boolean cycleFound = dfs(nextAdj, graph, visited, inCurrentIteration, currentIteration);
            if (cycleFound) {
                return true;
            }
        }

        inCurrentIteration[currentVertex] = false;
        currentIteration.removeLast();

        return false;
    }

}
