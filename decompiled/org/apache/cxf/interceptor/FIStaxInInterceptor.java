/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
import java.io.InputStream;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.StaxInEndingInterceptor;
import org.apache.cxf.interceptor.StaxInInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;

public class FIStaxInInterceptor
extends AbstractPhaseInterceptor<Message> {
    public static final String FI_GET_SUPPORTED = "org.apache.cxf.fastinfoset.get.supported";

    public FIStaxInInterceptor() {
        this("post-stream");
    }

    public FIStaxInInterceptor(String phase) {
        super(phase);
        this.addBefore(StaxInInterceptor.class.getName());
    }

    @Override
    protected boolean isRequestor(Message message) {
        return Boolean.TRUE.equals(message.containsKey("org.apache.cxf.client"));
    }

    private StAXDocumentParser getParser(InputStream in) {
        StAXDocumentParser parser = new StAXDocumentParser(in);
        parser.setStringInterning(true);
        parser.setForceStreamClose(true);
        parser.setInputStream(in);
        return parser;
    }

    @Override
    public void handleMessage(Message message) {
        if (message.getContent(XMLStreamReader.class) != null || !this.isHttpVerbSupported(message)) {
            return;
        }
        String ct = (String)message.get("Content-Type");
        if (ct != null && ct.indexOf("fastinfoset") != -1 && message.getContent(InputStream.class) != null && message.getContent(XMLStreamReader.class) == null) {
            message.setContent(XMLStreamReader.class, this.getParser(message.getContent(InputStream.class)));
            message.getInterceptorChain().add(StaxInEndingInterceptor.INSTANCE);
            ct = ct.replace("fastinfoset", "xml");
            if (ct.contains("application/xml")) {
                ct = ct.replace("application/xml", "text/xml");
            }
            message.put("Content-Type", ct);
            message.getExchange().put("org.apache.cxf.fastinfoset.enabled", Boolean.TRUE);
            if (this.isRequestor(message)) {
                Endpoint ep = message.getExchange().getEndpoint();
                ep.put("org.apache.cxf.fastinfoset.enabled", Boolean.TRUE);
            }
        }
    }

    protected boolean isHttpVerbSupported(Message message) {
        if (this.isGET(message)) {
            return this.isRequestor(message) && MessageUtils.getContextualBoolean(message, FI_GET_SUPPORTED, false);
        }
        return true;
    }
}

