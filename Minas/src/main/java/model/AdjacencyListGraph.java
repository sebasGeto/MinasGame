package model;

import java.util.*;

public class AdjacencyListGraph<T> implements Graph<T> {
    private Map<T, List<Edge<T>>> adjacencyList;

    public AdjacencyListGraph() {
        adjacencyList = new HashMap<>();
    }

    @Override
    public void addVertex(T vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    public void addEdge(T source, T target, int weight) {
        addVertex(source);
        addVertex(target);
        adjacencyList.get(source).add(new Edge<>(target, weight));
        adjacencyList.get(target).add(new Edge<>(source, weight)); // Grafo no dirigido
    }

    @Override
    public List<T> getNeighbors(T vertex) {
        List<T> neighbors = new ArrayList<>();
        if (adjacencyList.containsKey(vertex)) {
            for (Edge<T> edge : adjacencyList.get(vertex)) {
                neighbors.add(edge.target);
            }
        }
        return neighbors;
    }

    @Override
    public int getWeight(T source, T target) {
        if (adjacencyList.containsKey(source)) {
            for (Edge<T> edge : adjacencyList.get(source)) {
                if (edge.target.equals(target)) {
                    return edge.weight;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean hasVertex(T vertex) {
        return adjacencyList.containsKey(vertex);
    }

    @Override
    public boolean hasEdge(T source, T target) {
        return getWeight(source, target) != -1;
    }

    private static class Edge<T> {
        T target;
        int weight;

        public Edge(T target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }
}
