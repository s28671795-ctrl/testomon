/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.Mode;
import org.simpleframework.xml.stream.NamespaceMap;
import org.simpleframework.xml.stream.NodeWriter;
import org.simpleframework.xml.stream.OutputNode;
import org.simpleframework.xml.stream.OutputNodeMap;
import org.simpleframework.xml.stream.PrefixResolver;

class OutputElement
implements OutputNode {
    private OutputNodeMap table;
    private NamespaceMap scope;
    private NodeWriter writer;
    private OutputNode parent;
    private String reference;
    private String comment;
    private String value;
    private String name;
    private Mode mode;

    public OutputElement(OutputNode parent, NodeWriter writer, String name) {
        this.scope = new PrefixResolver(parent);
        this.table = new OutputNodeMap(this);
        this.mode = Mode.INHERIT;
        this.writer = writer;
        this.parent = parent;
        this.name = name;
    }

    @Override
    public String getPrefix() {
        return this.getPrefix(true);
    }

    @Override
    public String getPrefix(boolean inherit) {
        String prefix = this.scope.getPrefix(this.reference);
        if (inherit && prefix == null) {
            return this.parent.getPrefix();
        }
        return prefix;
    }

    @Override
    public String getReference() {
        return this.reference;
    }

    @Override
    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public NamespaceMap getNamespaces() {
        return this.scope;
    }

    @Override
    public OutputNode getParent() {
        return this.parent;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getComment() {
        return this.comment;
    }

    @Override
    public boolean isRoot() {
        return this.writer.isRoot(this);
    }

    @Override
    public Mode getMode() {
        return this.mode;
    }

    @Override
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public OutputNodeMap getAttributes() {
        return this.table;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setData(boolean data) {
        this.mode = data ? Mode.DATA : Mode.ESCAPE;
    }

    @Override
    public OutputNode setAttribute(String name, String value) {
        return this.table.put(name, value);
    }

    @Override
    public OutputNode getChild(String name) throws Exception {
        return this.writer.writeElement(this, name);
    }

    @Override
    public void remove() throws Exception {
        this.writer.remove(this);
    }

    @Override
    public void commit() throws Exception {
        this.writer.commit(this);
    }

    @Override
    public boolean isCommitted() {
        return this.writer.isCommitted(this);
    }

    public String toString() {
        return String.format("element %s", this.name);
    }
}

