/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.engine;

import org.pac4j.core.config.Config;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.context.session.SessionStore;
import org.pac4j.core.engine.SecurityGrantedAccessAdapter;
import org.pac4j.core.http.adapter.HttpActionAdapter;

@FunctionalInterface
public interface SecurityLogic {
    public Object perform(WebContext var1, SessionStore var2, Config var3, SecurityGrantedAccessAdapter var4, HttpActionAdapter var5, String var6, String var7, String var8, Object ... var9);
}

