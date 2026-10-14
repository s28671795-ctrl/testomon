/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.service;

import java.io.File;
import org.apache.cxf.Bus;
import org.apache.cxf.service.model.ServiceInfo;

public interface ServiceBuilder {
    public ServiceInfo createService();

    public void setBindingId(String var1);

    public void setTransportId(String var1);

    public void setBus(Bus var1);

    public void setAddress(String var1);

    public void setServiceClass(Class<?> var1);

    public File getOutputFile();

    public void validate();
}

