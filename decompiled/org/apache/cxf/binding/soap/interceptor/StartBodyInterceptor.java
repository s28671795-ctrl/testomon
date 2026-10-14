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
import org.apache.cxf.interceptor.Fault;

public class StartBodyInterceptor
extends AbstractSoapInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(StartBodyInterceptor.class);

    public StartBodyInterceptor() {
        super("read");
    }

    public StartBodyInterceptor(String phase) {
        super(phase);
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        if (this.isGET(message)) {
            LOG.fine("StartBodyInterceptor skipped in HTTP GET method");
            return;
        }
        XMLStreamReader xmlReader = message.getContent(XMLStreamReader.class);
        try {
            int i = xmlReader.next();
            while (i == 13 || i == 10) {
                i = xmlReader.next();
            }
        }
        catch (XMLStreamException e) {
            throw new SoapFault(new Message("XML_STREAM_EXC", LOG, e.getMessage()), (Throwable)e, message.getVersion().getSender());
        }
    }
}

