/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.engine;

import org.pac4j.core.config.Config;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.http.adapter.HttpActionAdapter;

@FunctionalInterface
public interface LogoutLogic {
    public Object perform(WebContext var1, SessionStore var2, Config var3, HttpActionAdapter var4, String var5, String var6, Boolean var7, Boolean var8, Boolean var9);
}

