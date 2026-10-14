/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import org.apache.activemq.filter.DestinationNode;

public class AnyChildDestinationNode
implements DestinationNode {
    private DestinationNode node;

    public AnyChildDestinationNode(DestinationNode node) {
        this.node = node;
    }

    public void appendMatchingValues(Set answer, String[] paths, int startIndex) {
        for (DestinationNode child : this.getChildNodes()) {
            child.appendMatchingValues(answer, paths, startIndex);
        }
    }

    public void appendMatchingWildcards(Set answer, String[] paths, int startIndex) {
        for (DestinationNode child : this.getChildNodes()) {
            child.appendMatchingWildcards(answer, paths, startIndex);
        }
    }

    public void appendDescendantValues(Set answer) {
        for (DestinationNode child : this.getChildNodes()) {
            child.appendDescendantValues(answer);
        }
    }

    @Override
    public DestinationNode getChild(String path) {
        final ArrayList<DestinationNode> list = new ArrayList<DestinationNode>();
        for (DestinationNode child : this.getChildNodes()) {
            DestinationNode answer = child.getChild(path);
            if (answer == null) continue;
            list.add(answer);
        }
        if (!list.isEmpty()) {
            return new AnyChildDestinationNode(this){

                @Override
                protected Collection getChildNodes() {
                    return list;
                }
            };
        }
        return null;
    }

    public Collection getDesendentValues() {
        ArrayList<DestinationNode> answer = new ArrayList<DestinationNode>();
        for (DestinationNode child : this.getChildNodes()) {
            answer.addAll(child.getDesendentValues());
        }
        return answer;
    }

    public Collection getValues() {
        ArrayList<DestinationNode> answer = new ArrayList<DestinationNode>();
        for (DestinationNode child : this.getChildNodes()) {
            answer.addAll(child.getValues());
        }
        return answer;
    }

    public Collection getChildren() {
        ArrayList<DestinationNode> answer = new ArrayList<DestinationNode>();
        for (DestinationNode child : this.getChildNodes()) {
            answer.addAll(child.getChildren());
        }
        return answer;
    }

    public Collection removeDesendentValues() {
        ArrayList<DestinationNode> answer = new ArrayList<DestinationNode>();
        for (DestinationNode child : this.getChildNodes()) {
            answer.addAll(child.removeDesendentValues());
        }
        return answer;
    }

    public Collection removeValues() {
        ArrayList<DestinationNode> answer = new ArrayList<DestinationNode>();
        for (DestinationNode child : this.getChildNodes()) {
            answer.addAll(child.removeValues());
        }
        return answer;
    }

    protected Collection getChildNodes() {
        return this.node.getChildren();
    }
}

