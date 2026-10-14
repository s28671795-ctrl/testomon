/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import com.sun.xml.fastinfoset.stax.StAXDocumentSerializer;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.interceptor.AttachmentOutInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.StaxOutEndingInterceptor;
import org.apache.cxf.interceptor.StaxOutInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

public class FIStaxOutInterceptor
extends AbstractPhaseInterceptor<Message> {
    public static final String FI_ENABLED = "org.apache.cxf.fastinfoset.enabled";
    private static final Logger LOG = LogUtils.getL7dLogger(FIStaxOutInterceptor.class);
    private static final String OUTPUT_STREAM_HOLDER = FIStaxOutInterceptor.class.getName() + ".outputstream";
    private static final StaxOutEndingInterceptor ENDING = new StaxOutEndingInterceptor(OUTPUT_STREAM_HOLDER);
    boolean force;
    private Integer serializerAttributeValueMapMemoryLimit;
    private Integer serializerMinAttributeValueSize;
    private Integer serializerMaxAttributeValueSize;
    private Integer serializerCharacterContentChunkMapMemoryLimit;
    private Integer serializerMinCharacterContentChunkSize;
    private Integer serializerMaxCharacterContentChunkSize;

    public FIStaxOutInterceptor() {
        super("pre-stream");
        this.addAfter(AttachmentOutInterceptor.class.getName());
        this.addBefore(StaxOutInterceptor.class.getName());
    }

    public FIStaxOutInterceptor(boolean f) {
        this();
        this.force = f;
    }

    @Override
    public void handleFault(Message message) {
        super.handleFault(message);
        OutputStream os = (OutputStream)message.get(OUTPUT_STREAM_HOLDER);
        if (os != null) {
            message.setContent(OutputStream.class, os);
        }
    }

    @Override
    public void handleMessage(Message message) {
        OutputStream out = message.getContent(OutputStream.class);
        XMLStreamWriter writer = message.getContent(XMLStreamWriter.class);
        if (out == null || writer != null) {
            return;
        }
        boolean req = this.isRequestor(message);
        Object o = message.getContextualProperty(FI_ENABLED);
        if (!req) {
            String s2;
            if (message.getExchange().getInMessage() != null && (s2 = (String)message.getExchange().getInMessage().get("Accept")) != null && s2.contains("fastinfoset")) {
                o = Boolean.TRUE;
            }
        } else {
            Map headers = CastUtils.cast((Map)message.get(Message.PROTOCOL_HEADERS));
            ArrayList<String> accepts = (ArrayList<String>)headers.get("Accept");
            if (accepts == null) {
                accepts = new ArrayList<String>();
                headers.put("Accept", accepts);
            }
            String a = "application/fastinfoset";
            if (!accepts.isEmpty()) {
                a = a + ", " + (String)accepts.get(0);
                accepts.set(0, a);
            } else {
                accepts.add(a);
            }
        }
        if (this.force || PropertyUtils.isTrue(o)) {
            StAXDocumentSerializer serializer = this.getOutput(out);
            message.setContent(XMLStreamWriter.class, serializer);
            message.removeContent(OutputStream.class);
            message.put(OUTPUT_STREAM_HOLDER, out);
            message.put("disable.outputstream.optimization", Boolean.TRUE);
            String s3 = (String)message.get("Content-Type");
            if (s3 != null && s3.contains("application/soap+xml")) {
                s3 = s3.replace("application/soap+xml", "application/soap+fastinfoset");
                message.put("Content-Type", s3);
            } else {
                message.put("Content-Type", "application/fastinfoset");
            }
            try {
                serializer.writeStartDocument();
            }
            catch (XMLStreamException e) {
                throw new Fault(e);
            }
            message.getInterceptorChain().add(ENDING);
        }
    }

    private StAXDocumentSerializer getOutput(OutputStream out) {
        StAXDocumentSerializer stAXDocumentSerializer = new StAXDocumentSerializer(out);
        if (this.serializerAttributeValueMapMemoryLimit != null && this.serializerAttributeValueMapMemoryLimit > 0) {
            stAXDocumentSerializer.setAttributeValueMapMemoryLimit(this.serializerAttributeValueMapMemoryLimit);
        }
        if (this.serializerMinAttributeValueSize != null && this.serializerMinAttributeValueSize > 0) {
            stAXDocumentSerializer.setMinAttributeValueSize(this.serializerMinAttributeValueSize);
        }
        if (this.serializerMaxAttributeValueSize != null && this.serializerMaxAttributeValueSize > 0) {
            stAXDocumentSerializer.setMaxAttributeValueSize(this.serializerMaxAttributeValueSize);
        }
        if (this.serializerCharacterContentChunkMapMemoryLimit != null && this.serializerCharacterContentChunkMapMemoryLimit > 0) {
            stAXDocumentSerializer.setCharacterContentChunkMapMemoryLimit(this.serializerCharacterContentChunkMapMemoryLimit);
        }
        if (this.serializerMinCharacterContentChunkSize != null && this.serializerMinCharacterContentChunkSize > 0) {
            stAXDocumentSerializer.setMinCharacterContentChunkSize(this.serializerMinCharacterContentChunkSize);
        }
        if (this.serializerMaxCharacterContentChunkSize != null && this.serializerMaxCharacterContentChunkSize > 0) {
            stAXDocumentSerializer.setMaxCharacterContentChunkSize(this.serializerMaxCharacterContentChunkSize);
        }
        return stAXDocumentSerializer;
    }

    public Integer getSerializerMinAttributeValueSize() {
        return this.serializerMinAttributeValueSize;
    }

    public void setSerializerMinAttributeValueSize(Integer serializerMinAttributeValueSize) {
        this.logSetter("serializerMinAttributeValueSize", serializerMinAttributeValueSize);
        this.serializerMinAttributeValueSize = serializerMinAttributeValueSize;
    }

    public Integer getSerializerMaxAttributeValueSize() {
        return this.serializerMaxAttributeValueSize;
    }

    public void setSerializerMaxAttributeValueSize(Integer serializerMaxAttributeValueSize) {
        this.logSetter("serializerMaxAttributeValueSize", serializerMaxAttributeValueSize);
        this.serializerMaxAttributeValueSize = serializerMaxAttributeValueSize;
    }

    public Integer getSerializerCharacterContentChunkMapMemoryLimit() {
        return this.serializerCharacterContentChunkMapMemoryLimit;
    }

    public void setSerializerCharacterContentChunkMapMemoryLimit(Integer serializerCharacterContentChunkMapMemoryLimit) {
        this.logSetter("serializerCharacterContentChunkMapMemoryLimit", serializerCharacterContentChunkMapMemoryLimit);
        this.serializerCharacterContentChunkMapMemoryLimit = serializerCharacterContentChunkMapMemoryLimit;
    }

    public Integer getSerializerMinCharacterContentChunkSize() {
        return this.serializerMinCharacterContentChunkSize;
    }

    public void setSerializerMinCharacterContentChunkSize(Integer serializerMinCharacterContentChunkSize) {
        this.logSetter("serializerMinCharacterContentChunkSize", serializerMinCharacterContentChunkSize);
        this.serializerMinCharacterContentChunkSize = serializerMinCharacterContentChunkSize;
    }

    public Integer getSerializerMaxCharacterContentChunkSize() {
        return this.serializerMaxCharacterContentChunkSize;
    }

    public void setSerializerMaxCharacterContentChunkSize(Integer serializerMaxCharacterContentChunkSize) {
        this.logSetter("serializerMaxCharacterContentChunkSize", serializerMaxCharacterContentChunkSize);
        this.serializerMaxCharacterContentChunkSize = serializerMaxCharacterContentChunkSize;
    }

    public Integer getSerializerAttributeValueMapMemoryLimit() {
        return this.serializerAttributeValueMapMemoryLimit;
    }

    public void setSerializerAttributeValueMapMemoryLimit(Integer serializerAttributeValueMapMemoryLimit) {
        this.logSetter("serializerAttributeValueMapMemoryLimit", serializerAttributeValueMapMemoryLimit);
        this.serializerAttributeValueMapMemoryLimit = serializerAttributeValueMapMemoryLimit;
    }

    private void logSetter(String propertyName, Object propertyValue) {
        if (LOG.isLoggable(Level.CONFIG)) {
            LOG.config("Setting " + propertyName + " to " + propertyValue);
        }
    }
}

