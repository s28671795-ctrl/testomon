/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.xml;

import org.apache.cxf.binding.Binding;
import org.apache.cxf.interceptor.AbstractBasicInterceptorProvider;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.message.XMLMessage;
import org.apache.cxf.service.model.BindingInfo;

public class XMLBinding
extends AbstractBasicInterceptorProvider
implements Binding {
    private BindingInfo bindingInfo;

    public XMLBinding(BindingInfo bindingInfo) {
        this.bindingInfo = bindingInfo;
    }

    @Override
    public BindingInfo getBindingInfo() {
        return this.bindingInfo;
    }

    @Override
    public Message createMessage() {
        return this.createMessage(new MessageImpl());
    }

    @Override
    public Message createMessage(Message m4) {
        if (!m4.containsKey("Content-Type")) {
            String ct = null;
            Exchange exchange = m4.getExchange();
            if (exchange != null) {
                ct = (String)exchange.get("Content-Type");
            }
            if (ct == null) {
                ct = "text/xml";
            }
            m4.put("Content-Type", ct);
        }
        return new XMLMessage(m4);
    }
}

