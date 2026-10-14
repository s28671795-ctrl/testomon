/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws22;

import javax.xml.ws.WebServiceFeature;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

@Deprecated
public class EndpointImpl
extends org.apache.cxf.jaxws.EndpointImpl {
    public EndpointImpl(Object implementor) {
        super(implementor);
    }

    public EndpointImpl(Bus b, Object implementor, JaxWsServerFactoryBean sf) {
        super(b, implementor, sf);
    }

    public EndpointImpl(Bus b, Object i, String bindingUri, String wsdl) {
        super(b, i, bindingUri, wsdl);
    }

    public EndpointImpl(Bus b, Object i, String bindingUri, String wsdl, WebServiceFeature[] f) {
        super(b, i, bindingUri, wsdl, f);
    }

    public EndpointImpl(Bus b, Object i, String bindingUri) {
        super(b, i, bindingUri);
    }

    public EndpointImpl(Bus b, Object i, String bindingUri, WebServiceFeature[] features) {
        super(b, i, bindingUri, features);
    }

    public EndpointImpl(Bus bus, Object implementor) {
        super(bus, implementor);
    }
}

