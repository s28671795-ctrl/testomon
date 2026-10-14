/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.network;

import java.net.URI;
import java.util.HashMap;
import org.apache.activemq.network.BridgeFactory;
import org.apache.activemq.network.DemandForwardingBridge;
import org.apache.activemq.network.DurableConduitBridge;
import org.apache.activemq.network.NetworkBridgeConfiguration;
import org.apache.activemq.network.NetworkBridgeListener;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFactory;
import org.apache.activemq.util.URISupport;

public final class NetworkBridgeFactory
implements BridgeFactory {
    public static final BridgeFactory INSTANCE = new NetworkBridgeFactory();

    private NetworkBridgeFactory() {
    }

    @Override
    public DemandForwardingBridge createNetworkBridge(NetworkBridgeConfiguration configuration, Transport localTransport, Transport remoteTransport, NetworkBridgeListener listener) {
        if (configuration.isConduitSubscriptions()) {
            return this.attachListener(new DurableConduitBridge(configuration, localTransport, remoteTransport), listener);
        }
        return this.attachListener(new DemandForwardingBridge(configuration, localTransport, remoteTransport), listener);
    }

    private DemandForwardingBridge attachListener(DemandForwardingBridge bridge, NetworkBridgeListener listener) {
        if (listener != null) {
            bridge.setNetworkBridgeListener(listener);
        }
        return bridge;
    }

    @Deprecated
    public static DemandForwardingBridge createBridge(NetworkBridgeConfiguration configuration, Transport localTransport, Transport remoteTransport, NetworkBridgeListener listener) {
        return INSTANCE.createNetworkBridge(configuration, localTransport, remoteTransport, listener);
    }

    public static Transport createLocalTransport(NetworkBridgeConfiguration configuration, URI uri) throws Exception {
        return NetworkBridgeFactory.createLocalTransport(uri, !configuration.isDispatchAsync());
    }

    public static Transport createLocalAsyncTransport(URI uri) throws Exception {
        return NetworkBridgeFactory.createLocalTransport(uri, true);
    }

    private static Transport createLocalTransport(URI uri, boolean async) throws Exception {
        HashMap<String, String> map = new HashMap<String, String>(URISupport.parseParameters(uri));
        map.put("async", String.valueOf(async));
        map.put("create", "false");
        uri = URISupport.createURIWithQuery(uri, URISupport.createQueryString(map));
        return TransportFactory.connect(uri);
    }
}

