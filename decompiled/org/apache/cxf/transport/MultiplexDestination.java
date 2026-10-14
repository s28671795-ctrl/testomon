/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import java.util.Map;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

public interface MultiplexDestination
extends Destination {
    public EndpointReferenceType getAddressWithId(String var1);

    public String getId(Map<String, Object> var1);
}

