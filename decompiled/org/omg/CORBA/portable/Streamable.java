/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.portable;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

public interface Streamable {
    public void _read(InputStream var1);

    public void _write(OutputStream var1);

    public TypeCode _type();
}

