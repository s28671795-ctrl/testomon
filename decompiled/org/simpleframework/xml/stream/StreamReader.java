/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.util.Iterator;
import javax.xml.stream.Location;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.simpleframework.xml.stream.EventAttribute;
import org.simpleframework.xml.stream.EventElement;
import org.simpleframework.xml.stream.EventNode;
import org.simpleframework.xml.stream.EventReader;
import org.simpleframework.xml.stream.EventToken;

class StreamReader
implements EventReader {
    private XMLEventReader reader;
    private EventNode peek;

    public StreamReader(XMLEventReader reader) {
        this.reader = reader;
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
        XMLEvent event = this.reader.nextEvent();
        if (!event.isEndDocument()) {
            if (event.isStartElement()) {
                return this.start(event);
            }
            if (event.isCharacters()) {
                return this.text(event);
            }
            if (event.isEndElement()) {
                return this.end();
            }
            return this.read();
        }
        return null;
    }

    private Start start(XMLEvent event) {
        Start node = new Start(event);
        if (node.isEmpty()) {
            return this.build(node);
        }
        return node;
    }

    private Start build(Start event) {
        Iterator<Attribute> list = event.getAttributes();
        while (list.hasNext()) {
            Attribute node = list.next();
            Entry entry = this.attribute(node);
            if (entry.isReserved()) continue;
            event.add(entry);
        }
        return event;
    }

    private Entry attribute(Attribute entry) {
        return new Entry(entry);
    }

    private Text text(XMLEvent event) {
        return new Text(event);
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
        private final Characters text;

        public Text(XMLEvent event) {
            this.text = event.asCharacters();
        }

        @Override
        public boolean isText() {
            return true;
        }

        @Override
        public String getValue() {
            return this.text.getData();
        }

        @Override
        public Object getSource() {
            return this.text;
        }
    }

    private static class Start
    extends EventElement {
        private final StartElement element;
        private final Location location;

        public Start(XMLEvent event) {
            this.element = event.asStartElement();
            this.location = event.getLocation();
        }

        @Override
        public int getLine() {
            return this.location.getLineNumber();
        }

        @Override
        public String getName() {
            return this.element.getName().getLocalPart();
        }

        @Override
        public String getPrefix() {
            return this.element.getName().getPrefix();
        }

        @Override
        public String getReference() {
            return this.element.getName().getNamespaceURI();
        }

        public Iterator<Attribute> getAttributes() {
            return this.element.getAttributes();
        }

        @Override
        public Object getSource() {
            return this.element;
        }
    }

    private static class Entry
    extends EventAttribute {
        private final Attribute entry;

        public Entry(Attribute entry) {
            this.entry = entry;
        }

        @Override
        public String getName() {
            return this.entry.getName().getLocalPart();
        }

        @Override
        public String getPrefix() {
            return this.entry.getName().getPrefix();
        }

        @Override
        public String getReference() {
            return this.entry.getName().getNamespaceURI();
        }

        @Override
        public String getValue() {
            return this.entry.getValue();
        }

        @Override
        public boolean isReserved() {
            return false;
        }

        @Override
        public Object getSource() {
            return this.entry;
        }
    }
}

