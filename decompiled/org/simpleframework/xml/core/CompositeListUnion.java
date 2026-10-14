/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.simpleframework.xml.core.Context;
import org.simpleframework.xml.core.Converter;
import org.simpleframework.xml.core.Expression;
import org.simpleframework.xml.core.Group;
import org.simpleframework.xml.core.Label;
import org.simpleframework.xml.core.LabelMap;
import org.simpleframework.xml.core.Repeater;
import org.simpleframework.xml.core.UnionException;
import org.simpleframework.xml.strategy.Type;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;
import org.simpleframework.xml.stream.Style;

class CompositeListUnion
implements Repeater {
    private final LabelMap elements;
    private final Expression path;
    private final Context context;
    private final Group group;
    private final Style style;
    private final Type type;

    public CompositeListUnion(Context context, Group group, Expression path, Type type) throws Exception {
        this.elements = group.getElements();
        this.style = context.getStyle();
        this.context = context;
        this.group = group;
        this.type = type;
        this.path = path;
    }

    @Override
    public Object read(InputNode node) throws Exception {
        Label text = this.group.getText();
        if (text == null) {
            return this.readElement(node);
        }
        return this.readText(node);
    }

    private Object readElement(InputNode node) throws Exception {
        String name = node.getName();
        String element = this.path.getElement(name);
        Label label = (Label)this.elements.get(element);
        Converter converter = label.getConverter(this.context);
        return converter.read(node);
    }

    private Object readText(InputNode node) throws Exception {
        Label text = this.group.getText();
        Converter converter = text.getConverter(this.context);
        return converter.read(node);
    }

    @Override
    public Object read(InputNode node, Object value) throws Exception {
        Object result = this.readElement(node, value);
        Label text = this.group.getText();
        if (text != null) {
            return this.readText(node, value);
        }
        return result;
    }

    private Object readElement(InputNode node, Object value) throws Exception {
        String name = node.getName();
        String element = this.path.getElement(name);
        Label label = (Label)this.elements.get(element);
        Converter converter = label.getConverter(this.context);
        return converter.read(node, value);
    }

    private Object readText(InputNode node, Object value) throws Exception {
        Label label = this.group.getText();
        Converter converter = label.getConverter(this.context);
        InputNode parent = node.getParent();
        return converter.read(parent, value);
    }

    @Override
    public boolean validate(InputNode node) throws Exception {
        String name = node.getName();
        String element = this.path.getElement(name);
        Label label = (Label)this.elements.get(element);
        Converter converter = label.getConverter(this.context);
        return converter.validate(node);
    }

    @Override
    public void write(OutputNode node, Object source2) throws Exception {
        Collection list = (Collection)source2;
        if (this.group.isInline()) {
            if (!list.isEmpty()) {
                this.write(node, list);
            } else if (!node.isCommitted()) {
                node.remove();
            }
        } else {
            this.write(node, list);
        }
    }

    private void write(OutputNode node, Collection list) throws Exception {
        for (Object item : list) {
            if (item == null) continue;
            Class<?> real = item.getClass();
            Label label = this.group.getLabel(real);
            if (label == null) {
                throw new UnionException("Entry of %s not declared in %s with annotation %s", real, this.type, this.group);
            }
            this.write(node, item, label);
        }
    }

    private void write(OutputNode node, Object item, Label label) throws Exception {
        Converter converter = label.getConverter(this.context);
        Set<Object> list = Collections.singleton(item);
        if (!label.isInline()) {
            String name = label.getName();
            String root = this.style.getElement(name);
            if (!node.isCommitted()) {
                node.setName(root);
            }
        }
        converter.write(node, list);
    }
}

