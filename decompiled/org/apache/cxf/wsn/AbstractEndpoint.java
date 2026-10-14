/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn;

import java.net.URL;
import javax.management.ObjectName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.cxf.wsn.EndpointMBean;
import org.apache.cxf.wsn.EndpointManager;
import org.apache.cxf.wsn.EndpointRegistrationException;
import org.oasis_open.docs.wsn.bw_2.CreatePullPoint;

public abstract class AbstractEndpoint
implements EndpointMBean {
    protected final String name;
    protected String address;
    protected EndpointManager manager;
    protected Endpoint endpoint;
    protected W3CEndpointReference endpointEpr;

    public AbstractEndpoint(String name) {
        this.name = name;
    }

    public ObjectName getMBeanName() {
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public final URL getWSDLLocation() {
        return CreatePullPoint.class.getClassLoader().getResource("org/apache/cxf/wsn/wsdl/wsn.wsdl");
    }

    @Override
    public synchronized void register() throws EndpointRegistrationException {
        this.endpoint = this.manager.register(this.getAddress(), this, this.getWSDLLocation());
        this.endpointEpr = null;
    }

    @Override
    public synchronized void unregister() throws EndpointRegistrationException {
        if (this.endpoint != null) {
            this.manager.unregister(this.endpoint, this);
            this.endpointEpr = null;
        }
    }

    public synchronized W3CEndpointReference getEpr() {
        if (this.endpoint != null) {
            if (this.endpointEpr == null) {
                this.endpointEpr = this.manager.getEpr(this.endpoint);
            }
            return this.endpointEpr;
        }
        return null;
    }

    public EndpointManager getManager() {
        return this.manager;
    }

    public void setManager(EndpointManager manager) {
        this.manager = manager;
    }
}

