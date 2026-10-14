/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.http2.api.server;

import org.eclipse.jetty.http2.api.Session;

public interface ServerSessionListener
extends Session.Listener {
    public void onAccept(Session var1);

    public static class Adapter
    extends Session.Listener.Adapter
    implements ServerSessionListener {
        @Override
        public void onAccept(Session session) {
        }
    }
}

