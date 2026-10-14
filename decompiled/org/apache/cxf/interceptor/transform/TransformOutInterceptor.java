/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor.transform;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.interceptor.StaxOutEndingInterceptor;
import org.apache.cxf.interceptor.StaxOutInterceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.PhaseInterceptorChain;
import org.apache.cxf.staxutils.transform.TransformUtils;

public class TransformOutInterceptor
extends AbstractPhaseInterceptor<Message> {
    private static final String OUTPUT_STREAM_HOLDER = TransformOutInterceptor.class.getName() + ".outputstream";
    private static final String TRANSFORM_SKIP = "transform.skip";
    private static final StaxOutEndingInterceptor ENDING = new StaxOutEndingInterceptor(OUTPUT_STREAM_HOLDER);
    private Map<String, String> outElementsMap;
    private Map<String, String> outAppendMap;
    private List<String> outDropElements;
    private Map<String, String> outAttributesMap;
    private boolean attributesToElements;
    private boolean skipOnFault;
    private String contextPropertyName;
    private String defaultNamespace;

    public TransformOutInterceptor() {
        this("pre-stream");
    }

    public TransformOutInterceptor(String phase) {
        super(phase);
        this.addBefore(StaxOutInterceptor.class.getName());
        this.addAfter("org.apache.cxf.interceptor.LoggingOutInterceptor");
        this.addAfter("org.apache.cxf.ext.logging.LoggingOutInterceptor");
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
        OutputStream out;
        if (!this.isHttpVerbSupported(message)) {
            return;
        }
        if (this.contextPropertyName != null && !MessageUtils.getContextualBoolean(message.getExchange().getInMessage(), this.contextPropertyName, false)) {
            return;
        }
        if (this.skipOnFault && null != message.getContent(Exception.class) || MessageUtils.getContextualBoolean(message, TRANSFORM_SKIP, false)) {
            return;
        }
        XMLStreamWriter writer = message.getContent(XMLStreamWriter.class);
        XMLStreamWriter transformWriter = this.createTransformWriterIfNeeded(writer, out = message.getContent(OutputStream.class));
        if (transformWriter != null) {
            message.setContent(XMLStreamWriter.class, transformWriter);
            if (message.getContextualProperty("disable.outputstream.optimization") == null) {
                message.put("disable.outputstream.optimization", Boolean.TRUE);
            }
            if (MessageUtils.isRequestor(message)) {
                message.removeContent(OutputStream.class);
                message.put(OUTPUT_STREAM_HOLDER, out);
                message.getInterceptorChain().add(ENDING);
            }
        }
    }

    private String getEncoding(Message message) {
        Exchange ex = message.getExchange();
        String encoding = (String)message.get(Message.ENCODING);
        if (encoding == null && ex.getInMessage() != null) {
            encoding = (String)ex.getInMessage().get(Message.ENCODING);
            message.put(Message.ENCODING, encoding);
        }
        if (encoding == null) {
            encoding = StandardCharsets.UTF_8.name();
            message.put(Message.ENCODING, encoding);
        }
        return encoding;
    }

    protected XMLStreamWriter createTransformWriterIfNeeded(XMLStreamWriter writer, OutputStream os) {
        Message m4 = PhaseInterceptorChain.getCurrentMessage();
        String encoding = this.getEncoding(m4);
        return TransformUtils.createTransformWriterIfNeeded(writer, os, this.outElementsMap, this.outDropElements, this.outAppendMap, this.outAttributesMap, this.attributesToElements, this.defaultNamespace, encoding);
    }

    public void setOutTransformElements(Map<String, String> outElements) {
        this.outElementsMap = outElements;
    }

    public void setOutAppendElements(Map<String, String> map) {
        this.outAppendMap = map;
    }

    public void setOutDropElements(List<String> dropElementsSet) {
        this.outDropElements = dropElementsSet;
    }

    public void setOutTransformAttributes(Map<String, String> outAttributes) {
        this.outAttributesMap = outAttributes;
    }

    public void setAttributesToElements(boolean value) {
        this.attributesToElements = value;
    }

    public void setSkipOnFault(boolean value) {
        this.skipOnFault = value;
    }

    protected boolean isHttpVerbSupported(Message message) {
        return !this.isRequestor(message) || !this.isGET(message);
    }

    public void setContextPropertyName(String propertyName) {
        this.contextPropertyName = propertyName;
    }

    public void setDefaultNamespace(String defaultNamespace) {
        this.defaultNamespace = defaultNamespace;
    }
}

