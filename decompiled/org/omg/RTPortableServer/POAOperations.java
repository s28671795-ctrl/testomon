/*
 * Decompiled with CFR 0.152.
 */
package org.omg.RTPortableServer;

import org.omg.CORBA.Object;
import org.omg.PortableServer.POAPackage.ObjectAlreadyActive;
import org.omg.PortableServer.POAPackage.ServantAlreadyActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import org.omg.PortableServer.Servant;

public interface POAOperations
extends org.omg.PortableServer.POAOperations {
    public Object create_reference_with_priority(String var1, short var2) throws WrongPolicy;

    public Object create_reference_with_id_and_priority(byte[] var1, String var2, short var3) throws WrongPolicy;

    public byte[] activate_object_with_priority(Servant var1, short var2) throws ServantAlreadyActive, WrongPolicy;

    public void activate_object_with_id_and_priority(byte[] var1, Servant var2, short var3) throws ServantAlreadyActive, ObjectAlreadyActive, WrongPolicy;
}

