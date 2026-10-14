/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA_2_3;

import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.ValueFactory;

public abstract class ORB
extends org.omg.CORBA.ORB {
    @Override
    public Object get_value_def(String repid) {
        throw new NO_IMPLEMENT();
    }

    @Override
    public ValueFactory register_value_factory(String id, ValueFactory factory2) {
        throw new NO_IMPLEMENT();
    }

    @Override
    public void unregister_value_factory(String id) {
        throw new NO_IMPLEMENT();
    }

    @Override
    public ValueFactory lookup_value_factory(String id) {
        throw new NO_IMPLEMENT();
    }

    @Override
    public void set_delegate(java.lang.Object wrapper) {
        throw new NO_IMPLEMENT();
    }
}

