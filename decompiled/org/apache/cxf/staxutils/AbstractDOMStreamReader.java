/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import java.util.Collections;
import java.util.List;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.staxutils.FastStack;

public abstract class AbstractDOMStreamReader<T, I>
implements XMLStreamReader {
    protected int currentEvent = 7;
    private FastStack<ElementFrame<T, I>> frames = new FastStack();
    private ElementFrame<T, I> frame;

    public AbstractDOMStreamReader(ElementFrame<T, I> frame) {
        this.frame = frame;
        this.frames.push(this.frame);
    }

    protected ElementFrame<T, I> getCurrentFrame() {
        return this.frame;
    }

    @Override
    public Object getProperty(String name) {
        return null;
    }

    @Override
    public int next() throws XMLStreamException {
        if (this.frame.ended) {
            this.frames.pop();
            if (!this.frames.empty()) {
                this.frame = this.frames.peek();
            } else {
                this.currentEvent = 8;
                return this.currentEvent;
            }
        }
        if (!this.frame.started) {
            this.frame.started = true;
            this.currentEvent = this.frame.isDocument() ? 7 : 1;
        } else if (this.hasMoreChildren()) {
            this.currentEvent = this.nextChild();
            if (this.currentEvent == 1) {
                ElementFrame<T, I> newFrame = this.getChildFrame();
                newFrame.started = true;
                this.frame = newFrame;
                this.frames.push(this.frame);
                this.currentEvent = 1;
                this.newFrame(newFrame);
            }
        } else {
            this.frame.ended = true;
            if (this.frame.isDocument()) {
                this.currentEvent = 8;
            } else {
                this.currentEvent = 2;
                this.endElement();
            }
        }
        return this.currentEvent;
    }

    protected void newFrame(ElementFrame<T, I> newFrame) {
    }

    protected void endElement() {
    }

    protected abstract boolean hasMoreChildren();

    protected abstract int nextChild();

    protected abstract ElementFrame<T, I> getChildFrame();

    @Override
    public void require(int arg0, String arg1, String arg2) throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public abstract String getElementText() throws XMLStreamException;

    public void consumeFrame() {
        this.frame.started = true;
        this.frame.ended = true;
        if (this.frame.isDocument()) {
            this.currentEvent = 8;
        } else {
            this.currentEvent = 2;
            this.endElement();
        }
    }

    @Override
    public int nextTag() throws XMLStreamException {
        while (this.hasNext()) {
            if (1 != this.next()) continue;
            return 1;
        }
        return this.currentEvent;
    }

    @Override
    public boolean hasNext() throws XMLStreamException {
        return !this.frame.ended || !this.frames.isEmpty() && !this.frame.isDocumentFragment();
    }

    @Override
    public void close() throws XMLStreamException {
    }

    @Override
    public abstract String getNamespaceURI(String var1);

    @Override
    public boolean isStartElement() {
        return this.currentEvent == 1;
    }

    @Override
    public boolean isEndElement() {
        return this.currentEvent == 2;
    }

    @Override
    public boolean isCharacters() {
        return this.currentEvent == 4;
    }

    @Override
    public boolean isWhiteSpace() {
        if (this.currentEvent == 4 || this.currentEvent == 12) {
            String text = this.getText();
            int len = text.length();
            for (int i = 0; i < len; ++i) {
                if (text.charAt(i) <= ' ') continue;
                return false;
            }
            return true;
        }
        return this.currentEvent == 6;
    }

    @Override
    public int getEventType() {
        return this.currentEvent;
    }

    @Override
    public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
        char[] src = this.getText().toCharArray();
        if (sourceStart + length >= src.length) {
            length = src.length - sourceStart;
        }
        for (int i = 0; i < length; ++i) {
            target[targetStart + i] = src[i + sourceStart];
        }
        return length;
    }

    @Override
    public boolean hasText() {
        return this.currentEvent == 4 || this.currentEvent == 11 || this.currentEvent == 9 || this.currentEvent == 5 || this.currentEvent == 6;
    }

    public String getSystemId() {
        return null;
    }

    public String getPublicId() {
        return null;
    }

    @Override
    public Location getLocation() {
        return new Location(){

            @Override
            public int getCharacterOffset() {
                return 0;
            }

            @Override
            public int getColumnNumber() {
                return 0;
            }

            @Override
            public int getLineNumber() {
                return 0;
            }

            @Override
            public String getPublicId() {
                return AbstractDOMStreamReader.this.getPublicId();
            }

            @Override
            public String getSystemId() {
                return AbstractDOMStreamReader.this.getSystemId();
            }
        };
    }

    @Override
    public boolean hasName() {
        return this.currentEvent == 1 || this.currentEvent == 2;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public boolean isStandalone() {
        return false;
    }

    @Override
    public boolean standaloneSet() {
        return false;
    }

    @Override
    public String getCharacterEncodingScheme() {
        return null;
    }

    public static class ElementFrame<T, I> {
        T element;
        I currentChild;
        boolean started;
        boolean ended;
        List<String> uris;
        List<String> prefixes;
        List<Object> attributes;
        final ElementFrame<T, I> parent;

        public ElementFrame(T element, ElementFrame<T, I> parent) {
            this.element = element;
            this.parent = parent;
        }

        public ElementFrame(T element, ElementFrame<T, I> parent, I ch) {
            this.element = element;
            this.parent = parent;
            this.currentChild = ch;
        }

        public ElementFrame(T doc, boolean s2) {
            this.element = doc;
            this.parent = null;
            this.started = s2;
            this.attributes = Collections.emptyList();
            this.prefixes = Collections.emptyList();
            this.uris = Collections.emptyList();
        }

        public ElementFrame(T doc) {
            this(doc, true);
        }

        public T getElement() {
            return this.element;
        }

        public I getCurrentChild() {
            return this.currentChild;
        }

        public void setCurrentChild(I o) {
            this.currentChild = o;
        }

        public boolean isDocument() {
            return false;
        }

        public boolean isDocumentFragment() {
            return false;
        }
    }
}

