/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.client.spi;

import javax.ws.rs.client.ClientBuilder;
import org.glassfish.jersey.Beta;

@Beta
public interface ClientBuilderListener {
    public void onNewBuilder(ClientBuilder var1);
}

