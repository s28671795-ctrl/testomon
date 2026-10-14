/*
 * Decompiled with CFR 0.152.
 */
package liquibase.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import liquibase.Scope;

public class DependencyUtil {

    public static interface NodeValueListener<T> {
        public void evaluating(T var1);
    }

    private static class GraphNode<T> {
        public T value;
        private List<GraphNode<T>> comingInNodes;
        private List<GraphNode<T>> goingOutNodes;

        private GraphNode() {
        }

        public void addComingInNode(GraphNode<T> node) {
            if (this.comingInNodes == null) {
                this.comingInNodes = new ArrayList<GraphNode<T>>();
            }
            this.comingInNodes.add(node);
        }

        public void addGoingOutNode(GraphNode<T> node) {
            if (this.goingOutNodes == null) {
                this.goingOutNodes = new ArrayList<GraphNode<T>>();
            }
            this.goingOutNodes.add(node);
        }

        public List<GraphNode<T>> getComingInNodes() {
            return this.comingInNodes;
        }

        public List<GraphNode<T>> getGoingOutNodes() {
            return this.goingOutNodes;
        }
    }

    public static class DependencyGraph<T> {
        private final LinkedHashMap<T, GraphNode<T>> nodes = new LinkedHashMap();
        private final NodeValueListener<T> listener;
        private final List<GraphNode<T>> evaluatedNodes = new ArrayList<GraphNode<T>>();
        private Integer recursiveSizeCheck;

        public DependencyGraph(NodeValueListener<T> listener) {
            this.listener = listener;
        }

        public void add(T evalFirstValue, T evalAfterValue) {
            GraphNode<T> firstNode = null;
            GraphNode<T> afterNode = null;
            if (this.nodes.containsKey(evalFirstValue)) {
                firstNode = this.nodes.get(evalFirstValue);
            } else {
                firstNode = this.createNode(evalFirstValue);
                this.nodes.put(evalFirstValue, firstNode);
            }
            if (this.nodes.containsKey(evalAfterValue)) {
                afterNode = this.nodes.get(evalAfterValue);
            } else {
                afterNode = this.createNode(evalAfterValue);
                this.nodes.put(evalAfterValue, afterNode);
            }
            firstNode.addGoingOutNode(afterNode);
            afterNode.addComingInNode(firstNode);
        }

        private GraphNode<T> createNode(T value) {
            GraphNode node = new GraphNode();
            node.value = value;
            return node;
        }

        public void computeDependencies() {
            List<GraphNode<T>> orphanNodes = this.getOrphanNodes();
            ArrayList<GraphNode<T>> nextNodesToDisplay = new ArrayList<GraphNode<T>>();
            if (orphanNodes != null) {
                for (GraphNode<T> node : orphanNodes) {
                    this.listener.evaluating(node.value);
                    this.evaluatedNodes.add(node);
                    nextNodesToDisplay.addAll(node.getGoingOutNodes());
                }
                this.computeDependencies(nextNodesToDisplay);
            }
        }

        private void computeDependencies(List<GraphNode<T>> nodes) {
            ArrayList<GraphNode<T>> nextNodesToDisplay = null;
            for (GraphNode<T> node : nodes) {
                if (this.isAlreadyEvaluated(node)) continue;
                List<GraphNode<T>> comingInNodes = node.getComingInNodes();
                if (this.areAlreadyEvaluated(comingInNodes)) {
                    this.listener.evaluating(node.value);
                    this.evaluatedNodes.add(node);
                    List<GraphNode<T>> goingOutNodes = node.getGoingOutNodes();
                    if (goingOutNodes == null) continue;
                    if (nextNodesToDisplay == null) {
                        nextNodesToDisplay = new ArrayList();
                    }
                    nextNodesToDisplay.addAll(goingOutNodes);
                    continue;
                }
                if (nextNodesToDisplay == null) {
                    nextNodesToDisplay = new ArrayList<GraphNode<T>>();
                }
                nextNodesToDisplay.add(node);
            }
            if (nextNodesToDisplay != null && !nextNodesToDisplay.isEmpty()) {
                int recursiveSizeDepth = this.recursiveSizeDepth(nextNodesToDisplay);
                if (recursiveSizeDepth < 0 || this.recursiveSizeCheck != null && recursiveSizeDepth >= this.recursiveSizeCheck) {
                    GraphNode nodeToRemove = null;
                    int nodeToRemoveLinks = Integer.MAX_VALUE;
                    for (GraphNode graphNode : nextNodesToDisplay) {
                        List links = graphNode.getComingInNodes();
                        if (links == null || links.size() >= nodeToRemoveLinks) continue;
                        nodeToRemove = graphNode;
                        nodeToRemoveLinks = links.size();
                    }
                    if (nodeToRemove != null) {
                        Scope.getCurrentScope().getLog(this.getClass()).fine("Potential StackOverflowException. Pro-actively removing " + nodeToRemove.value + " with " + nodeToRemoveLinks + " incoming nodes");
                        nextNodesToDisplay.remove(nodeToRemove);
                    }
                }
                if (recursiveSizeDepth >= 0) {
                    this.recursiveSizeCheck = recursiveSizeDepth;
                }
                this.computeDependencies(nextNodesToDisplay);
            }
        }

        private int recursiveSizeDepth(List<GraphNode<T>> nodes) {
            if (nodes == null) {
                return 0;
            }
            int sum = 0;
            for (GraphNode<T> node : nodes) {
                int depth = this.recursiveSizeDepth(node, 0);
                if (depth < 0) {
                    return -1;
                }
                sum += depth;
            }
            return sum;
        }

        private int recursiveSizeDepth(GraphNode<T> node, int safetyCounter) {
            if (safetyCounter > 1000) {
                return -1;
            }
            if (this.isAlreadyEvaluated(node)) {
                return 0;
            }
            if (node.getGoingOutNodes() == null || node.getGoingOutNodes().isEmpty()) {
                return 1;
            }
            int sum = 0;
            ++safetyCounter;
            for (GraphNode<T> n : node.getGoingOutNodes()) {
                int depth = this.recursiveSizeDepth(n, safetyCounter);
                if (depth < 0) {
                    return -1;
                }
                sum += depth;
            }
            return node.getGoingOutNodes().size() + sum;
        }

        private boolean isAlreadyEvaluated(GraphNode<T> node) {
            return this.evaluatedNodes.contains(node);
        }

        private boolean areAlreadyEvaluated(List<GraphNode<T>> nodes) {
            return this.evaluatedNodes.containsAll(nodes);
        }

        private List<GraphNode<T>> getOrphanNodes() {
            ArrayList<GraphNode<T>> orphanNodes = null;
            Set<T> keys2 = this.nodes.keySet();
            for (T key : keys2) {
                GraphNode<T> node = this.nodes.get(key);
                if (node.getComingInNodes() != null) continue;
                if (orphanNodes == null) {
                    orphanNodes = new ArrayList<GraphNode<T>>();
                }
                orphanNodes.add(node);
            }
            return orphanNodes;
        }
    }
}

