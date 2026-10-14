/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn.services;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.Endpoint;
import javax.xml.ws.spi.Provider;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.cxf.wsn.AbstractEndpoint;
import org.apache.cxf.wsn.EndpointManager;
import org.apache.cxf.wsn.EndpointRegistrationException;
import org.apache.cxf.wsn.util.WSNHelper;
import org.w3c.dom.Element;

public class JaxwsEndpointManager
implements EndpointManager {
    protected MBeanServer mbeanServer;

    public void setMBeanServer(MBeanServer s2) {
        this.mbeanServer = s2;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Endpoint register(String address, Object service2, URL wsdlLocation) throws EndpointRegistrationException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
            if (WSNHelper.getInstance().setClassLoader()) {
                Thread.currentThread().setContextClassLoader(JaxwsEndpointManager.class.getClassLoader());
            }
            Endpoint endpoint = this.createEndpoint(service2);
            if (wsdlLocation != null) {
                try {
                    if (endpoint.getProperties() == null) {
                        endpoint.setProperties(new HashMap<String, Object>());
                    }
                    endpoint.getProperties().put("javax.xml.ws.wsdl.description", wsdlLocation.toExternalForm());
                    ArrayList<Source> mt = new ArrayList<Source>();
                    StreamSource src = new StreamSource(wsdlLocation.openStream(), wsdlLocation.toExternalForm());
                    mt.add(src);
                    endpoint.setMetadata(mt);
                }
                catch (IOException mt) {
                    // empty catch block
                }
            }
            endpoint.publish(address);
            try {
                ObjectName on;
                if (this.mbeanServer != null && service2 instanceof AbstractEndpoint && (on = ((AbstractEndpoint)service2).getMBeanName()) != null) {
                    this.mbeanServer.registerMBean(service2, on);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            Endpoint endpoint2 = endpoint;
            return endpoint2;
        }
        finally {
            Thread.currentThread().setContextClassLoader(cl);
        }
    }

    protected Endpoint createEndpoint(Object service2) {
        String bindingId = "http://schemas.xmlsoap.org/wsdl/soap/http";
        if (this.isCXF()) {
            bindingId = "http://www.w3.org/2003/05/soap/bindings/HTTP/";
        }
        return Endpoint.create(bindingId, service2);
    }

    private boolean isCXF() {
        return Provider.provider().getClass().getName().contains(".cxf");
    }

    @Override
    public void unregister(Endpoint endpoint, Object service2) throws EndpointRegistrationException {
        try {
            ObjectName on;
            if (this.mbeanServer != null && service2 instanceof AbstractEndpoint && (on = ((AbstractEndpoint)service2).getMBeanName()) != null) {
                this.mbeanServer.unregisterMBean(on);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        endpoint.stop();
    }

    @Override
    public W3CEndpointReference getEpr(Endpoint endpoint) {
        return endpoint.getEndpointReference(W3CEndpointReference.class, new Element[0]);
    }
}

