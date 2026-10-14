/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.strategy;

import java.util.Map;
import org.simpleframework.xml.strategy.Contract;
import org.simpleframework.xml.strategy.ReadGraph;
import org.simpleframework.xml.strategy.ReadState;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.strategy.Value;
import org.simpleframework.xml.strategy.WriteGraph;
import org.simpleframework.xml.strategy.WriteState;
import org.simpleframework.xml.stream.NodeMap;

public class CycleStrategy
implements Strategy {
    private final WriteState write;
    private final ReadState read;
    private final Contract contract;

    public CycleStrategy() {
        this("id", "reference");
    }

    public CycleStrategy(String mark, String refer) {
        this(mark, refer, "class");
    }

    public CycleStrategy(String mark, String refer, String label) {
        this(mark, refer, label, "length");
    }

    public CycleStrategy(String mark, String refer, String label, String length) {
        this.contract = new Contract(mark, refer, label, length);
        this.write = new WriteState(this.contract);
        this.read = new ReadState(this.contract);
    }

    public Value read(Type type, NodeMap node, Map map) throws Exception {
        ReadGraph graph = this.read.find(map);
        if (graph != null) {
            return graph.read(type, node);
        }
        return null;
    }

    public boolean write(Type type, Object value, NodeMap node, Map map) {
        WriteGraph graph = this.write.find(map);
        if (graph != null) {
            return graph.write(type, value, node);
        }
        return false;
    }
}

