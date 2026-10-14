/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.strategy;

import java.util.Map;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.strategy.TreeStrategy;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.strategy.Value;
import org.simpleframework.xml.strategy.Visitor;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.OutputNode;

public class VisitorStrategy
implements Strategy {
    private final Strategy strategy;
    private final Visitor visitor;

    public VisitorStrategy(Visitor visitor) {
        this(visitor, new TreeStrategy());
    }

    public VisitorStrategy(Visitor visitor, Strategy strategy) {
        this.strategy = strategy;
        this.visitor = visitor;
    }

    @Override
    public Value read(Type type, NodeMap<InputNode> node, Map map) throws Exception {
        if (this.visitor != null) {
            this.visitor.read(type, node);
        }
        return this.strategy.read(type, node, map);
    }

    @Override
    public boolean write(Type type, Object value, NodeMap<OutputNode> node, Map map) throws Exception {
        boolean result = this.strategy.write(type, value, node, map);
        if (this.visitor != null) {
            this.visitor.write(type, node);
        }
        return result;
    }
}

