/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.io.Writer;
import java.util.HashSet;
import java.util.Set;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.stream.Formatter;
import org.simpleframework.xml.stream.Mode;
import org.simpleframework.xml.stream.NamespaceMap;
import org.simpleframework.xml.stream.NodeException;
import org.simpleframework.xml.stream.NodeMap;
import org.simpleframework.xml.stream.OutputDocument;
import org.simpleframework.xml.stream.OutputElement;
import org.simpleframework.xml.stream.OutputNode;
import org.simpleframework.xml.stream.OutputStack;

class NodeWriter {
    private final OutputStack stack;
    private final Formatter writer;
    private final Set active;
    private final boolean verbose;

    public NodeWriter(Writer result) {
        this(result, new Format());
    }

    public NodeWriter(Writer result, Format format) {
        this(result, format, false);
    }

    private NodeWriter(Writer result, Format format, boolean verbose) {
        this.writer = new Formatter(result, format);
        this.active = new HashSet();
        this.stack = new OutputStack(this.active);
        this.verbose = verbose;
    }

    public OutputNode writeRoot() throws Exception {
        OutputDocument root = new OutputDocument(this, this.stack);
        if (this.stack.isEmpty()) {
            this.writer.writeProlog();
        }
        return root;
    }

    public boolean isRoot(OutputNode node) {
        return this.stack.bottom() == node;
    }

    public boolean isCommitted(OutputNode node) {
        return !this.active.contains(node);
    }

    public void commit(OutputNode parent) throws Exception {
        if (this.stack.contains(parent)) {
            OutputNode top = this.stack.top();
            if (!this.isCommitted(top)) {
                this.writeStart(top);
            }
            while (this.stack.top() != parent) {
                this.writeEnd(this.stack.pop());
            }
            this.writeEnd(parent);
            this.stack.pop();
        }
    }

    public void remove(OutputNode node) throws Exception {
        if (this.stack.top() != node) {
            throw new NodeException("Cannot remove node");
        }
        this.stack.pop();
    }

    public OutputNode writeElement(OutputNode parent, String name) throws Exception {
        if (this.stack.isEmpty()) {
            return this.writeStart(parent, name);
        }
        if (this.stack.contains(parent)) {
            OutputNode top = this.stack.top();
            if (!this.isCommitted(top)) {
                this.writeStart(top);
            }
            while (this.stack.top() != parent) {
                this.writeEnd(this.stack.pop());
            }
            if (!this.stack.isEmpty()) {
                this.writeValue(parent);
            }
            return this.writeStart(parent, name);
        }
        return null;
    }

    private OutputNode writeStart(OutputNode parent, String name) throws Exception {
        OutputElement node = new OutputElement(parent, this, name);
        if (name == null) {
            throw new NodeException("Can not have a null name");
        }
        return this.stack.push(node);
    }

    private void writeStart(OutputNode node) throws Exception {
        this.writeComment(node);
        this.writeName(node);
        this.writeAttributes(node);
        this.writeNamespaces(node);
    }

    private void writeComment(OutputNode node) throws Exception {
        String comment = node.getComment();
        if (comment != null) {
            this.writer.writeComment(comment);
        }
    }

    private void writeName(OutputNode node) throws Exception {
        String prefix = node.getPrefix(this.verbose);
        String name = node.getName();
        if (name != null) {
            this.writer.writeStart(name, prefix);
        }
    }

    private void writeValue(OutputNode node) throws Exception {
        Mode mode = node.getMode();
        String value = node.getValue();
        if (value != null) {
            for (OutputNode next : this.stack) {
                if (mode != Mode.INHERIT) break;
                mode = next.getMode();
            }
            this.writer.writeText(value, mode);
        }
        node.setValue(null);
    }

    private void writeEnd(OutputNode node) throws Exception {
        String name = node.getName();
        String prefix = node.getPrefix(this.verbose);
        String value = node.getValue();
        if (value != null) {
            this.writeValue(node);
        }
        if (name != null) {
            this.writer.writeEnd(name, prefix);
            this.writer.flush();
        }
    }

    private void writeAttributes(OutputNode node) throws Exception {
        NodeMap<OutputNode> map = node.getAttributes();
        for (String name : map) {
            OutputNode entry = map.get(name);
            String value = entry.getValue();
            String prefix = entry.getPrefix(this.verbose);
            this.writer.writeAttribute(name, value, prefix);
        }
        this.active.remove(node);
    }

    private void writeNamespaces(OutputNode node) throws Exception {
        NamespaceMap map = node.getNamespaces();
        for (String name : map) {
            String prefix = map.getPrefix(name);
            this.writer.writeNamespace(name, prefix);
        }
    }
}

