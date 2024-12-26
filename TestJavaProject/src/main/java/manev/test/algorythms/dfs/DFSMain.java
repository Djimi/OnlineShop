package manev.test.algorythms.dfs;

import java.util.*;

public class DFSMain {
    public static void main(String[] args) {

        int[][] graph1 = new int[][]{
                new int[] {1,2},
                new int[] {0,2},
                new int[] {0 ,1 ,3 , 4},
                new int[] {2},
                new int[] {2}
        };

        int[][] graph2 = new int[][]{
                new int[] {1,2, 3},
                new int[] {0},
                new int[] {0, 4},
                new int[] {0}, new int[] { 2 }
        };

        int[][] graph3 = new int[][]{
                new int[] {1,4},
                new int[] {0, 3},
                new int[] {4},
                new int[] {0},
                new int[] {0, 2}
        };

        int[][] graph4 = new int[][]{
                new int[] {1,2},
                new int[] {0, 3},
                new int[] {0, 4},
                new int[] {1},
                new int[] {2}
        };

        dfsRecursive(graph4);
    }


    private static void dfsRecursive(int[][] graph) {
        if (graph != null) {
            dfsRecursiveInternal(graph, 0, new ArrayList<>());
        }
    }

    private static void dfsRecursiveInternal(int[][] graph, int current, ArrayList<Integer> iterated) {
        if (!iterated.contains(current)) {
            System.out.println(current);
            iterated.add(current);
            int[] adjacents = graph[current];
            for (int adj = 0; adj < adjacents.length; adj++) {
                dfsRecursiveInternal(graph, adjacents[adj], iterated);
            }
        }

    }

}
