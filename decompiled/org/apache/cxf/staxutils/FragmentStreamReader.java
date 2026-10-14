/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.staxutils.DepthXMLStreamReader;

public class FragmentStreamReader
extends DepthXMLStreamReader {
    private boolean startElement;
    private boolean middle = true;
    private boolean endDoc;
    private boolean doDocEvents = true;
    private int depth;
    private int current = 7;
    private boolean filter = true;
    private boolean advanceAtEnd = true;

    public FragmentStreamReader(XMLStreamReader reader) {
        super(reader);
    }

    public FragmentStreamReader(XMLStreamReader reader, boolean doDocEvents) {
        super(reader);
        this.doDocEvents = doDocEvents;
        if (!doDocEvents) {
            this.depth = this.getDepth();
            this.current = reader.getEventType();
            if (this.current != 7) {
                this.startElement = true;
            }
        }
    }

    @Override
    public int getEventType() {
        return this.current;
    }

    @Override
    public boolean isCharacters() {
        return this.current == 4;
    }

    @Override
    public boolean isEndElement() {
        return this.current == 2;
    }

    @Override
    public boolean isStartElement() {
        return this.current == 1;
    }

    @Override
    public boolean isWhiteSpace() {
        return this.current == 4 && this.reader.isWhiteSpace();
    }

    @Override
    public boolean hasNext() throws XMLStreamException {
        if (this.endDoc) {
            return false;
        }
        return this.reader.hasNext();
    }

    @Override
    public final int next() throws XMLStreamException {
        if (!this.startElement) {
            this.depth = this.getDepth();
            this.current = this.reader.getEventType();
            if (this.filter) {
                while (this.current != 1 && this.depth >= this.getDepth() && super.hasNext()) {
                    this.current = super.next();
                }
                this.filter = false;
            }
            this.startElement = true;
            this.current = 1;
        } else if (this.middle) {
            this.current = super.next();
            if (this.current == 2 && this.getDepth() < this.depth) {
                this.middle = false;
                if (!this.doDocEvents) {
                    this.endDoc = true;
                }
            }
        } else if (!this.endDoc) {
            if (this.advanceAtEnd) {
                super.next();
            }
            this.endDoc = true;
            this.current = 8;
        } else {
            throw new XMLStreamException("Already at the end of the document.");
        }
        return this.current;
    }

    public boolean isAdvanceAtEnd() {
        return this.advanceAtEnd;
    }

    public void setAdvanceAtEnd(boolean a) {
        this.advanceAtEnd = a;
    }
}

