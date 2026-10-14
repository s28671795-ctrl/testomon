/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.frontend;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.frontend.WSDLGetInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.StaxOutInterceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.staxutils.StaxUtils;
import org.w3c.dom.Document;

public class WSDLGetOutInterceptor
extends AbstractPhaseInterceptor<Message> {
    public static final WSDLGetOutInterceptor INSTANCE = new WSDLGetOutInterceptor();

    public WSDLGetOutInterceptor() {
        super("pre-stream");
        this.getAfter().add(StaxOutInterceptor.class.getName());
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        Document doc = (Document)message.get(WSDLGetInterceptor.DOCUMENT_HOLDER);
        if (doc == null) {
            return;
        }
        message.remove(WSDLGetInterceptor.DOCUMENT_HOLDER);
        XMLStreamWriter writer = message.getContent(XMLStreamWriter.class);
        if (writer == null) {
            return;
        }
        message.put("Content-Type", "text/xml");
        try {
            StaxUtils.writeDocument(doc, writer, !MessageUtils.getContextualBoolean(message, "org.apache.cxf.stax.force-start-document", false), true);
        }
        catch (XMLStreamException e) {
            throw new Fault(e);
        }
    }
}

