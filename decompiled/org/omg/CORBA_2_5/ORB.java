/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA_2_5;

import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.Object;
import org.omg.CORBA.TypeCode;

public abstract class ORB
extends org.omg.CORBA_2_3.ORB {
    @Override
    public String id() {
        throw new NO_IMPLEMENT();
    }

    @Override
    public void register_initial_reference(String object_name, Object object) throws InvalidName {
        throw new NO_IMPLEMENT();
    }

    @Override
    public TypeCode create_local_interface_tc(String id, String name) {
        throw new NO_IMPLEMENT();
    }
}

