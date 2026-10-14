/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.io.IOException;
import org.apache.cxf.Bus;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transport.http.HTTPTransportFactory;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

public interface HTTPConduitFactory {
    public HTTPConduit createConduit(HTTPTransportFactory var1, Bus var2, EndpointInfo var3, EndpointReferenceType var4) throws IOException;
}

