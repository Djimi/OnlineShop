package manev.test.algorithms.dijkstra;

import com.google.common.graph.Graph;
import lombok.Data;

import java.util.*;

public class Dijkstra {
    public static void main(String[] args) {

        Graph graph = new Graph(false);
        graph.addEdge(0, 1, 2);
        graph.addEdge(0, 2, 6);
        graph.addEdge(1, 3, 5);
        graph.addEdge(2, 3, 8);
        graph.addEdge(3, 4, 10);
        graph.addEdge(3, 5, 15);
        graph.addEdge(4, 5, 6);
        graph.addEdge(4, 6, 2);
        graph.addEdge(5, 6, 6);

        Graph graph2 = new Graph(false);

        graph2.addEdge(0, 1, 4);
        graph2.addEdge(0, 7, 8);
        graph2.addEdge(1, 2, 8);
        graph2.addEdge(1, 7, 11);
        graph2.addEdge(7, 6, 1);
        graph2.addEdge(7, 8, 7);
        graph2.addEdge(6, 8, 6);
        graph2.addEdge(6, 5, 2);
        graph2.addEdge(8, 2, 2);
        graph2.addEdge(2, 3, 7);
        graph2.addEdge(2, 5, 4);
        graph2.addEdge(3, 4, 9);
        graph2.addEdge(3, 5, 14);
        graph2.addEdge(4, 5, 10);

        Graph graph3 = new Graph(true);
        graph3.addEdge(0, 1, 1);
        graph3.addEdge(0, 2, 1);
        graph3.addEdge(1, 2, 1);
        graph3.addEdge(2, 3, 1);
        graph3.addEdge(2, 0, 1);
        graph3.addEdge(3, 3, 1);

        //        int[] parent = new int[graph2.getNumberOfVertexes()];
        //        List<Integer> shortestPath = shortestPathByDijkstraUsingMinHeapPersistingParent(graph2, parent, 0);
        //
        //        System.out.println("Shortest paths are: " + Arrays.asList(shortestPath));
        //        System.out.println("Shortest path for 8 is: " + getShortestPath(parent, 8));
        //        System.out.println("Shortest path for 4 is: " + getShortestPath(parent, 4));

        iterativeDFS(graph3, 0);

        for (int i = 0; i < 50; ++i) {
            Runnable r = () -> {

                int x = 1;
                while (1 == 1) {
                    ++x;
                }

            };

            new Thread(r).start();
        }
    }

    private static void iterativeDFS(Graph graph, int startIndex) {

        boolean[] visited = new boolean[graph.getNumberOfVertexes()];

        Deque<IterativeEdge> stack = new ArrayDeque<>();
        List<Edge> adjacents = graph.getAdjacents(startIndex);
        System.out.println("Visiting: " + startIndex);
        visited[startIndex] = true;
        if (adjacents.size() > 0) {
            stack.addFirst(new IterativeEdge(startIndex, 0));
        }

        while (!stack.isEmpty()) {
            IterativeEdge lastVertex = stack.getFirst();

            int currentVertex = graph.getAdjacents(lastVertex.getIndex()).get(lastVertex.getNextAdjacent()).getDestNode();
            System.out.println("Visiting: " + currentVertex);
            visited[currentVertex] = true;

            int nextVertex = currentVertex;
            int nextAdjIndex = 0;
            boolean nextVertexFound = false;

            while (!nextVertexFound && !stack.isEmpty()) {
                List<Edge> nextAdj = graph.getAdjacents(nextVertex);

                for (int i = nextAdjIndex; i < nextAdj.size(); ++i) {
                    if (visited[nextAdj.get(i).getDestNode()] == false) {
                        stack.addFirst(new IterativeEdge(nextVertex, i));
                        nextVertexFound = true;
                        break;
                    }
                }

                if (!nextVertexFound) {
                    //go one level back
                    IterativeEdge tmp = stack.removeFirst();
                    nextVertex = tmp.getIndex();
                    nextAdjIndex = tmp.getNextAdjacent() + 1;
                }
            }
        }

    }

    @Data
    private static class IterativeEdge {

        private final int index;

        private final int nextAdjacent;
    }

    private static String getShortestPath(int[] parent, int target) {
        if (parent[target] == -1) {
            return Integer.toString(target);
        }

        String shortestPath = getShortestPath(parent, parent[target]);

        return shortestPath + " " + target;
    }

