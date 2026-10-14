/*
 * Decompiled with CFR 0.152.
 */
package org.pf4j.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class DirectedGraph<V> {
    private Map<V, List<V>> neighbors = new HashMap<V, List<V>>();

    public void addVertex(V vertex) {
        if (this.containsVertex(vertex)) {
            return;
        }
        this.neighbors.put(vertex, new ArrayList());
    }

    public boolean containsVertex(V vertex) {
        return this.neighbors.containsKey(vertex);
    }

    public void removeVertex(V vertex) {
        this.neighbors.remove(vertex);
    }

    public void addEdge(V from, V to) {
        this.addVertex(from);
        this.addVertex(to);
        this.neighbors.get(from).add(to);
    }

    public void removeEdge(V from, V to) {
        if (!this.containsVertex(from)) {
            throw new IllegalArgumentException("Nonexistent vertex " + from);
        }
        if (!this.containsVertex(to)) {
            throw new IllegalArgumentException("Nonexistent vertex " + to);
        }
        this.neighbors.get(from).remove(to);
    }

    public List<V> getNeighbors(V vertex) {
        return this.containsVertex(vertex) ? this.neighbors.get(vertex) : new ArrayList();
    }

    public Map<V, Integer> outDegree() {
        HashMap<V, Integer> result = new HashMap<V, Integer>();
        for (V vertex : this.neighbors.keySet()) {
            result.put(vertex, this.neighbors.get(vertex).size());
        }
        return result;
    }

    public Map<V, Integer> inDegree() {
        HashMap<V, Integer> result = new HashMap<V, Integer>();
        for (V vertex : this.neighbors.keySet()) {
            result.put(vertex, 0);
        }
        for (V from : this.neighbors.keySet()) {
            for (V to : this.neighbors.get(from)) {
                result.put(to, (Integer)result.get(to) + 1);
            }
        }
        return result;
    }

    public List<V> topologicalSort() {
        Map<Integer, Integer> degree = this.inDegree();
        Stack<V> zeroVertices = new Stack<V>();
        for (V v : degree.keySet()) {
            if (degree.get(v) != 0) continue;
            zeroVertices.push(v);
        }
        ArrayList result = new ArrayList();
        while (!zeroVertices.isEmpty()) {
            Object vertex = zeroVertices.pop();
            result.add(vertex);
            for (V neighbor : this.neighbors.get(vertex)) {
                degree.put((Integer)neighbor, degree.get(neighbor) - 1);
                if (degree.get(neighbor) != 0) continue;
                zeroVertices.push(neighbor);
            }
        }
        if (result.size() != this.neighbors.size()) {
            return null;
        }
        return result;
    }

    public List<V> reverseTopologicalSort() {
        List<V> list = this.topologicalSort();
        if (list == null) {
            return null;
        }
        Collections.reverse(list);
        return list;
    }

    public boolean isDag() {
        return this.topologicalSort() != null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (V vertex : this.neighbors.keySet()) {
            sb.append("\n   ").append(vertex).append(" -> ").append(this.neighbors.get(vertex));
        }
        return sb.toString();
    }
}

