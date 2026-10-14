/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.scanning;

import java.net.URI;
import java.util.Set;
import org.glassfish.jersey.server.ResourceFinder;

interface UriSchemeResourceFinderFactory {
    public Set<String> getSchemes();

    public ResourceFinder create(URI var1, boolean var2);
}

