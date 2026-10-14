/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.net.InetSocketAddress;
import java.net.URI;
import org.apache.activemq.Service;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.transport.TransportAcceptListener;

public interface TransportServer
extends Service {
    public void setAcceptListener(TransportAcceptListener var1);

    public void setBrokerInfo(BrokerInfo var1);

    public URI getConnectURI();

    public InetSocketAddress getSocketAddress();

    public boolean isSslServer();

    public boolean isAllowLinkStealing();
}

