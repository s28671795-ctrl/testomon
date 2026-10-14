/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.apache.cxf.Bus;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

public interface ConduitInitiator {
    public Conduit getConduit(EndpointInfo var1, Bus var2) throws IOException;

    public Conduit getConduit(EndpointInfo var1, EndpointReferenceType var2, Bus var3) throws IOException;

    public Set<String> getUriPrefixes();

    public List<String> getTransportIds();
}

