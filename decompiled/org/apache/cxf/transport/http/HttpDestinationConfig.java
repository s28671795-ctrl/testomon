/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.transports.http.configuration.HTTPServerPolicy;

public class HttpDestinationConfig {
    private HTTPServerPolicy serverPolicy;

    public void apply(AbstractHTTPDestination destination) {
        destination.setServer(this.serverPolicy);
    }

    public void setServerPolicy(HTTPServerPolicy serverPolicy) {
        this.serverPolicy = serverPolicy;
    }
}

