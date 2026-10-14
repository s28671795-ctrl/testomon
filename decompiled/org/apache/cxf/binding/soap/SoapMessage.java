/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;

public class SoapMessage
extends MessageImpl {
    private static final long serialVersionUID = 6310906412722265578L;
    private SoapVersion version;

    public SoapMessage(Message message) {
        super(message);
        this.setVersion(Soap11.getInstance());
    }

    public SoapMessage(SoapVersion ver) {
        this.setVersion(ver);
    }

    public SoapVersion getVersion() {
        return this.version;
    }

    public final void setVersion(SoapVersion v) {
        super.put(SoapVersion.class, v);
        this.version = v;
    }

    public List<Header> getHeaders() {
        List<Header> heads = CastUtils.cast((List)this.get(Header.HEADER_LIST));
        if (heads == null) {
            heads = new ArrayList<Header>();
            this.put(Header.HEADER_LIST, (Object)heads);
        }
        return heads;
    }

    public boolean hasHeader(QName qn) {
        for (Header head : this.getHeaders()) {
            if (!head.getName().equals(qn)) continue;
            return true;
        }
        return false;
    }

    public Header getHeader(QName qn) {
        for (Header head : this.getHeaders()) {
            if (!head.getName().equals(qn)) continue;
            return head;
        }
        return null;
    }

    public boolean hasHeaders() {
        return this.containsKey(Header.HEADER_LIST) && !this.getHeaders().isEmpty();
    }

    public Map<String, String> getEnvelopeNs() {
        return CastUtils.cast((Map)this.getContextualProperty("soap.env.ns.map"));
    }

    public boolean hasAdditionalEnvNs() {
        Map<String, String> ns = this.getEnvelopeNs();
        return ns != null && !ns.isEmpty();
    }
}

