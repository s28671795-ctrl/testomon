/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.ServerRequest;
import org.omg.PortableServer.Servant;

public abstract class DynamicImplementation
extends Servant {
    public abstract void invoke(ServerRequest var1);
}

