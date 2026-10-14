/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.Collection;
import org.simpleframework.xml.core.CollectionFactory;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Primitive;
import org.simpleframework.xml.core.Repeater;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.Mode;
import org.simpleframework.xml.stream.OutputNode;

class PrimitiveInlineList
implements Repeater {
    private final CollectionFactory factory;
    private final Primitive root;
    private final String parent;
    private final Type entry;

    public PrimitiveInlineList(Context context, Type type, Type entry, String parent) {
        this.factory = new CollectionFactory(context, type);
        this.root = new Primitive(context, entry);
        this.parent = parent;
        this.entry = entry;
    }

    @Override
    public Object read(InputNode node) throws Exception {
        Object value = this.factory.getInstance();
        Collection list = (Collection)value;
        if (list != null) {
            return this.read(node, list);
        }
        return null;
    }

    @Override
    public Object read(InputNode node, Object value) throws Exception {
        Collection list = (Collection)value;
        if (list != null) {
            return this.read(node, list);
        }
        return this.read(node);
    }

    private Object read(InputNode node, Collection list) throws Exception {
        InputNode from = node.getParent();
        String name = node.getName();
        while (node != null) {
            Object item = this.root.read(node);
            if (item != null) {
                list.add(item);
            }
            node = from.getNext(name);
        }
        return list;
    }

    @Override
    public boolean validate(InputNode node) throws Exception {
        InputNode from = node.getParent();
        String name = node.getName();
        while (node != null) {
            boolean valid = this.root.validate(node);
            if (!valid) {
                return false;
            }
            node = from.getNext(name);
        }
        return true;
    }

    @Override
    public void write(OutputNode node, Object source2) throws Exception {
        OutputNode parent = node.getParent();
        Mode mode = node.getMode();
        if (!node.isCommitted()) {
            node.remove();
        }
        this.write(parent, source2, mode);
    }

    private void write(OutputNode node, Object source2, Mode mode) throws Exception {
        Collection list = (Collection)source2;
        for (Object item : list) {
            OutputNode child;
            if (item == null || this.isOverridden(child = node.getChild(this.parent), item)) continue;
            child.setMode(mode);
            this.root.write(child, item);
        }
    }

    private boolean isOverridden(OutputNode node, Object value) throws Exception {
        return this.factory.setOverride(this.entry, value, node);
    }
}

