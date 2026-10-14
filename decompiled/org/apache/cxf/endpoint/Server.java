/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.transport.Destination;

public interface Server {
    public void start();

    public void stop();

    public void destroy();

    public boolean isStarted();

    public Destination getDestination();

    public Endpoint getEndpoint();
}

