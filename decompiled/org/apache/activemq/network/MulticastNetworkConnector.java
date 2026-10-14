/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import java.net.URI;
import org.apache.activemq.network.CompositeDemandForwardingBridge;
import org.apache.activemq.network.DemandForwardingBridgeSupport;
import org.apache.activemq.network.NetworkConnector;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFactory;
import org.apache.activemq.util.ServiceStopper;

public class MulticastNetworkConnector
extends NetworkConnector {
    private Transport localTransport;
    private Transport remoteTransport;
    private URI remoteURI;
    private DemandForwardingBridgeSupport bridge;

    public MulticastNetworkConnector() {
    }

    public MulticastNetworkConnector(URI remoteURI) {
        this.remoteURI = remoteURI;
    }

    public DemandForwardingBridgeSupport getBridge() {
        return this.bridge;
    }

    public void setBridge(DemandForwardingBridgeSupport bridge) {
        this.bridge = bridge;
    }

    public Transport getLocalTransport() {
        return this.localTransport;
    }

    public void setLocalTransport(Transport localTransport) {
        this.localTransport = localTransport;
    }

    public Transport getRemoteTransport() {
        return this.remoteTransport;
    }

    public void setRemoteTransport(Transport remoteTransport) {
        this.remoteTransport = remoteTransport;
    }

    public URI getRemoteURI() {
        return this.remoteURI;
    }

    public void setRemoteURI(URI remoteURI) {
        this.remoteURI = remoteURI;
    }

    @Override
    protected void handleStart() throws Exception {
        if (this.remoteTransport == null) {
            if (this.remoteURI == null) {
                throw new IllegalArgumentException("You must specify the remoteURI property");
            }
            this.remoteTransport = TransportFactory.connect(this.remoteURI);
        }
        if (this.localTransport == null) {
            this.localTransport = this.createLocalTransport();
        }
        this.bridge = this.createBridge(this.localTransport, this.remoteTransport);
        this.configureBridge(this.bridge);
        this.bridge.start();
        this.remoteTransport.start();
        this.localTransport.start();
        super.handleStart();
    }

    @Override
    protected void handleStop(ServiceStopper stopper) throws Exception {
        super.handleStop(stopper);
        if (this.bridge != null) {
            try {
                this.bridge.stop();
            }
            catch (Exception e) {
                stopper.onException(this, e);
            }
        }
        if (this.remoteTransport != null) {
            try {
                this.remoteTransport.stop();
            }
            catch (Exception e) {
                stopper.onException(this, e);
            }
        }
        if (this.localTransport != null) {
            try {
                this.localTransport.stop();
            }
            catch (Exception e) {
                stopper.onException(this, e);
            }
        }
    }

    public String toString() {
        return this.getClass().getName() + ":" + this.getName() + "[" + this.remoteTransport.toString() + "]";
    }

    protected DemandForwardingBridgeSupport createBridge(Transport local, Transport remote) {
        CompositeDemandForwardingBridge bridge = new CompositeDemandForwardingBridge(this, local, remote);
        bridge.setBrokerService(this.getBrokerService());
        return bridge;
    }
}

