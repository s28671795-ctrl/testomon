/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.activemq.filter.AnyChildDestinationNode;
import org.apache.activemq.filter.DestinationNode;

public class DestinationMapNode
implements DestinationNode {
    protected static final String ANY_CHILD = "*";
    protected static final String ANY_DESCENDENT = ">";
    private DestinationMapNode parent;
    private List<Object> values = new ArrayList<Object>();
    private Map<String, DestinationNode> childNodes = new HashMap<String, DestinationNode>();
    private String path = "Root";
    private int pathLength;

    public DestinationMapNode(DestinationMapNode parent) {
        this.parent = parent;
        this.pathLength = parent == null ? 0 : parent.pathLength + 1;
    }

    @Override
    public DestinationNode getChild(String path) {
        return this.childNodes.get(path);
    }

    @Override
    public Collection<DestinationNode> getChildren() {
        return this.childNodes.values();
    }

    public int getChildCount() {
        return this.childNodes.size();
    }

    public DestinationMapNode getChildOrCreate(String path) {
        DestinationMapNode answer = (DestinationMapNode)this.childNodes.get(path);
        if (answer == null) {
            answer = this.createChildNode();
            answer.path = path;
            this.childNodes.put(path, answer);
        }
        return answer;
    }

    public List getValues() {
        return this.values;
    }

    public List removeValues() {
        ArrayList<Object> v = new ArrayList<Object>(this.values);
        this.values.clear();
        this.pruneIfEmpty();
        return v;
    }

    public Set removeDesendentValues() {
        HashSet answer = new HashSet();
        this.removeDesendentValues(answer);
        return answer;
    }

    protected void removeDesendentValues(Set answer) {
        ArrayList<DestinationNode> candidates = new ArrayList<DestinationNode>();
        for (Map.Entry<String, DestinationNode> child : this.childNodes.entrySet()) {
            candidates.add(child.getValue());
        }
        for (DestinationNode node : candidates) {
            answer.addAll(node.removeValues());
            answer.addAll(node.removeDesendentValues());
        }
    }

    public Set getDesendentValues() {
        HashSet answer = new HashSet();
        this.appendDescendantValues((Set)answer);
        return answer;
    }

    public void add(String[] paths, int idx, Object value) {
        if (idx >= paths.length) {
            this.values.add(value);
        } else {
            this.getChildOrCreate(paths[idx]).add(paths, idx + 1, value);
        }
    }

    public void set(String[] paths, int idx, Object value) {
        if (idx >= paths.length) {
            this.values.clear();
            this.values.add(value);
        } else {
            this.getChildOrCreate(paths[idx]).set(paths, idx + 1, value);
        }
    }

    public void remove(String[] paths, int idx, Object value) {
        if (idx >= paths.length) {
            this.values.remove(value);
            this.pruneIfEmpty();
        } else {
            this.getChildOrCreate(paths[idx]).remove(paths, ++idx, value);
        }
    }

    public void removeAll(Set<DestinationNode> answer, String[] paths, int startIndex) {
        DestinationNode node = this;
        int size = paths.length;
        for (int i = startIndex; i < size && node != null; ++i) {
            String path = paths[i];
            if (path.equals(ANY_DESCENDENT)) {
                answer.addAll(node.removeDesendentValues());
                break;
            }
            node.appendMatchingWildcards(answer, paths, i);
            node = path.equals(ANY_CHILD) ? new AnyChildDestinationNode(node) : node.getChild(path);
        }
        if (node != null) {
            answer.addAll(node.removeValues());
        }
    }

    public void appendDescendantValues(Set answer) {
        for (DestinationNode child : this.childNodes.values()) {
            answer.addAll(child.getValues());
            child.appendDescendantValues(answer);
        }
    }

    protected DestinationMapNode createChildNode() {
        return new DestinationMapNode(this);
    }

    public void appendMatchingWildcards(Set answer, String[] paths, int idx) {
        if (idx - 1 > this.pathLength) {
            return;
        }
        DestinationNode wildCardNode = this.getChild(ANY_CHILD);
        if (wildCardNode != null) {
            wildCardNode.appendMatchingValues(answer, paths, idx + 1);
        }
        if ((wildCardNode = this.getChild(ANY_DESCENDENT)) != null) {
            answer.addAll(wildCardNode.getValues());
            answer.addAll(wildCardNode.getDesendentValues());
        }
    }

    public void appendMatchingValues(Set answer, String[] paths, int idx) {
        this.appendMatchingValues(answer, paths, idx, true);
    }

    public void appendMatchingValues(Set<DestinationNode> answer, String[] paths, int startIndex, boolean deep) {
        DestinationNode node = this;
        boolean couldMatchAny = true;
        int size = paths.length;
        for (int i = startIndex; i < size && node != null; ++i) {
            String path = paths[i];
            if (deep && path.equals(ANY_DESCENDENT)) {
                answer.addAll(node.getDesendentValues());
                couldMatchAny = false;
                break;
            }
            node.appendMatchingWildcards(answer, paths, i);
            node = path.equals(ANY_CHILD) ? new AnyChildDestinationNode(node) : node.getChild(path);
        }
        if (node != null) {
            DestinationNode child;
            answer.addAll(node.getValues());
            if (couldMatchAny && (child = node.getChild(ANY_DESCENDENT)) != null) {
                answer.addAll(child.getValues());
            }
        }
    }

    public String getPath() {
        return this.path;
    }

    public boolean isEmpty() {
        return this.childNodes.isEmpty();
    }

    protected void pruneIfEmpty() {
        if (this.parent != null && this.childNodes.isEmpty() && this.values.isEmpty()) {
            this.parent.removeChild(this);
        }
    }

    protected void removeChild(DestinationMapNode node) {
        this.childNodes.remove(node.getPath());
        this.pruneIfEmpty();
    }
}

