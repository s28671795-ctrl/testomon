/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.util.Set;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.interceptor.ReadHeadersInterceptor;
import org.apache.cxf.binding.soap.model.SoapBindingInfo;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.wsdl.interceptors.AbstractEndpointSelectionInterceptor;

public class EndpointSelectionInterceptor
extends AbstractEndpointSelectionInterceptor {
    public EndpointSelectionInterceptor() {
        super("read");
        this.getAfter().add(ReadHeadersInterceptor.class.getName());
    }

    @Override
    protected Endpoint selectEndpoint(Message message, Set<Endpoint> eps) {
        SoapVersion sv = ((SoapMessage)message).getVersion();
        for (Endpoint e : eps) {
            EndpointInfo ei = e.getEndpointInfo();
            BindingInfo binding = ei.getBinding();
            if (!(binding instanceof SoapBindingInfo) || !((SoapBindingInfo)binding).getSoapVersion().equals(sv)) continue;
            return e;
        }
        return null;
    }
}

