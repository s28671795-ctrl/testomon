/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import org.glassfish.jersey.spi.Contract;

@Contract
@ConstrainedTo(value=RuntimeType.SERVER)
public interface DestroyListener {
    public void onDestroy();
}

