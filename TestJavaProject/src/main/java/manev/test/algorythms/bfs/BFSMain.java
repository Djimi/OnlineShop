package manev.test.algorythms.bfs;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BFSMain {
    public static void main(String[] args) {
        int[][] graph1 = new int[][] {
                new int[] {2},
                new int[] {2, 4, 5},
                new int[] {0, 1},
                new int[] {5},
                new int[] {1},
                new int[] {3}
        };

        bfs(graph1, 0);
    }

    private static void bfs(int[][] graph, int startIndex) {
        if (graph == null || graph.length == 0) {
            return;
        }


        boolean[] visited = new boolean[graph.length];

        Queue<Integer> queue = new ArrayDeque<>();

        queue.add(startIndex);

        while (!queue.isEmpty()) {
            Integer currentIndex = queue.poll();

            if(visited[currentIndex]){
                continue;
            }

            System.out.println(currentIndex);
            visited[currentIndex] = true;

            queue.addAll(IntStream.of(graph[currentIndex]).boxed().collect(Collectors.toList()));
        }
    }

}
