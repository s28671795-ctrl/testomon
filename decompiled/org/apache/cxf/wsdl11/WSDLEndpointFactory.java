/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl11;

import java.util.List;
import org.apache.cxf.Bus;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.ServiceInfo;

public interface WSDLEndpointFactory {
    public EndpointInfo createEndpointInfo(Bus var1, ServiceInfo var2, BindingInfo var3, List<?> var4);

    public void createPortExtensors(Bus var1, EndpointInfo var2, Service var3);
}

