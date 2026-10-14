/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.spi;

import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.spi.Contract;

@Contract
@ConstrainedTo(value=RuntimeType.SERVER)
public interface Container {
    public static final int DEFAULT_HTTP_PORT = 80;
    public static final int DEFAULT_HTTPS_PORT = 443;

    public ResourceConfig getConfiguration();

    public ApplicationHandler getApplicationHandler();

    public void reload();

    public void reload(ResourceConfig var1);
}

