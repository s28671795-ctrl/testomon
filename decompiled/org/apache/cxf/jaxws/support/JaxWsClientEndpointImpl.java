/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.support;

import java.util.List;
import java.util.concurrent.Executor;
import javax.xml.ws.WebServiceFeature;
import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.jaxws.ServiceImpl;
import org.apache.cxf.jaxws.support.JaxWsEndpointImpl;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.EndpointInfo;

public class JaxWsClientEndpointImpl
extends JaxWsEndpointImpl {
    private static final long serialVersionUID = 3059035442836604053L;
    private ServiceImpl executorProvider;

    public JaxWsClientEndpointImpl(Bus bus, Service s2, EndpointInfo ei, ServiceImpl si, List<WebServiceFeature> wf) throws EndpointException {
        super(bus, s2, ei, wf);
        this.executorProvider = si;
    }

    @Override
    public Executor getExecutor() {
        Executor e = this.executorProvider.getExecutor();
        if (null == e) {
            e = super.getExecutor();
        }
        return e;
    }
}

