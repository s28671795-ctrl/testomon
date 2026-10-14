/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.lang.reflect.Array;
import org.simpleframework.xml.core.ArrayFactory;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Converter;
import org.simpleframework.xml.core.ElementException;
import org.simpleframework.xml.core.Instance;
import org.simpleframework.xml.core.Primitive;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;
import org.simpleframework.xml.stream.Position;

class PrimitiveArray
implements Converter {
    private final ArrayFactory factory;
    private final Primitive root;
    private final String parent;
    private final Type entry;
    private final Type type;

    public PrimitiveArray(Context context, Type type, Type entry, String parent) {
        this.factory = new ArrayFactory(context, type);
        this.root = new Primitive(context, entry);
        this.parent = parent;
        this.entry = entry;
        this.type = type;
    }

    @Override
    public Object read(InputNode node) throws Exception {
        Instance type = this.factory.getInstance(node);
        Object list = type.getInstance();
        if (!type.isReference()) {
            return this.read(node, list);
        }
        return list;
    }

    @Override
    public Object read(InputNode node, Object list) throws Exception {
        int length = Array.getLength(list);
        int pos = 0;
        while (true) {
            Position line = node.getPosition();
            InputNode next = node.getNext();
            if (next == null) {
                return list;
            }
            if (pos >= length) {
                throw new ElementException("Array length missing or incorrect for %s at %s", this.type, line);
            }
            Array.set(list, pos, this.root.read(next));
            ++pos;
        }
    }

    @Override
    public boolean validate(InputNode node) throws Exception {
        Instance value = this.factory.getInstance(node);
        if (!value.isReference()) {
            Object result = value.setInstance(null);
            Class expect = value.getType();
            return this.validate(node, expect);
        }
        return true;
    }

    private boolean validate(InputNode node, Class type) throws Exception {
        InputNode next;
        while ((next = node.getNext()) != null) {
            this.root.validate(next);
        }
        return true;
    }

    @Override
    public void write(OutputNode node, Object source2) throws Exception {
        OutputNode child;
        int size = Array.getLength(source2);
        for (int i = 0; i < size && (child = node.getChild(this.parent)) != null; ++i) {
            this.write(child, source2, i);
        }
    }

    private void write(OutputNode node, Object source2, int index) throws Exception {
        Object item = Array.get(source2, index);
        if (item != null && !this.isOverridden(node, item)) {
            this.root.write(node, item);
        }
    }

    private boolean isOverridden(OutputNode node, Object value) throws Exception {
        return this.factory.setOverride(this.entry, value, node);
    }
}

