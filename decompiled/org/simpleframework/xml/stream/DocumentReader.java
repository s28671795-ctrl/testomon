/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import org.simpleframework.xml.stream.EventAttribute;
import org.simpleframework.xml.stream.EventElement;
import org.simpleframework.xml.stream.EventNode;
import org.simpleframework.xml.stream.EventReader;
import org.simpleframework.xml.stream.EventToken;
import org.simpleframework.xml.stream.NodeExtractor;
import org.simpleframework.xml.stream.NodeStack;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

class DocumentReader
implements EventReader {
    private static final String RESERVED = "xml";
    private NodeExtractor queue;
    private NodeStack stack;
    private EventNode peek;

    public DocumentReader(Document document) {
        this.queue = new NodeExtractor(document);
        this.stack = new NodeStack();
        this.stack.push(document);
    }

    @Override
    public EventNode peek() throws Exception {
        if (this.peek == null) {
            this.peek = this.next();
        }
        return this.peek;
    }

    @Override
    public EventNode next() throws Exception {
        EventNode next = this.peek;
        if (next == null) {
            next = this.read();
        } else {
            this.peek = null;
        }
        return next;
    }

    private EventNode read() throws Exception {
        Node node = (Node)this.queue.peek();
        if (node == null) {
            return this.end();
        }
        return this.read(node);
    }

    private EventNode read(Node node) throws Exception {
        Node top;
        Node parent = node.getParentNode();
        if (parent != (top = (Node)this.stack.top())) {
            if (top != null) {
                this.stack.pop();
            }
            return this.end();
        }
        if (node != null) {
            this.queue.poll();
        }
        return this.convert(node);
    }

    private EventNode convert(Node node) throws Exception {
        short type = node.getNodeType();
        if (type == 1) {
            if (node != null) {
                this.stack.push(node);
            }
            return this.start(node);
        }
        return this.text(node);
    }

    private Start start(Node node) {
        Start event = new Start(node);
        if (event.isEmpty()) {
            return this.build(event);
        }
        return event;
    }

    private Start build(Start event) {
        NamedNodeMap list = event.getAttributes();
        int length = list.getLength();
        for (int i = 0; i < length; ++i) {
            Node node = list.item(i);
            Entry value = this.attribute(node);
            if (value.isReserved()) continue;
            event.add(value);
        }
        return event;
    }

    private Entry attribute(Node node) {
        return new Entry(node);
    }

    private Text text(Node node) {
        return new Text(node);
    }

    private End end() {
        return new End();
    }

    private static class End
    extends EventToken {
        private End() {
        }

        @Override
        public boolean isEnd() {
            return true;
        }
    }

    private static class Text
    extends EventToken {
        private final Node node;

        public Text(Node node) {
            this.node = node;
        }

        @Override
        public boolean isText() {
            return true;
        }

        @Override
        public String getValue() {
            return this.node.getNodeValue();
        }

        @Override
        public Object getSource() {
            return this.node;
        }
    }

    private static class Start
    extends EventElement {
        private final Element element;

        public Start(Node element) {
            this.element = (Element)element;
        }

        @Override
        public String getName() {
            return this.element.getLocalName();
        }

        @Override
        public String getPrefix() {
            return this.element.getPrefix();
        }

        @Override
        public String getReference() {
            return this.element.getNamespaceURI();
        }

        public NamedNodeMap getAttributes() {
            return this.element.getAttributes();
        }

        @Override
        public Object getSource() {
            return this.element;
        }
    }

    private static class Entry
    extends EventAttribute {
        private final Node node;

        public Entry(Node node) {
            this.node = node;
        }

        @Override
        public String getName() {
            return this.node.getLocalName();
        }

        @Override
        public String getValue() {
            return this.node.getNodeValue();
        }

        @Override
        public String getPrefix() {
            return this.node.getPrefix();
        }

        @Override
        public String getReference() {
            return this.node.getNamespaceURI();
        }

        @Override
        public boolean isReserved() {
            String prefix = this.getPrefix();
            String name = this.getName();
            if (prefix != null) {
                return prefix.startsWith(DocumentReader.RESERVED);
            }
            return name.startsWith(DocumentReader.RESERVED);
        }

        @Override
        public Object getSource() {
            return this.node;
        }
    }
}

