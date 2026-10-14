/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.spi;

import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.spi.Contract;

@Contract
@ConstrainedTo(value=RuntimeType.SERVER)
public interface ContainerLifecycleListener {
    public void onStartup(Container var1);

    public void onReload(Container var1);

    public void onShutdown(Container var1);
}

