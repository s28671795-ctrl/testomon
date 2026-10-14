/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.apache.cxf.Bus;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Destination;

public interface DestinationFactory {
    public Destination getDestination(EndpointInfo var1, Bus var2) throws IOException;

    public Set<String> getUriPrefixes();

    public List<String> getTransportIds();
}

