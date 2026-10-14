/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding.source;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.databinding.DataReader;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.staxutils.StaxSource;
import org.apache.cxf.staxutils.StaxUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class NodeDataReader
implements DataReader<Node> {
    private static final Logger LOG = LogUtils.getL7dLogger(NodeDataReader.class);

    @Override
    public Object read(MessagePartInfo part, Node input) {
        return this.read(input);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Object read(QName name, Node input, Class<?> type) {
        if (SAXSource.class.isAssignableFrom(type)) {
            XMLStreamReader reader = StaxUtils.createXMLStreamReader((Element)input);
            return new StaxSource(reader);
        }
        if (!StreamSource.class.isAssignableFrom(type)) return this.read(input);
        try (CachedOutputStream out = new CachedOutputStream();){
            StaxUtils.writeTo(input, out);
            InputStream is = out.getInputStream();
            StreamSource streamSource = new StreamSource(is);
            return streamSource;
        }
        catch (IOException e) {
            throw new Fault("COULD_NOT_READ_XML_STREAM", LOG, (Throwable)e);
        }
        catch (XMLStreamException e) {
            throw new Fault("COULD_NOT_READ_XML_STREAM_CAUSED_BY", LOG, (Throwable)e, e.getClass().getCanonicalName(), e.getMessage());
        }
    }

    @Override
    public Object read(Node n) {
        return new DOMSource(n);
    }

    @Override
    public void setSchema(Schema s2) {
    }

    @Override
    public void setAttachments(Collection<Attachment> attachments) {
    }

    @Override
    public void setProperty(String prop, Object value) {
    }
}

