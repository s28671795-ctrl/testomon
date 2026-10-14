/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.portable;

import java.io.Serializable;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public interface BoxedValueHelper {
    public Serializable read_value(InputStream var1);

    public void write_value(OutputStream var1, Serializable var2);

    public String get_id();
}

