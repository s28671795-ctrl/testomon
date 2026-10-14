/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.AbstractPortableFeature;
import org.apache.cxf.feature.DelegatingFeature;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.transport.http.HttpDestinationConfig;

public class HttpDestinationFeature
extends DelegatingFeature<Portable> {
    public HttpDestinationFeature() {
        super(new Portable());
    }

    public void setDestinationConfig(HttpDestinationConfig destinationConfig) {
        ((Portable)this.delegate).setDestinationConfig(destinationConfig);
    }

    public static class Portable
    implements AbstractPortableFeature {
        private HttpDestinationConfig destinationConfig;

        @Override
        public void initialize(Server server, Bus bus) {
            Destination destination = server.getDestination();
            if (this.destinationConfig != null && destination instanceof AbstractHTTPDestination) {
                this.destinationConfig.apply((AbstractHTTPDestination)destination);
            }
        }

        public void setDestinationConfig(HttpDestinationConfig destinationConfig) {
            this.destinationConfig = destinationConfig;
        }
    }
}

