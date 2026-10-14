/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

import javax.ws.rs.ConstrainedTo;
import javax.ws.rs.RuntimeType;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.glassfish.jersey.spi.Contract;

@Contract
@ConstrainedTo(value=RuntimeType.SERVER)
public interface ApplicationEventListener {
    public void onEvent(ApplicationEvent var1);

    public RequestEventListener onRequest(RequestEvent var1);
}

