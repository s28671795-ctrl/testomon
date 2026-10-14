/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import java.io.Serializable;
import org.omg.CORBA.DataOutputStream;
import org.omg.CORBA.portable.BoxedValueHelper;

public interface CustomOutputStream
extends DataOutputStream {
    public void write_value(Serializable var1);

    public void write_value(Serializable var1, String var2);

    public void write_value(Serializable var1, BoxedValueHelper var2);

    public void write_abstract_interface(Object var1);
}

