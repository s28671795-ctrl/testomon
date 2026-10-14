/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.EventNode;
import org.simpleframework.xml.stream.EventReader;
import org.simpleframework.xml.stream.InputElement;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.InputStack;
import org.simpleframework.xml.stream.NodeException;

public class NodeReader {
    private final StringBuilder text = new StringBuilder();
    private final EventReader reader;
    private final InputStack stack = new InputStack();

    public NodeReader(EventReader reader) {
        this.reader = reader;
    }

    public boolean isRoot(InputNode node) {
        return this.stack.bottom() == node;
    }

    public InputNode readRoot() throws Exception {
        if (this.stack.isEmpty()) {
            InputNode node = this.readElement(null);
            if (node == null) {
                throw new NodeException("Document has no root element");
            }
            return node;
        }
        return null;
    }

    public InputNode readElement(InputNode from) throws Exception {
        if (!this.stack.isRelevant(from)) {
            return null;
        }
        EventNode event = this.reader.next();
        while (event != null) {
            if (event.isEnd()) {
                if (this.stack.pop() == from) {
                    return null;
                }
            } else if (event.isStart()) {
                return this.readStart(from, event);
            }
            event = this.reader.next();
        }
        return null;
    }

    public InputNode readElement(InputNode from, String name) throws Exception {
        if (!this.stack.isRelevant(from)) {
            return null;
        }
        EventNode event = this.reader.peek();
        while (event != null) {
            if (event.isText()) {
                this.fillText(from);
            } else if (event.isEnd()) {
                if (this.stack.top() == from) {
                    return null;
                }
                this.stack.pop();
            } else if (event.isStart()) {
                if (!this.isName(event, name)) break;
                return this.readElement(from);
            }
            event = this.reader.next();
            event = this.reader.peek();
        }
        return null;
    }

    private InputNode readStart(InputNode from, EventNode event) throws Exception {
        InputElement input = new InputElement(from, this, event);
        if (this.text.length() > 0) {
            this.text.setLength(0);
        }
        if (event.isStart()) {
            return this.stack.push(input);
        }
        return input;
    }

    private boolean isName(EventNode node, String name) {
        String local = node.getName();
        if (local == null) {
            return false;
        }
        return local.equals(name);
    }

    public String readValue(InputNode from) throws Exception {
        EventNode event;
        if (!this.stack.isRelevant(from)) {
            return null;
        }
        int length = this.text.length();
        if (length <= 0 && (event = this.reader.peek()).isEnd()) {
            if (this.stack.top() == from) {
                return null;
            }
            this.stack.pop();
            EventNode eventNode = this.reader.next();
        }
        return this.readText(from);
    }

    private String readText(InputNode from) throws Exception {
        EventNode event = this.reader.peek();
        while (this.stack.top() == from && event.isText()) {
            this.fillText(from);
            event = this.reader.next();
            event = this.reader.peek();
        }
        return this.readBuffer(from);
    }

    private String readBuffer(InputNode from) throws Exception {
        int length = this.text.length();
        if (length > 0) {
            String value = this.text.toString();
            this.text.setLength(0);
            return value;
        }
        return null;
    }

    private void fillText(InputNode from) throws Exception {
        EventNode event = this.reader.peek();
        if (event.isText()) {
            String data = event.getValue();
            this.text.append(data);
        }
    }

    public boolean isEmpty(InputNode from) throws Exception {
        EventNode event;
        return this.stack.top() == from && (event = this.reader.peek()).isEnd();
    }

    public void skipElement(InputNode from) throws Exception {
        while (this.readElement(from) != null) {
        }
    }
}

