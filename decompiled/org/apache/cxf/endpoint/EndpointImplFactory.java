/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.EndpointException;
import org.apache.cxf.endpoint.EndpointImpl;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.EndpointInfo;

public interface EndpointImplFactory {
    public EndpointImpl newEndpointImpl(Bus var1, Service var2, EndpointInfo var3) throws EndpointException;
}

