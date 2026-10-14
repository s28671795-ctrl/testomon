/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.Endpoint;

public class CheckFaultInterceptor
extends AbstractSoapInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(CheckFaultInterceptor.class);

    public CheckFaultInterceptor() {
        this("post-protocol");
    }

    public CheckFaultInterceptor(String phase) {
        super(phase);
    }

    @Override
    public void handleMessage(SoapMessage message) {
        XMLStreamReader xmlReader = message.getContent(XMLStreamReader.class);
        if (xmlReader == null) {
            return;
        }
        try {
            int x = xmlReader.getEventType();
            while (x != 1 && x != 2 && xmlReader.hasNext()) {
                x = xmlReader.next();
            }
            if (!xmlReader.hasNext()) {
                return;
            }
        }
        catch (XMLStreamException e) {
            throw new SoapFault(new Message("XML_STREAM_EXC", LOG, e.getMessage()), (Throwable)e, message.getVersion().getSender());
        }
        if (message.getVersion().getFault().equals(xmlReader.getName()) && this.isRequestor(message)) {
            Endpoint ep = message.getExchange().getEndpoint();
            message.getInterceptorChain().abort();
            if (ep.getInFaultObserver() != null) {
                ep.getInFaultObserver().onMessage(message);
            }
        }
    }
}

