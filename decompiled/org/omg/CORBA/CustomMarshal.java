/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.DataInputStream;
import org.omg.CORBA.DataOutputStream;
import org.omg.CORBA.portable.ValueBase;

public interface CustomMarshal
extends ValueBase {
    public void marshal(DataOutputStream var1);

    public void unmarshal(DataInputStream var1);
}

