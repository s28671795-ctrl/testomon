/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.PortableServer.POAManagerPackage.AdapterInactive;
import org.omg.PortableServer.POAManagerPackage.State;

public interface POAManagerOperations {
    public void activate() throws AdapterInactive;

    public void hold_requests(boolean var1) throws AdapterInactive;

    public void discard_requests(boolean var1) throws AdapterInactive;

    public void deactivate(boolean var1, boolean var2) throws AdapterInactive;

    public State get_state();
}

