/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding;

import org.apache.cxf.binding.Binding;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.transport.Destination;

public interface BindingFactory {
    public Binding createBinding(BindingInfo var1);

    public BindingInfo createBindingInfo(Service var1, String var2, Object var3);

    public void addListener(Destination var1, Endpoint var2);
}

