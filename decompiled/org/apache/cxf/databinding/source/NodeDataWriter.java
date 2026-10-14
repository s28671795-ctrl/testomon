/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding.source;

import java.util.Collection;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.databinding.DataWriter;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class NodeDataWriter
implements DataWriter<Node> {
    private static final Logger LOG = LogUtils.getL7dLogger(NodeDataWriter.class);

    @Override
    public void write(Object obj, MessagePartInfo part, Node output) {
        this.write(obj, output);
    }

    @Override
    public void write(Object obj, Node n) {
        try {
            Source s2 = (Source)obj;
            if (s2 instanceof DOMSource && ((DOMSource)s2).getNode() == null) {
                return;
            }
            W3CDOMStreamWriter writer = new W3CDOMStreamWriter((Element)n);
            StaxUtils.copy(s2, (XMLStreamWriter)writer);
        }
        catch (XMLStreamException e) {
            throw new Fault("COULD_NOT_WRITE_XML_STREAM_CAUSED_BY", LOG, (Throwable)e, e.getClass().getCanonicalName(), e.getMessage());
        }
    }

    @Override
    public void setSchema(Schema s2) {
    }

    @Override
    public void setAttachments(Collection<Attachment> attachments) {
    }

    @Override
    public void setProperty(String key, Object value) {
    }
}

