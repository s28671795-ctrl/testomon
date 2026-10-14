/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.HashMap;
import java.util.Map;
import org.jooq.Node;
import org.jooq.exception.DataDefinitionException;
import org.jooq.tools.StringUtils;

abstract class AbstractNode<N extends Node<N>>
implements Node<N> {
    private final String id;
    private final String message;

    AbstractNode(String id, String message) {
        this.id = id;
        this.message = StringUtils.defaultIfNull(message, "");
    }

    @Override
    public final String id() {
        return this.id;
    }

    @Override
    public final String message() {
        return this.message;
    }

    @Override
    public final N root() {
        Node<N> node = this;
        while (!node.parents().isEmpty()) {
            node = (Node)node.parents().get(0);
        }
        return (N)node;
    }

    final N commonAncestor(N other) {
        if (this.id().equals(other.id())) {
            return (N)this;
        }
        Map<AbstractNode, Integer> a1 = this.ancestors(this, new HashMap(), 1);
        Map<N, Integer> a2 = this.ancestors(other, new HashMap(), 1);
        Node node = null;
        Integer distance = null;
        for (Map.Entry<AbstractNode, Integer> entry : a1.entrySet()) {
            if (!a2.containsKey(entry.getKey()) || distance != null && distance <= entry.getValue()) continue;
            node = entry.getKey();
            distance = entry.getValue();
        }
        if (node == null) {
            throw new DataDefinitionException("Versions " + this.id() + " and " + other.id() + " do not have a common ancestor");
        }
        return (N)node;
    }

    private Map<N, Integer> ancestors(N node, Map<N, Integer> result, int distance) {
        Integer previous = result.get(node);
        if (previous == null || previous > distance) {
            result.put(node, distance);
            for (Node parent : node.parents()) {
                this.ancestors(parent, result, distance + 1);
            }
        }
        return result;
    }
}

