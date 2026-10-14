/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport.http;

import java.util.Collection;
import java.util.Set;
import org.apache.cxf.transport.AbstractDestination;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

public interface DestinationRegistry {
    public void addDestination(AbstractHTTPDestination var1);

    public void removeDestination(String var1);

    public AbstractHTTPDestination getDestinationForPath(String var1);

    public AbstractHTTPDestination getDestinationForPath(String var1, boolean var2);

    public AbstractHTTPDestination checkRestfulRequest(String var1);

    public Collection<AbstractHTTPDestination> getDestinations();

    public AbstractDestination[] getSortedDestinations();

    public Set<String> getDestinationsPaths();
}

