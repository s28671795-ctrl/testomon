/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.strategy;

import java.lang.reflect.Array;
import java.util.Map;
import org.simpleframework.xml.strategy.ArrayValue;
import org.simpleframework.xml.strategy.Loader;
import org.simpleframework.xml.strategy.ObjectValue;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.strategy.Value;
import org.simpleframework.xml.stream.NodeMap;

public class TreeStrategy
implements Strategy {
    private final Loader loader = new Loader();
    private final String length;
    private final String label;

    public TreeStrategy() {
        this("class", "length");
    }

    public TreeStrategy(String label, String length) {
        this.length = length;
        this.label = label;
    }

    public Value read(Type type, NodeMap node, Map map) throws Exception {
        Class actual = this.readValue(type, node);
        Class expect = type.getType();
        if (expect.isArray()) {
            return this.readArray(actual, node);
        }
        if (expect != actual) {
            return new ObjectValue(actual);
        }
        return null;
    }

    private Value readArray(Class type, NodeMap node) throws Exception {
        Object entry = node.remove(this.length);
        int size = 0;
        if (entry != null) {
            String value = entry.getValue();
            size = Integer.parseInt(value);
        }
        return new ArrayValue(type, size);
    }

    private Class readValue(Type type, NodeMap node) throws Exception {
        Object entry = node.remove(this.label);
        Class expect = type.getType();
        if (expect.isArray()) {
            expect = expect.getComponentType();
        }
        if (entry != null) {
            String name = entry.getValue();
            expect = this.loader.load(name);
        }
        return expect;
    }

    public boolean write(Type type, Object value, NodeMap node, Map map) {
        Class actual = value.getClass();
        Class expect = type.getType();
        Class real = actual;
        if (actual.isArray()) {
            real = this.writeArray(expect, value, node);
        }
        if (actual != expect) {
            node.put(this.label, real.getName());
        }
        return false;
    }

    private Class writeArray(Class field2, Object value, NodeMap node) {
        int size = Array.getLength(value);
        if (this.length != null) {
            node.put(this.length, String.valueOf(size));
        }
        return field2.getComponentType();
    }
}

