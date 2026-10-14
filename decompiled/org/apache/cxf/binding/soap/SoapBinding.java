/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap;

import org.apache.cxf.binding.Binding;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.interceptor.AbstractBasicInterceptorProvider;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.BindingInfo;

public class SoapBinding
extends AbstractBasicInterceptorProvider
implements Binding {
    private SoapVersion version;
    private BindingInfo bindingInfo;

    public SoapBinding(BindingInfo info) {
        this(info, Soap11.getInstance());
    }

    public SoapBinding(BindingInfo info, SoapVersion v) {
        this.version = v;
        this.bindingInfo = info;
    }

    @Override
    public BindingInfo getBindingInfo() {
        return this.bindingInfo;
    }

    public void setSoapVersion(SoapVersion v) {
        this.version = v;
    }

    public SoapVersion getSoapVersion() {
        return this.version;
    }

    @Override
    public Message createMessage() {
        SoapMessage soapMessage = new SoapMessage(this.version);
        soapMessage.put("Content-Type", (Object)soapMessage.getVersion().getContentType());
        return soapMessage;
    }

    @Override
    public Message createMessage(Message m4) {
        SoapMessage soapMessage = new SoapMessage(m4);
        if (m4.getExchange() != null) {
            if (m4.getExchange().getInMessage() instanceof SoapMessage) {
                soapMessage.setVersion(((SoapMessage)m4.getExchange().getInMessage()).getVersion());
            } else {
                soapMessage.setVersion(this.version);
            }
        } else {
            soapMessage.setVersion(this.version);
        }
        if (!soapMessage.containsKey("Content-Type")) {
            soapMessage.put("Content-Type", (Object)soapMessage.getVersion().getContentType());
        }
        return soapMessage;
    }
}

