/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public abstract class AbstractTransportFactory {
    public static final String PREFERRED_TRANSPORT_ID = "org.apache.cxf.preferred.transport.id";
    private List<String> transportIds;

    public AbstractTransportFactory() {
    }

    public AbstractTransportFactory(List<String> ids) {
        this.transportIds = ids;
    }

    public final List<String> getTransportIds() {
        return this.transportIds;
    }

    public void setTransportIds(List<String> transportIds) {
        this.transportIds = transportIds;
    }

    public Set<String> getUriPrefixes() {
        return Collections.emptySet();
    }
}

