/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.engine.savedrequest;

import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.exception.http.HttpAction;

public interface SavedRequestHandler {
    public void save(WebContext var1, SessionStore var2);

    public HttpAction restore(WebContext var1, SessionStore var2, String var3);
}

