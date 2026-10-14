/*
 * Decompiled with CFR 0.152.
 */
package org.omg.PortableServer;

import org.omg.CORBA.Object;
import org.omg.PortableServer.CurrentPackage.NoContext;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

public interface CurrentOperations
extends org.omg.CORBA.CurrentOperations {
    public POA get_POA() throws NoContext;

    public byte[] get_object_id() throws NoContext;

    public Object get_reference() throws NoContext;

    public Servant get_servant() throws NoContext;
}

