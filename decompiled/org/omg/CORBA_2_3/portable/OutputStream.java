/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA_2_3.portable;

import java.io.Serializable;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.portable.BoxedValueHelper;

public abstract class OutputStream
extends org.omg.CORBA.portable.OutputStream {
    public void write_value(Serializable value) {
        throw new NO_IMPLEMENT();
    }

    public void write_value(Serializable value, String rep_id) {
        throw new NO_IMPLEMENT();
    }

    public void write_value(Serializable value, Class clz) {
        throw new NO_IMPLEMENT();
    }

    public void write_value(Serializable value, BoxedValueHelper factory2) {
        throw new NO_IMPLEMENT();
    }

    public void write_abstract_interface(Object object) {
        throw new NO_IMPLEMENT();
    }
}

