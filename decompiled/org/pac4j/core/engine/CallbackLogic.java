/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.engine;

import org.pac4j.core.config.Config;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.http.adapter.HttpActionAdapter;

@FunctionalInterface
public interface CallbackLogic {
    public Object perform(WebContext var1, SessionStore var2, Config var3, HttpActionAdapter var4, String var5, Boolean var6, String var7);
}

