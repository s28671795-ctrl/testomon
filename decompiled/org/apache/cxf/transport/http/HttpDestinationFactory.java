/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.io.IOException;
import org.apache.cxf.Bus;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.apache.cxf.transport.http.DestinationRegistry;

public interface HttpDestinationFactory {
    public AbstractHTTPDestination createDestination(EndpointInfo var1, Bus var2, DestinationRegistry var3) throws IOException;
}

