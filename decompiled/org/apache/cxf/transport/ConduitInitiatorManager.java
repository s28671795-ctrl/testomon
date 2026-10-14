/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.transport;

import org.apache.cxf.BusException;
import org.apache.cxf.transport.ConduitInitiator;

public interface ConduitInitiatorManager {
    public void registerConduitInitiator(String var1, ConduitInitiator var2);

    public void deregisterConduitInitiator(String var1);

    public ConduitInitiator getConduitInitiator(String var1) throws BusException;

    public ConduitInitiator getConduitInitiatorForUri(String var1);
}

