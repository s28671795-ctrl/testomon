/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import java.util.ArrayDeque;
import java.util.Deque;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.staxutils.DepthExceededStaxException;
import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.apache.cxf.staxutils.DocumentDepthProperties;

public class DepthRestrictingStreamReader
extends DepthXMLStreamReader {
    private DocumentDepthProperties props;
    private int totalElementCount;
    private final Deque<Integer> stack = new ArrayDeque<Integer>();

    public DepthRestrictingStreamReader(XMLStreamReader reader, int elementCountThreshold, int innerElementLevelThreshold, int innerElementCountThreshold) {
        this(reader, new DocumentDepthProperties(elementCountThreshold, innerElementLevelThreshold, innerElementCountThreshold));
    }

    public DepthRestrictingStreamReader(XMLStreamReader reader, DocumentDepthProperties props) {
        super(reader);
        this.props = props;
    }

    @Override
    public int next() throws XMLStreamException {
        int next = super.next();
        if (next == 1) {
            if (this.props.getInnerElementLevelThreshold() != -1 && this.getDepth() >= this.props.getInnerElementLevelThreshold()) {
                throw new DepthExceededStaxException();
            }
            if (this.props.getElementCountThreshold() != -1 && ++this.totalElementCount >= this.props.getElementCountThreshold()) {
                throw new DepthExceededStaxException();
            }
            if (this.props.getInnerElementCountThreshold() != -1) {
                if (!this.stack.isEmpty()) {
                    int currentCount = this.stack.pop();
                    if (++currentCount >= this.props.getInnerElementCountThreshold()) {
                        throw new DepthExceededStaxException();
                    }
                    this.stack.push(currentCount);
                }
                this.stack.push(0);
            }
        } else if (next == 2 && this.props.getInnerElementCountThreshold() != -1) {
            this.stack.pop();
        }
        return next;
    }
}

