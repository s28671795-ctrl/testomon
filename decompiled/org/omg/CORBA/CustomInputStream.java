/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.Serializable;
import org.omg.CORBA.DataInputStream;
import org.omg.CORBA.portable.BoxedValueHelper;

public interface CustomInputStream
extends DataInputStream {
    public Serializable read_value();

    public Serializable read_value(String var1);

    public Serializable read_value(Class var1);

    public Object read_abstract_interface();

    public Object read_abstract_interface(Class var1);

    public Serializable read_value(BoxedValueHelper var1);
}

