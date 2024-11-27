package model;

import java.util.*;

public interface Graph<T> {
    void addVertex(T vertex);
    void addEdge(T source, T target, int weight);
    List<T> getNeighbors(T vertex);
    int getWeight(T source, T target);
    boolean hasVertex(T vertex);
    boolean hasEdge(T source, T target);
}
