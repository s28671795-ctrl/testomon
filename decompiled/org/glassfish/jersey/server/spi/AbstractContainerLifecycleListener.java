/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.spi;

import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;

public abstract class AbstractContainerLifecycleListener
implements ContainerLifecycleListener {
    @Override
    public void onStartup(Container container) {
    }

    @Override
    public void onReload(Container container) {
    }

    @Override
    public void onShutdown(Container container) {
    }
}

