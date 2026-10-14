/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.tokenstore;

import java.util.Collection;
import org.apache.cxf.ws.security.tokenstore.SecurityToken;

public interface TokenStore {
    public void add(SecurityToken var1);

    public void add(String var1, SecurityToken var2);

    public void remove(String var1);

    public Collection<String> getTokenIdentifiers();

    public SecurityToken getToken(String var1);
}

