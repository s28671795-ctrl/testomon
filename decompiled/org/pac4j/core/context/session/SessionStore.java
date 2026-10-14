/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.context.session;

import java.util.Optional;
import org.pac4j.core.context.WebContext;

public interface SessionStore {
    public Optional<String> getSessionId(WebContext var1, boolean var2);

    public Optional<Object> get(WebContext var1, String var2);

    public void set(WebContext var1, String var2, Object var3);

    public boolean destroySession(WebContext var1);

    public Optional<Object> getTrackableSession(WebContext var1);

    public Optional<SessionStore> buildFromTrackableSession(WebContext var1, Object var2);

    public boolean renewSession(WebContext var1);
}

