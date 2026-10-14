/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.strategy;

import java.util.HashMap;
import org.simpleframework.xml.strategy.Allocate;
import org.simpleframework.xml.strategy.ArrayValue;
import org.simpleframework.xml.strategy.Contract;
import org.simpleframework.xml.strategy.CycleException;
import org.simpleframework.xml.strategy.Loader;
import org.simpleframework.xml.strategy.ObjectValue;
import org.simpleframework.xml.strategy.Reference;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.strategy.Value;
import org.simpleframework.xml.stream.NodeMap;

class ReadGraph
extends HashMap {
    private final Loader loader;
    private final String length;
    private final String label;
    private final String mark;
    private final String refer;

    public ReadGraph(Contract contract, Loader loader) {
        this.refer = contract.getReference();
        this.mark = contract.getIdentity();
        this.length = contract.getLength();
        this.label = contract.getLabel();
        this.loader = loader;
    }

    public Value read(Type type, NodeMap node) throws Exception {
        Object entry = node.remove(this.label);
        Class expect = type.getType();
        if (expect.isArray()) {
            expect = expect.getComponentType();
        }
        if (entry != null) {
            String name = entry.getValue();
            expect = this.loader.load(name);
        }
        return this.readInstance(type, expect, node);
    }

    private Value readInstance(Type type, Class real, NodeMap node) throws Exception {
        Object entry = node.remove(this.mark);
        if (entry == null) {
            return this.readReference(type, real, node);
        }
        String key = entry.getValue();
        if (this.containsKey(key)) {
            throw new CycleException("Element '%s' already exists", key);
        }
        return this.readValue(type, real, node, key);
    }

    private Value readReference(Type type, Class real, NodeMap node) throws Exception {
        Object entry = node.remove(this.refer);
        if (entry == null) {
            return this.readValue(type, real, node);
        }
        String key = entry.getValue();
        Object value = this.get(key);
        if (!this.containsKey(key)) {
            throw new CycleException("Invalid reference '%s' found", key);
        }
        return new Reference(value, real);
    }

    private Value readValue(Type type, Class real, NodeMap node) throws Exception {
        Class expect = type.getType();
        if (expect.isArray()) {
            return this.readArray(type, real, node);
        }
        return new ObjectValue(real);
    }

    private Value readValue(Type type, Class real, NodeMap node, String key) throws Exception {
        Value value = this.readValue(type, real, node);
        if (key != null) {
            return new Allocate(value, this, key);
        }
        return value;
    }

    private Value readArray(Type type, Class real, NodeMap node) throws Exception {
        Object entry = node.remove(this.length);
        int size = 0;
        if (entry != null) {
            String value = entry.getValue();
            size = Integer.parseInt(value);
        }
        return new ArrayValue(real, size);
    }
}

