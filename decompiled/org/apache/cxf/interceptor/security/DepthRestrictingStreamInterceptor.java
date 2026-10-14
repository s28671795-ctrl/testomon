/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.security;

import java.io.InputStream;
import java.util.List;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.interceptor.StaxInInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.staxutils.DepthRestrictingStreamReader;
import org.apache.cxf.staxutils.StaxUtils;

public class DepthRestrictingStreamInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final String XML_SUB_TYPE = "xml";
    private int elementCountThreshold = 2000;
    private int innerElementLevelThreshold = 20;
    private int innerElementCountThreshold = 50;

    public DepthRestrictingStreamInterceptor() {
        this("post-stream");
        this.addAfter(StaxInInterceptor.class.getName());
    }

    public DepthRestrictingStreamInterceptor(String phase) {
        super(phase);
    }

    public DepthRestrictingStreamInterceptor(String phase, List<String> after) {
        super(phase);
        if (after != null) {
            this.addAfter(after);
        }
    }

    public DepthRestrictingStreamInterceptor(String phase, List<String> before, List<String> after) {
        this(phase, after);
        if (before != null) {
            this.addBefore(before);
        }
    }

    @Override
    public void handleMessage(Message message) {
        if (this.canBeIgnored(message)) {
            return;
        }
        XMLStreamReader reader = message.getContent(XMLStreamReader.class);
        if (reader == null) {
            InputStream is = message.getContent(InputStream.class);
            if (is != null) {
                reader = StaxUtils.createXMLStreamReader(is);
                message.setContent(InputStream.class, null);
            }
            if (reader == null) {
                return;
            }
        }
        DepthRestrictingStreamReader dr = new DepthRestrictingStreamReader(reader, this.elementCountThreshold, this.innerElementLevelThreshold, this.innerElementCountThreshold);
        message.setContent(XMLStreamReader.class, dr);
    }

    protected boolean canBeIgnored(Message message) {
        String ct = (String)message.get("Content-Type");
        return ct != null && !ct.contains(XML_SUB_TYPE);
    }

    public void setElementCountThreshold(int elementCountThreshold) {
        this.elementCountThreshold = elementCountThreshold;
    }

    public int getElementCountThreshold() {
        return this.elementCountThreshold;
    }

    public void setInnerElementLevelThreshold(int elementLevelThreshold) {
        this.innerElementLevelThreshold = elementLevelThreshold;
    }

    public int getInnerElementLevelThreshold() {
        return this.innerElementLevelThreshold;
    }

    public void setInnerElementCountThreshold(int innerElementCountThreshold) {
        this.innerElementCountThreshold = innerElementCountThreshold;
    }

    public int getInnerElementCountThreshold() {
        return this.innerElementCountThreshold;
    }
}

