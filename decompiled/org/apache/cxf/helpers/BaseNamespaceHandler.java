/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.aries.blueprint.NamespaceHandler
 */
package org.apache.cxf.helpers;

import java.net.URL;
import org.apache.aries.blueprint.NamespaceHandler;
import org.apache.cxf.internal.CXFAPINamespaceHandler;

public abstract class BaseNamespaceHandler
implements NamespaceHandler {
    private NamespaceHandler cxfApiNamespaceHandler = new CXFAPINamespaceHandler();

    protected URL findCoreSchemaLocation(String namespace) {
        return this.cxfApiNamespaceHandler.getSchemaLocation(namespace);
    }
}

