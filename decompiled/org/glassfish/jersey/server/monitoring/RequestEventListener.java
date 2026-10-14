/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

import org.glassfish.jersey.server.monitoring.RequestEvent;

public interface RequestEventListener {
    public void onEvent(RequestEvent var1);
}

