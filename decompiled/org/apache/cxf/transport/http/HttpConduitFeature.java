/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.feature.AbstractPortableFeature;
import org.apache.cxf.feature.DelegatingFeature;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.HttpConduitConfig;

public class HttpConduitFeature
extends DelegatingFeature<Portable> {
    public HttpConduitFeature() {
        super(new Portable());
    }

    public void setConduitConfig(HttpConduitConfig conduitConfig) {
        ((Portable)this.delegate).setConduitConfig(conduitConfig);
    }

    public static class Portable
    implements AbstractPortableFeature {
        private HttpConduitConfig conduitConfig;

        @Override
        public void initialize(Client client, Bus bus) {
            Conduit conduit = client.getConduit();
            if (this.conduitConfig != null && conduit instanceof HTTPConduit) {
                this.conduitConfig.apply((HTTPConduit)conduit);
            }
        }

        public void setConduitConfig(HttpConduitConfig conduitConfig) {
            this.conduitConfig = conduitConfig;
        }
    }
}