    private static List<Integer> shortestPathByDijkstraUsingMinHeapPersistingParent(Graph graph, int parent[], int startV) {

        int numberOfVertexes = graph.getNumberOfVertexes();

        List<Integer> shortestDist = new ArrayList<>(numberOfVertexes);

        for (int i = 0; i < numberOfVertexes; ++i) {
            if (i == startV) {
                shortestDist.add(0);
            } else {
                shortestDist.add(Integer.MAX_VALUE);
            }
        }

        parent[startV] = -1;

        boolean[] minPathFound = new boolean[numberOfVertexes];

        PriorityQueue<GPair> priorityQueue = new PriorityQueue<>(numberOfVertexes, Comparator.comparingInt(x -> x.getShortestPathToVertex()));
        priorityQueue.add(new GPair(startV, shortestDist.get(startV)));

        while (!priorityQueue.isEmpty()) {

            int currentIndex = priorityQueue.poll().vertexIndex;

            List<Edge> adjacents = graph.getAdjacents(currentIndex);

            for (Edge edge : adjacents) {
                if (!minPathFound[edge.getDestNode()]) {
                    int currentWeight = shortestDist.get(currentIndex) + edge.getWeight();
                    if (shortestDist.get(edge.getDestNode()) > currentWeight) {
                        shortestDist.set(edge.getDestNode(), currentWeight);
                        priorityQueue.add(new GPair(edge.getDestNode(), currentWeight));
                        parent[edge.getDestNode()] = currentIndex;
                    }
                }
            }

            minPathFound[currentIndex] = true;
        }

        return shortestDist;
    }

    @Data
    private static class GPair {
        private final int vertexIndex;

        private final int shortestPathToVertex;
    }

    private static List<Integer> shortestPathByDijkstra(Graph graph, int startV) {

        int numberOfVertexes = graph.getNumberOfVertexes();

        List<Integer> shortestDist = new ArrayList<>(numberOfVertexes);

        for (int i = 0; i < numberOfVertexes; ++i) {
            if (i == startV) {
                shortestDist.add(0);
            } else {
                shortestDist.add(Integer.MAX_VALUE);
            }
        }

        boolean[] minPathFound = new boolean[numberOfVertexes];

        for (int i = 0; i < numberOfVertexes; ++i) {
            int currentIndex = findMinDistance(shortestDist, minPathFound);

            List<Edge> adjacents = graph.getAdjacents(currentIndex);

            for (Edge edge : adjacents) {
                if (!minPathFound[edge.getDestNode()]) {
                    int currentWeight = shortestDist.get(currentIndex) + edge.getWeight();
                    if (shortestDist.get(edge.getDestNode()) > currentWeight) {
                        shortestDist.set(edge.getDestNode(), currentWeight);
                    }
                }
            }

            minPathFound[currentIndex] = true;
        }

        return shortestDist;
    }

    private static int findMinDistance(List<Integer> shortestDist, boolean[] minPathFound) {
        int minValueIndex = -1;
        int minValue = Integer.MAX_VALUE;

        for (int i = 0; i < minPathFound.length; ++i) {
            if (!minPathFound[i]) {
                Integer currentVal = shortestDist.get(i);
                if (currentVal < minValue) {
                    minValueIndex = i;
                    minValue = currentVal;
                }

            }
        }

        return minValueIndex;
    }

    @Data
    private static class Graph {

        private final boolean isDirected;

        private List<List<Edge>> edges = new ArrayList<>();

        //        public void init(int numberOfVertexes) {
        //            edges = new ArrayList<>(numberOfVertexes);
        //            for (int i = 0; i < edges.size(); ++i) {
        //                edges.add(new ArrayList<>());
        //            }
        //        }

        public int getNumberOfVertexes() {
            return edges.size();
        }

        public List<Edge> getAdjacents(int vertex) {
            return edges.get(vertex);
        }

        public void addEdge(int source, int destination, int weight) {
            int maxIndex = Math.max(source, destination);
            while (edges.size() <= maxIndex) {
                edges.add(new ArrayList<>());
            }

            edges.get(source).add(new Edge(destination, weight));
            if (!isDirected) {
                edges.get(destination).add(new Edge(source, weight));
            }
        }
    }

    @Data
    private static class Edge {
        private final int destNode;

        private final int weight;
    }

}
