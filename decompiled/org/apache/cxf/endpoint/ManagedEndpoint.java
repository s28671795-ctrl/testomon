/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import javax.management.JMException;
import javax.management.ObjectName;
import org.apache.cxf.Bus;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.endpoint.ServerLifeCycleListener;
import org.apache.cxf.endpoint.ServerLifeCycleManager;
import org.apache.cxf.management.ManagedComponent;
import org.apache.cxf.management.annotation.ManagedAttribute;
import org.apache.cxf.management.annotation.ManagedOperation;
import org.apache.cxf.management.annotation.ManagedResource;

@ManagedResource(componentName="Endpoint", description="Responsible for managing server instances.")
public class ManagedEndpoint
implements ManagedComponent,
ServerLifeCycleListener {
    public static final String ENDPOINT_NAME = "managed.endpoint.name";
    public static final String SERVICE_NAME = "managed.service.name";
    public static final String INSTANCE_ID = "managed.instance.id";
    protected final Bus bus;
    protected final Endpoint endpoint;
    protected final Server server;
    private State state = State.CREATED;

    public ManagedEndpoint(Bus b, Endpoint ep, Server s2) {
        this.bus = b;
        this.endpoint = ep;
        this.server = s2;
    }

    @ManagedOperation
    public void start() {
        if (this.state == State.STARTED) {
            return;
        }
        ServerLifeCycleManager mgr = this.bus.getExtension(ServerLifeCycleManager.class);
        if (mgr != null) {
            mgr.registerListener(this);
        }
        this.server.start();
    }

    @ManagedOperation
    public void stop() {
        this.server.stop();
    }

    @ManagedOperation
    public void destroy() {
        this.server.destroy();
    }

    @ManagedAttribute(description="Address Attribute", currencyTimeLimit=60)
    public String getAddress() {
        return this.endpoint.getEndpointInfo().getAddress();
    }

    @ManagedAttribute(description="TransportId Attribute", currencyTimeLimit=60)
    public String getTransportId() {
        return this.endpoint.getEndpointInfo().getTransportId();
    }

    @ManagedAttribute(description="Server State")
    public String getState() {
        return this.state.toString();
    }

    @Override
    public ObjectName getObjectName() throws JMException {
        StringBuilder buffer = new StringBuilder("org.apache.cxf").append(':');
        buffer.append("bus.id").append('=').append(this.bus.getId()).append(',');
        buffer.append("type").append('=').append("Bus.Service.Endpoint,");
        String serviceName = (String)this.endpoint.get(SERVICE_NAME);
        if (StringUtils.isEmpty(serviceName)) {
            serviceName = this.endpoint.getService().getName().toString();
        }
        serviceName = ObjectName.quote(serviceName);
        buffer.append("service").append('=').append(serviceName).append(',');
        String endpointName = (String)this.endpoint.get(ENDPOINT_NAME);
        if (StringUtils.isEmpty(endpointName)) {
            endpointName = this.endpoint.getEndpointInfo().getName().getLocalPart();
        }
        endpointName = ObjectName.quote(endpointName);
        buffer.append("port").append('=').append(endpointName).append(',');
        String instanceId = (String)this.endpoint.get(INSTANCE_ID);
        if (StringUtils.isEmpty(instanceId)) {
            instanceId = "" + this.endpoint.hashCode();
        }
        buffer.append("instance.id").append('=').append(instanceId);
        return new ObjectName(buffer.toString());
    }

    @Override
    public void startServer(Server s2) {
        if (this.server.equals(s2)) {
            this.state = State.STARTED;
        }
    }

    @Override
    public void stopServer(Server s2) {
        if (this.server.equals(s2)) {
            this.state = State.STOPPED;
            ServerLifeCycleManager mgr = this.bus.getExtension(ServerLifeCycleManager.class);
            if (mgr != null) {
                mgr.unRegisterListener(this);
            }
        }
    }

    private static enum State {
        CREATED,
        STARTED,
        STOPPED;

    }
}

