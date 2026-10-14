/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import java.util.Set;
import org.apache.cxf.BusException;
import org.apache.cxf.transport.DestinationFactory;

public interface DestinationFactoryManager {
    public void registerDestinationFactory(String var1, DestinationFactory var2);

    public void deregisterDestinationFactory(String var1);

    public Set<String> getRegisteredDestinationFactoryNames();

    public DestinationFactory getDestinationFactory(String var1) throws BusException;

    public DestinationFactory getDestinationFactoryForUri(String var1);
}

