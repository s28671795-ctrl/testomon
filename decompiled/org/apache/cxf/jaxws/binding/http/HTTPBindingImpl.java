/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.binding.http;

import java.util.List;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.LogicalHandler;
import javax.xml.ws.http.HTTPBinding;
import org.apache.cxf.jaxws.binding.AbstractBindingImpl;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;
import org.apache.cxf.service.model.BindingInfo;

public class HTTPBindingImpl
extends AbstractBindingImpl
implements HTTPBinding {
    public HTTPBindingImpl(BindingInfo sb, JaxWsEndpointImpl ep) {
        super(ep);
    }

    @Override
    public String getBindingID() {
        return "http://cxf.apache.org/bindings/xformat";
    }

    @Override
    public void setHandlerChain(List<Handler> hc) {
        super.setHandlerChain(hc);
        this.validate();
    }

    private void validate() {
        for (Handler handler : this.getHandlerChain()) {
            if (handler instanceof LogicalHandler) continue;
            throw new WebServiceException("Adding an incompatible handler in HTTPBinding: " + handler.getClass());
        }
    }
}

