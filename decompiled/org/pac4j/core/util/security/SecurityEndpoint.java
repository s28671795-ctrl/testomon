/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.util.security;

import org.pac4j.core.config.Config;
import org.pac4j.core.engine.SecurityLogic;
import org.pac4j.core.http.adapter.HttpActionAdapter;

public interface SecurityEndpoint {
    public void setClients(String var1);

    public void setAuthorizers(String var1);

    public void setMatchers(String var1);

    public void setSecurityLogic(SecurityLogic var1);

    public void setHttpActionAdapter(HttpActionAdapter var1);

    public void setConfig(Config var1);
}

