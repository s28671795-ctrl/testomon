/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.frontend;

import java.util.Map;
import org.apache.cxf.BusException;
import org.apache.cxf.configuration.security.AuthorizationPolicy;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientImpl;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.frontend.AbstractWSDLBasedEndpointFactory;
import org.apache.cxf.service.factory.FactoryBeanListener;
import org.apache.cxf.service.factory.ServiceConstructionException;
import org.apache.cxf.transport.ConduitInitiator;
import org.apache.cxf.transport.ConduitInitiatorManager;
import org.apache.cxf.transport.DestinationFactoryManager;
import org.apache.cxf.wsdl.service.factory.ReflectionServiceFactoryBean;
import org.apache.cxf.wsdl11.WSDLEndpointFactory;

public class ClientFactoryBean
extends AbstractWSDLBasedEndpointFactory {
    public ClientFactoryBean() {
        this(new ReflectionServiceFactoryBean());
    }

    public ClientFactoryBean(ReflectionServiceFactoryBean factory2) {
        super(factory2);
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
        if (this.destinationFactory instanceof WSDLEndpointFactory) {
            return (WSDLEndpointFactory)((Object)this.destinationFactory);
        }
        try {
            ConduitInitiator o = this.getBus().getExtension(ConduitInitiatorManager.class).getConduitInitiator(this.transportId);
            if (o instanceof WSDLEndpointFactory) {
                return (WSDLEndpointFactory)((Object)o);
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        if (this.destinationFactory == null) {
            try {
                this.destinationFactory = this.getBus().getExtension(DestinationFactoryManager.class).getDestinationFactory(this.transportId);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        return null;
    }

    public Client create() {
        this.getServiceFactory().reset();
        if (this.getServiceFactory().getProperties() == null) {
            this.getServiceFactory().setProperties(this.properties);
        } else if (this.properties != null) {
            this.getServiceFactory().getProperties().putAll(this.properties);
        }
        Client client = null;
        Endpoint ep = null;
        try {
            ep = this.createEndpoint();
            this.getServiceFactory().sendEvent(FactoryBeanListener.Event.PRE_CLIENT_CREATE, ep);
            this.applyProperties(ep);
            client = this.createClient(ep);
            this.initializeAnnotationInterceptors(ep, this.getServiceClass());
        }
        catch (BusException | EndpointException e) {
            throw new ServiceConstructionException(e);
        }
        this.applyFeatures(client);
        this.getServiceFactory().sendEvent(FactoryBeanListener.Event.CLIENT_CREATED, client, ep);
        return client;
    }

    protected Client createClient(Endpoint ep) {
        return new ClientImpl(this.getBus(), ep, this.getConduitSelector());
    }

    protected void applyFeatures(Client client) {
        if (this.getFeatures() != null) {
            for (Feature feature : this.getFeatures()) {
                feature.initialize(client, this.getBus());
            }
        }
    }

    protected void applyProperties(Endpoint ep) {
        Map<String, Object> props = this.getProperties();
        if (props != null && props.get(AuthorizationPolicy.class.getName()) != null) {
            AuthorizationPolicy ap = (AuthorizationPolicy)props.get(AuthorizationPolicy.class.getName());
            ep.getEndpointInfo().addExtensor(ap);
        }
    }
}

