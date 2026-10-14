/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.resolver;

import org.xml.sax.InputSource;

public interface URIResolver {
    public InputSource resolveEntity(String var1, String var2, String var3);
}

