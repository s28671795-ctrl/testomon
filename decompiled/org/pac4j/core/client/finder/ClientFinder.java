/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.client.finder;

import java.util.List;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.context.WebContext;

@FunctionalInterface
public interface ClientFinder {
    public List<Client> find(Clients var1, WebContext var2, String var3);
}

