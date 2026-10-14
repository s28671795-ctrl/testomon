/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.model;

import org.glassfish.jersey.uri.PathPattern;

public interface Routed {
    public String getPath();

    public PathPattern getPathPattern();
}

