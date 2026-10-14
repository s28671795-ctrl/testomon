/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantManagerOperations;

public interface ServantActivatorOperations
extends ServantManagerOperations {
    public Servant incarnate(byte[] var1, POA var2) throws ForwardRequest;

    public void etherealize(byte[] var1, POA var2, Servant var3, boolean var4, boolean var5);
}

