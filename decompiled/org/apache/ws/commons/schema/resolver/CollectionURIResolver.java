/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.resolver;

import org.apache.ws.commons.schema.resolver.URIResolver;

public interface CollectionURIResolver
extends URIResolver {
    public void setCollectionBaseURI(String var1);

    public String getCollectionBaseURI();
}

