/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsn;

import java.net.URL;
import javax.xml.ws.Endpoint;
import javax.xml.ws.wsaddressing.W3CEndpointReference;
import org.apache.cxf.wsn.EndpointRegistrationException;

public interface EndpointManager {
    public Endpoint register(String var1, Object var2, URL var3) throws EndpointRegistrationException;

    public void unregister(Endpoint var1, Object var2) throws EndpointRegistrationException;

    public W3CEndpointReference getEpr(Endpoint var1);
}

