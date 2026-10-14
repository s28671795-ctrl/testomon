/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.frontend;

import java.io.File;
import org.apache.cxf.frontend.AbstractWSDLBasedEndpointFactory;
import org.apache.cxf.service.ServiceBuilder;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.transport.ConduitInitiator;
import org.apache.cxf.transport.ConduitInitiatorManager;
import org.apache.cxf.transport.DestinationFactoryManager;
import org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean;
import org.apache.cxf.wsdl11.WSDLEndpointFactory;

public abstract class AbstractServiceFactory
extends AbstractWSDLBasedEndpointFactory
implements ServiceBuilder {
    protected AbstractServiceFactory() {
    }

    protected AbstractServiceFactory(ReflectionServiceFactoryBean sbean) {
        super(sbean);
    }

    @Override
    protected String detectTransportIdFromAddress(String ad) {
        ConduitInitiatorManager cim = this.getBus().getExtension(ConduitInitiatorManager.class);
        ConduitInitiator ci = cim.getConduitInitiatorForUri(this.getAddress());
        if (ci != null) {
            return ci.getTransportIds().get(0);
        }
        return null;
    }

    @Override
    protected WSDLEndpointFactory getWSDLEndpointFactory() {
        if (this.destinationFactory == null) {
            try {
                this.destinationFactory = this.getBus().getExtension(DestinationFactoryManager.class).getDestinationFactory(this.transportId);
            }
            catch (Throwable t2) {
                try {
                    ConduitInitiator o = this.getBus().getExtension(ConduitInitiatorManager.class).getConduitInitiator(this.transportId);
                    if (o instanceof WSDLEndpointFactory) {
                        return (WSDLEndpointFactory)((Object)o);
                    }
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
        }
        if (this.destinationFactory instanceof WSDLEndpointFactory) {
            return (WSDLEndpointFactory)((Object)this.destinationFactory);
        }
        return null;
    }

    @Override
    public ServiceInfo createService() {
        try {
            return this.createEndpoint().getEndpointInfo().getService();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getOutputFile() {
        return null;
    }

    @Override
    public void setServiceClass(Class<?> clz) {
        super.setServiceClass(clz);
        this.getServiceFactory().setServiceClass(clz);
    }

    @Override
    public void validate() {
    }
}

