/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.staxutils.DepthXMLStreamReader;

public class PartialXMLStreamReader
extends DepthXMLStreamReader {
    private QName endTag;
    private boolean foundEnd;
    private int endDepth;
    private int currentEvent;

    public PartialXMLStreamReader(XMLStreamReader r, QName endTag) {
        super(r);
        this.endTag = endTag;
        this.currentEvent = r.getEventType();
    }

    @Override
    public int next() throws XMLStreamException {
        if (!this.foundEnd) {
            this.currentEvent = super.next();
            if (this.currentEvent == 1 && this.getName().equals(this.endTag)) {
                this.foundEnd = true;
                this.endDepth = this.getDepth();
                return 1;
            }
            return this.currentEvent;
        }
        if (this.endDepth > 0) {
            --this.endDepth;
            this.currentEvent = 2;
        } else {
            this.currentEvent = 8;
        }
        return this.currentEvent;
    }

    @Override
    public int getEventType() {
        return this.currentEvent;
    }

    @Override
    public boolean hasNext() {
        return this.currentEvent != 8;
    }
}

