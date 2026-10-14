/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA_2_3.portable;

import java.io.Serializable;
import org.omg.CORBA.NO_IMPLEMENT;
import org.omg.CORBA.portable.BoxedValueHelper;

public abstract class InputStream
extends org.omg.CORBA.portable.InputStream {
    public Serializable read_value() {
        throw new NO_IMPLEMENT();
    }

    public Serializable read_value(String rep_id) {
        throw new NO_IMPLEMENT();
    }

    public Serializable read_value(Class clz) {
        throw new NO_IMPLEMENT();
    }

    public Serializable read_value(BoxedValueHelper factory2) {
        throw new NO_IMPLEMENT();
    }

    public Serializable read_value(Serializable value) {
        throw new NO_IMPLEMENT();
    }

    public Object read_abstract_interface() {
        throw new NO_IMPLEMENT();
    }

    public Object read_abstract_interface(Class clz) {
        throw new NO_IMPLEMENT();
    }
}

