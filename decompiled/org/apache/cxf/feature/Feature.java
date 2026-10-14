/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.feature;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.InterceptorProvider;

public interface Feature {
    public void initialize(Server var1, Bus var2);

    public void initialize(Client var1, Bus var2);

    public void initialize(InterceptorProvider var1, Bus var2);

    public void initialize(Bus var1);
}

