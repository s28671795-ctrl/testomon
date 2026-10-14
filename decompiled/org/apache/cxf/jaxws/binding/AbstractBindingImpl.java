/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.binding;

import java.util.ArrayList;
import java.util.List;
import javax.xml.ws.Binding;
import javax.xml.ws.handler.Handler;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;

public abstract class AbstractBindingImpl
implements Binding {
    private List<Handler> handlerChain = new ArrayList<Handler>();
    private final JaxWsEndpointImpl endpoint;

    public AbstractBindingImpl(JaxWsEndpointImpl imp) {
        this.endpoint = imp;
    }

    @Override
    public List<Handler> getHandlerChain() {
        return new ArrayList<Handler>(this.handlerChain);
    }

    @Override
    public void setHandlerChain(List<Handler> hc) {
        this.handlerChain = hc;
        if (this.handlerChain == null || this.handlerChain.isEmpty()) {
            this.endpoint.removeHandlerInterceptors();
        } else {
            this.endpoint.addHandlerInterceptors();
        }
    }

    @Override
    public abstract String getBindingID();
}

